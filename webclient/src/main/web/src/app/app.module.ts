import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from "@angular/forms";
import { HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";


import { AppComponent } from './app.component';
import { DocumentComponent } from './document/document.component';
import { BlockPasteDirective } from './directives/block-paste.directive';

@NgModule({
  declarations: [
    AppComponent,
    DocumentComponent,
    BlockPasteDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
