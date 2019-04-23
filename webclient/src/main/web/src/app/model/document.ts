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
