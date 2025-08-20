import {Injectable, OnInit} from '@angular/core';
import * as Rx from "rxjs/index"
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {WebConfig} from "../config/web-config.service";
import {Document} from "../model/document";
import {Update} from "../model/update";
import {WebSocketSubject} from "./webSocketSubject";

@Injectable({
  providedIn: 'root'
})
export class DocumentService implements OnInit{

  constructor(
    private http: HttpClient,
    private webConfig: WebConfig
  ) {}

  ngOnInit(): void {

  }

  getWsConnection(documentName: String): Rx.Observable<WebSocketSubject<Update>>{
    return Rx.of(new WebSocketSubject(this.webConfig, documentName))
  }

  getDocuments(): Rx.Observable<Document[]> {
    return this.http.get<Document[]>(this.webConfig.restUrl);
  }

  getDocument(documentName: String): Rx.Observable<Document>{
    return this.http.get<Document>(this.webConfig.restUrl+"/"+documentName)
  }

  addNewDocument(document: Document): Rx.Observable<Document> {
    return this.http.post<Document>(this.webConfig.restUrl, document);
  }

  deleteDocument(documentName: String): Rx.Observable<Document> {
    return this.http.delete<Document>(this.webConfig.restUrl+"/"+documentName);
  }

  renameDocument(oldName: String, newName: String): Rx.Observable<String>{
    const requestOptions: Object = {
      responseType: 'text'
    };

    return this.http.patch<String>(
      this.webConfig.restUrl+"/"+oldName+"?newName="+newName,
      null,
      requestOptions
    );
  }
}
