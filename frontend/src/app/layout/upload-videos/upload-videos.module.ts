import { PageHeaderModule } from './../../shared/modules/page-header/page-header.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UploadVideosComponent } from './upload-videos.component';
import { UploadVideosRoutingModule } from './upload-videos-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


@NgModule({
  imports: [
    CommonModule,
    UploadVideosRoutingModule,
    PageHeaderModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [UploadVideosComponent]
})
export class UploadVideosModule { }
