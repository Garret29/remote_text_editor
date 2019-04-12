import {Injectable} from '@angular/core';
import * as Rx from "rxjs/index";
import {Subject} from "rxjs/index";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {WebConfig} from "../config/web-config.service";
import {Document} from "../model/document";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stomp;
  private connection: Subject<String>;

  constructor(private webConfig: WebConfig) {}

  public getConnection(document: Document): Rx.Subject<String> {
    if (!this.connection) {
      this.connection = this.connect(document)
    }
    return this.connection;
  }

  private connect(document: Document): Subject<String> {
    const observable = Rx.Observable.create((obs) => {

      if(this.stomp){
        this.stomp.disconnect(()=>{

        })
      }

      const ws = new SockJS(this.webConfig.wsUrl);
      this.stomp = Stomp.over(ws);
      this.stomp.connect({}, (frame) => {
        console.log(frame);
        this.stomp.subscribe(this.webConfig.topicUrl + "/" + document.name, (response) => {
          obs.next(response.body)
        });
      });

      // return () => {
      //   this.stomp.disconnect(() => {
      //     console.log("unsub");
      //     this.connection = null;
      //     obs.complete()
      //   }, {})
      // }
    });

    const observer = {
      next: (data) => {
        this.stomp.send(this.webConfig.messageUrl + "/" + document.name, {}, data)
      }
    };

    return Rx.Subject.create(observer, observable);
  }
}
