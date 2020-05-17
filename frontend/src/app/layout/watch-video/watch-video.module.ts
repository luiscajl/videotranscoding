import { PageHeaderModule } from './../../shared/modules/page-header/page-header.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WatchVideoComponent } from './watch-video.component';
import { WatchVideoRoutingModule } from './watch-video-routing.module';
import { VgCoreModule } from 'ngx-videogular';
import { VgControlsModule } from 'ngx-videogular';
import { VgOverlayPlayModule } from 'ngx-videogular';
import { VgBufferingModule } from 'ngx-videogular';

@NgModule({
    imports: [
        CommonModule,
        WatchVideoRoutingModule,
        PageHeaderModule,
        VgCoreModule,
        VgControlsModule,
        VgOverlayPlayModule,
        VgBufferingModule
    ],
    declarations: [WatchVideoComponent]
})
export class WatchVideoModule { }
