import {Injectable, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {WebConfig} from "../config/web-config.service";
import {Document} from "../model/document";
import {WebSocketService} from "./web-socket.service";

@Injectable({
  providedIn: 'root'
})
export class DocumentService implements OnInit{

  private websocket: Subject<String>;

  constructor(
    private http: HttpClient,
    private websocketService: WebSocketService,
    private webConfig: WebConfig
  ) {}

  ngOnInit(): void {

  }

  updateDocument(document: Document, update: String) {
    this.websocketService.getConnection(document).next(update);
  }

  getDocuments(): Observable<Document[]> {
    console.log(this.webConfig.restUrl);

    return this.http.get<Document[]>(this.webConfig.restUrl);
  }

  getDocument(name: String): Observable<Document>{
    return this.http.get<Document>(this.webConfig.restUrl+"/"+name)
  }

  addNewDocument(document: Document): Observable<Document> {
    return this.http.post<Document>(this.webConfig.restUrl, document);
  }

  deleteDocument(name: String): Observable<Document> {
    return this.http.delete<Document>(this.webConfig.restUrl+"/"+name+"/delete");
  }

  renameDocument(oldName: String, newName: String): Observable<String>{
    const requestOptions: Object = {
      responseType: 'text'
    };

    return this.http.patch<String>(
      this.webConfig.restUrl+"/"+oldName+"?newName="+newName,
      null,
      requestOptions
    );
  }

  getUpdates(document: Document): Observable<String> {
    return this.websocketService.getConnection(document).asObservable();
  }

  connectToWs(document: Document) {
    console.log("check 0");
    if (this.websocket){
      console.log("check 1");
      this.websocket.complete()
    }
    this.websocket = this.websocketService.getConnection(document);
  }
}
