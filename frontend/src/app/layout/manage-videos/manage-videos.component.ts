import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { UploadFileService } from '../../shared/services/upload-file.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse, HttpEventType } from '@angular/common/http';
import { MediaService } from '../../shared/services/media.service';
import { Original } from '../../shared/models/original.model';
import { Router } from '@angular/router';
@Component({
  selector: 'app-manage-videos',
  templateUrl: './manage-videos.component.html',
  styleUrls: ['./manage-videos.component.scss'],
  animations: [routerTransition()]
})

export class ManageVideosComponent implements OnInit {
  originalVideos: Array<Original> = [];
  isCollapsed = true;
  sum: number;
  finished: boolean;
  noVideos: boolean;
  constructor(private router: Router, private mediaService: MediaService) {

  }
  ngOnInit() {
    this.sum = 0;
    // this.ng4LoadingSpinnerService.show();
    this.mediaService.getAllMediaByPageable(this.sum).subscribe(
      result => {
        this.originalVideos = result;
        // // this.ng4LoadingSpinnerService.hide();
      },
      error => {
        if (error.status = 404) {
          this.finished = true;
          this.originalVideos = [];
          this.noVideos = true;
        }
        console.log(error);
        // this.ng4LoadingSpinnerService.hide();
      })
  }
  onScroll() {
    if (!this.finished) {
    //   this.ng4LoadingSpinnerService.show();
      this.sum += 1;
      this.mediaService.getAllMediaByPageable(this.sum).subscribe(
        videos => {
          if (videos.length < 9) {
            this.finished = true;
          }
          videos.forEach(element => {
            this.originalVideos.push(element);
          });
          // this.ng4LoadingSpinnerService.hide();
        },
        error => {
          if (error.status = 404) {
            this.finished = true;
          }
          // this.ng4LoadingSpinnerService.hide();
        });
    }
  }
  goToConversion(originalId: number) {
    this.router.navigate(['/manage-video/' + originalId]);
  }
  watchVideo(idRedirect: number, idWatchRedirect: number) {
    this.router.navigate(['/watch-video/' + idRedirect], { queryParams: { idWatch: idWatchRedirect } });
  }
  downloadVideo(originalIdDownload: number) {
    this.mediaService.downloadById(originalIdDownload);
  }
  canPlay(video: any): boolean {
    return this.mediaService.canPlayVideo(video);

  }
  deleteVideo(video: Original) {
    // this.ng4LoadingSpinnerService.show();
    this.mediaService.deleteVideo(
      video.originalId).subscribe(
        result => {
          this.ngOnInit();
          console.log(result)
        },
        error => {
          console.log(error)
          // this.ng4LoadingSpinnerService.hide();
        })
  }
}
