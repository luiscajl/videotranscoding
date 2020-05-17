import { PageHeaderModule } from './../../shared/modules/page-header/page-header.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageVideoComponent } from './manage-video.component';
import { ManageVideoRoutingModule } from './manage-video-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        ManageVideoRoutingModule,
        PageHeaderModule, NgbModule
    ],
    declarations: [ManageVideoComponent]
})
export class ManageVideoModule { }
