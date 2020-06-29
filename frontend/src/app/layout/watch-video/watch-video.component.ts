import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';
import { routerTransition } from '../../router.animations';
import { MediaService } from '../../shared/services/media.service';
import { Original } from '../../shared/models/original.model';
import { Conversion } from '../../shared/models/conversion.model';
import { VgAPI } from 'ngx-videogular';
import { ActivatedRoute } from '@angular/router';

export interface CurrentItem {
    video: any;
    src: string;

}
@Component({
    selector: 'app-watch-video',
    templateUrl: './watch-video.component.html',
    styleUrls: ['./watch-video.component.scss'],
    animations: [routerTransition()]

})
export class WatchVideoComponent implements OnInit {
    conversions: Conversion[] = [];
    originalVideo: Original = { active: false, complete: true, conversions: this.conversions, id: 0, fileSize: "2", name: "", path: "", userVideo: null };
    currentItemWatching: CurrentItem = { video: null, src: "" };
    api: VgAPI;
    canEvaluate: boolean;
    idRedirect: number;
    @Input() watchId: number;
    constructor(private location: Location, private activatedRoute: ActivatedRoute, private mediaService: MediaService) {
        // this.ng4LoadingSpinnerService.show();
        this.watchId = activatedRoute.snapshot.queryParams['idWatch'];
        this.idRedirect = activatedRoute.snapshot.params['id'];
    }
    onPlayerReady(api: VgAPI) {
        this.api = api;
    }
    ngOnInit() {
        if (this.idRedirect == undefined) {
            //LANZAR NOT FOUND
            console.log("No se ha encontrado original ID, no has sido rederigido correctamente")
        }
        this.getOriginal();
    }
    getOriginal() {
        this.mediaService.getOriginalById(this.idRedirect).subscribe(
            result => {
                this.originalVideo = result;
                this.originalVideo.conversions.forEach(element => {
                    if (element.finished == true) {
                        this.conversions.push(element);
                    }

                });
                if (this.watchId != this.originalVideo.id) {
                    this.currentItemWatching.video = this.conversions.find(element => element.id == this.watchId);
                }
                else {
                    this.currentItemWatching.video = result;
                }
                this.currentItemWatching.src = this.getVideoUrl(this.currentItemWatching.video);
                this.canEvaluate = true;
                // this.ng4LoadingSpinnerService.hide();
            },
            error => {
                console.log(error);
                // this.ng4LoadingSpinnerService.hide();
            }
        )
    }
    getVideoUrl(video: any) {
        if (this.currentItemWatching.video.id != undefined) {
            return this.mediaService.watchById(this.currentItemWatching.video.id);
        }
        else {
            return this.mediaService.watchById(this.currentItemWatching.video.id);
        }
    }
    changeSource(newid: number): any {
        if (newid != this.originalVideo.id) {
            let video = this.conversions.find(element => element.id == newid);
            if (!this.getErrored(video) && this.canPlay(video))
                this.currentItemWatching.video = video;
        }
        else if (this.canPlay(this.originalVideo)) {
            this.currentItemWatching.video = this.originalVideo;
        }
        this.currentItemWatching.src = this.getVideoUrl(this.currentItemWatching.video);

    }
    backToLocation() {
        this.location.back();
    }
    getErrored(conversion: Conversion): boolean {
        return this.mediaService.getErroredOnConversion(conversion);
    }
    canPlay(video: any): boolean {
        return this.mediaService.canPlayVideo(video);
    }

}
