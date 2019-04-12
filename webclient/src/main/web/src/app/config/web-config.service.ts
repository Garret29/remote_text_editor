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
    let url=location.href;

    if(url.search("localhost")!==-1){
      console.log(url);
      url = "http://localhost:8080/"
    }

    this.restUrl = url+"docs";
    this.wsUrl = url+"ws/docs";
    this.messageUrl = "/app/update";
    this.topicUrl = "/topic/updates";
  }
}
