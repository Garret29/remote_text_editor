import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class WebConfig {

  public readonly restUrl;
  public readonly wsUrl;
  public readonly messageUrl;
  public readonly topicUrl;

  constructor(){
    this.restUrl = "http://localhost:8080/docs";
    this.wsUrl = "http://localhost:8080/ws/docs";
    this.messageUrl = "/app/update";
    this.topicUrl = "/topic/updates";
  }
}
