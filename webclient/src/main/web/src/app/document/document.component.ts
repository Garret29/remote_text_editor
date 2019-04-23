import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Document} from "../model/document";
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

  @ViewChild("textArea")
  set setTextArea(value: ElementRef) {
    this.textArea = value;
  }


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
        } else {
          this.currentDocument = null;
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
        this.applyUpdate(document, update);
      })
    });
  }

  isRenamingDisabled() {
    return this.newName.length === 0;
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
    } else if (event.inputType === "insertLineBreak") {
      update = createUpdate("\n", this.selectionStart, this.selectionEnd, false)
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

  applyUpdate(document: Document, update: Update) {
    const start = this.textArea.nativeElement.selectionStart;
    const end = this.textArea.nativeElement.selectionEnd;
    const oldContent: String = document.content;

    if (update.appending) {
      document.previousContent = document.previousContent + update.content
    } else {
      const first = document.previousContent.substring(0, update.start);
      const last = document.previousContent.substring(update.end);

      document.previousContent = first + update.content + last
    }
    if (document.content !== document.previousContent) {
      document.content = document.previousContent;
    }
  }
}
