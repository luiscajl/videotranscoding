import { Component } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { UploadFileService } from '../../shared/services/upload-file.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse, HttpEventType } from '@angular/common/http';
import { MediaService } from '../../shared/services/media.service';
import { Original } from '../../shared/models/original.model';
import { Route, ActivatedRoute, Router } from '@angular/router';
import { Conversion } from '../../shared/models/conversion.model';
@Component({
    selector: 'app-manage-video',
    templateUrl: './manage-video.component.html',
    styleUrls: ['./manage-video.component.scss'],
    animations: [routerTransition()]
})

export class ManageVideoComponent {
    originalVideo: Original = null;
    idOriginalVideo: number;
    canEvaluate: boolean;
    constructor(private router: Router, private activatedRoute: ActivatedRoute, private mediaService: MediaService) {
        // this.ng4LoadingSpinnerService.show();
        this.idOriginalVideo = activatedRoute.snapshot.params['id'];
        this.getOriginal();
    }
    getOriginal() {
        this.mediaService.getOriginalById(this.idOriginalVideo).subscribe(
            result => {
                this.originalVideo = result;
                this.canEvaluate = true;
                // this.ng4LoadingSpinnerService.hide();
            },
            error => {
                console.log(error);
                // this.ng4LoadingSpinnerService.hide();
            })
    }
    watchVideo(idRedirect: number, idWatchRedirect: number) {
        this.router.navigate(['/watch-video/' + idRedirect], { queryParams: { idWatch: idWatchRedirect } });
    }
    downloadVideo(idDownload: number) {
        this.mediaService.downloadById(idDownload);
    }
    getErrored(conversion: Conversion): boolean {
        return this.mediaService.getErroredOnConversion(conversion);
    }
    canPlay(video: any): boolean {
        return this.mediaService.canPlayVideo(video);

    }

}
