import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs/index";
import {Document} from "../model/document";
import {DocumentService} from "../services/document.service";
import {WebSocketService} from "../services/web-socket.service";


@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {
  documents: Document[];
  currentDocument: Document;
  newDocument: Document;
  newName: String;

  constructor(
    private documentService: DocumentService,
  ) {
  }

  ngOnInit() {
    this.updateDocumentList();
    this.newDocument = new Document("", "");
    this.newName = "";
  }

  updateDocumentList() {
    this.documentService.getDocuments().subscribe((docs) => {
        this.documents = docs;
        this.currentDocument = docs[0];
        this.startUpdating();
      },
      (error) => {
      },
      ()=>{

      }
      )

  }

  private startUpdating() {
    if (this.currentDocument) {
      console.log("updating...");
      this.documentService.getUpdates(this.currentDocument).subscribe((string) => {
        this.currentDocument.content=string;
      })
    }
  }

  addNewDocument(document: Document) {
    this.documentService.addNewDocument(document).subscribe(
      (document) => {
        this.documents.push(document);
        this.newDocument = new Document("", "")
      },
      (error) => {

      },
      ()=>{

      }
    );
  }

  renameDocument(document: Document, newName: String){

    this.documentService.renameDocument(document.name, newName).subscribe(
      (string)=>{
        this.updateDocumentList();
        this.newName="";


      },
      (error)=>{

      },
      ()=>{

      }
    );

  }

  deleteDocument(name: String) {
    this.documentService.deleteDocument(name).subscribe(
      (document)=>{
        this.updateDocumentList();
      },
      ()=>{

      },
      ()=>{

      }
    )
  }

  updateDocument(document: Document) {
    this.documentService.updateDocument(document, document.content)
  }

  isRenamingDisabled(): boolean {
    return this.newName.length === 0;
  }

  documentSelected(selectedDocument: Document) {

  }

  documentChanged() {
    console.log("changed");
    this.startUpdating();
  }
}
