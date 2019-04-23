import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {applyUpdate, Document} from "../model/document";
import {createUpdate, Update} from "../model/update";
import {DocumentService} from "../services/document.service";
import {WebSocketSubject} from "../services/webSocketSubject";

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {
  documents: Document[];
  currentDocument: Document;
  newDocument: Document;
  newName: string;
  updateSubject: WebSocketSubject<Update>;
  private textArea: ElementRef;
  selectionStart: number;
  selectionEnd: number;

  constructor(
    private documentService: DocumentService,
  ) {
  }

  ngOnInit() {
    this.updateDocumentList();
    this.newDocument = new Document("", "");
    this.newName = "";
  }

  updateCurrentDocument(update: Update) {
    this.updateSubject.send(update)
  }

  updateDocumentList() {
    this.documentService.getDocuments().subscribe((docs) => {
        this.documents = docs;
        if (docs.length > 0) {
          this.currentDocument = docs[0];
          this.currentDocument.previousContent = this.currentDocument.content;
          this.documentSelected(this.currentDocument)
        }
      }
    )
  }

  addNewDocument(document: Document) {
    this.documentService.addNewDocument(document).subscribe(
      (document) => {
        this.documents.push(document);
        this.newDocument = new Document("", "")
      }
    );
  }

  renameDocument(document: Document, newName: String) {
    this.documentService.renameDocument(document.name, newName).subscribe(
      (string) => {
        this.updateDocumentList();
        this.newName = "";
      })
  }

  deleteDocument(name: String) {
    this.documentService.deleteDocument(name).subscribe(
      (document) => {
        this.updateDocumentList();
      }
    )
  }

  documentSelected(document: Document) {
    if (this.updateSubject) {
      this.updateSubject.complete()
    }
    this.documentService.getWsConnection(document.name).subscribe((ws: WebSocketSubject<Update>) => {
      this.updateSubject = ws;
      this.updateSubject.subscribe((update: Update) => {
        applyUpdate(document, update)
      })
    });
  }

  isRenamingDisabled() {
    return this.newName.length === 0;
  }

  @ViewChild("textArea")
  set setTextArea(value: ElementRef) {
    this.textArea = value;
  }


  textChanged(event, document: Document) {

    let update: Update;

    if (event.inputType === "deleteContentBackward") {
      if (this.selectionStart === this.selectionEnd) {
        update = createUpdate("", this.selectionStart - 1, this.selectionEnd, false)
      } else {
        update = createUpdate("", this.selectionStart, this.selectionEnd, false)
      }
    } else if (event.inputType === "deleteContentForward") {
      update = createUpdate("", this.selectionStart, this.selectionEnd + 1, false)
    } else if (event.inputType==="insertLineBreak") {
      update = createUpdate("\n",this.selectionStart,this.selectionEnd,false)
    } else {
      const appending: boolean = this.selectionStart === this.selectionEnd && document.content.length === this.selectionEnd + 1;
      update = createUpdate(document.content.substring(this.selectionStart, this.selectionEnd + 1), this.selectionStart, this.selectionEnd, appending)
    }

    this.updateCurrentDocument(update)
  }

  updateSelection() {
    this.selectionStart = this.textArea.nativeElement.selectionStart;
    this.selectionEnd = this.textArea.nativeElement.selectionEnd;
  }
}
