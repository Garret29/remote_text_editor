import {Update} from "./update";

export class Document {
  name: string;
  content: string;
  previousContent: string;

  constructor(name: string, content: string) {
    this.name = name;
    this.content = content;
    this.previousContent = content;
  }
}



export function applyUpdate(document: Document ,update: Update){
  if(update.appending){
    document.previousContent = document.previousContent+update.content
  } else {
    const first = document.previousContent.substring(0, update.start);
    const last = document.previousContent.substring(update.end);

    document.previousContent = first+update.content+ last
  }
  if(document.content !== document.previousContent){
    document.content = document.previousContent
  }
}
