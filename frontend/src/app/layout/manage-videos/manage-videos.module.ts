import { PageHeaderModule } from './../../shared/modules/page-header/page-header.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManageVideosComponent } from './manage-videos.component';
import { ManageVideosRoutingModule } from './manage-videos-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
@NgModule({
    imports: [
        CommonModule,
        ManageVideosRoutingModule,
        PageHeaderModule, NgbModule,
        InfiniteScrollModule
    ],
    declarations: [ManageVideosComponent]
})
export class ManageVideosModule { }
