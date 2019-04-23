import {Injectable} from '@angular/core';
import * as Rx from "rxjs/index";
import {Subject} from "rxjs/index";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {WebConfig} from "../config/web-config.service";
import {Document} from "../model/document";
import {Update} from "../model/update";

export class WebSocketSubject<T>{
  private connection: Subject<T>;


  constructor(private webConfig: WebConfig, private name:String) {
    this.connection = this.connect(name);
  }

  send(t:T): void{
    this.connection.next(t)
  }

  subscribe(callback?:(value?:T)=>void){
    this.connection.subscribe((t:T)=>{
      callback(t)
    })
  }

  complete(){
    this.connection.complete()
  }


  private connect(name: String): Subject<T> {
    const ws = new SockJS(this.webConfig.wsUrl);
    const stomp = Stomp.over(ws);
    const observable = Rx.Observable.create((obs) => {
      stomp.connect({}, (frame) => {
        stomp.subscribe(this.webConfig.topicUrl + "/" + name, (response) => {
          console.log(response.body);
          obs.next(JSON.parse(response.body))
        });
      });
    });

    const observer = {
      next: (data) => {
        stomp.send(this.webConfig.messageUrl + "/" + name, {}, JSON.stringify(data))
      },
      complete: ()=>{
          stomp.disconnect(()=>{
            ws.close();
            this.connection = null;
          })
        }
    };

    return Rx.Subject.create(observer, observable);
  }
}
