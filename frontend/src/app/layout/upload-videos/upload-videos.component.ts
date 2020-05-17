import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { UploadFileService } from '../../shared/services/upload-file.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpResponse, HttpEventType, HttpEvent } from '@angular/common/http';
import { MediaService } from '../../shared/services/media.service';
@Component({
  selector: 'app-upload-videos',
  templateUrl: './upload-videos.component.html',
  styleUrls: ['./upload-videos.component.scss'],
  animations: [routerTransition()]
})

export class UploadVideosComponent implements OnInit {
  selectedFiles: FileList;
  file: File;
  uploadVideoExpertForm: FormGroup;
  uploadVideoBasicForm: FormGroup;
  typeConversionExpert: string[] = [];
  typeConversionBasic: string[] = [];
  progressUploaded = 0;
  videoUploaded: boolean;
  noVideoFile: boolean;
  fileExists: boolean;

  constructor(private uploadService: UploadFileService,
    private mediaService: MediaService) {

  }
  ngOnInit() {
    //this.ng4LoadingSpinnerService.show();
    this.getTypesExpert();
    this.getTypesBasic();
    this.uploadVideoExpertForm = new FormGroup({
      conversionType: new FormControl('', Validators.required)
    })
    this.uploadVideoBasicForm = new FormGroup({
      conversionType: new FormControl('', Validators.required)
    })
  }
  getTypesExpert() {
    this.mediaService.getTypeExpert().subscribe(
      result => {
        this.typeConversionExpert = result;
        //this.ng4LoadingSpinnerService.hide();
      },
      error => {
        console.log(error);
        //this.ng4LoadingSpinnerService.hide();
      })
  }
  getTypesBasic() {
    this.mediaService.getTypeBasic().subscribe(
      result => this.typeConversionBasic = result,
      error => console.log(error))
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  uploadFileExpert(form: FormGroup) {
    this.noVideoFile = false;
    this.fileExists = false;
    this.file = this.selectedFiles.item(0);
    this.progressUploaded = 0;
    this.selectedFiles = undefined;
    this.uploadService.uploadFileExpert(form, this.file).subscribe(
      (event: HttpEvent<any>) => {
        switch (event.type) {
          case HttpEventType.Sent:
            console.log('Request has been made!');
            break;
          case HttpEventType.ResponseHeader:
            console.log('Response header has been received!');
            break;
          case HttpEventType.UploadProgress:
            this.progressUploaded = Math.round(event.loaded / event.total * 100);
            console.log(`Uploaded! ${this.progressUploaded}%`);
            break;
          case HttpEventType.Response:
            console.log('User successfully created!', event.body);
            setTimeout(() => {
              this.progressUploaded = 0;
            }, 1500);
  
        }
      }


      // event => {
      //   if (event.type === HttpEventType.UploadProgress) {
      //     console.log("event type upload")
      //     this.progressUploaded = Math.round(event.loaded / event.total * 100);
      //   } else if (event instanceof HttpResponse) {
      //     console.log('File is completely uploaded!');
      //     this.videoUploaded = true;
      //   }
      // },
      // error => {
      //   console.log(error);
      //   if (error.error.code == 15010) {
      //     this.fileExists = true;
      //   }
      //   else if (error.status == 400)
      //     this.noVideoFile = true;
      // })
    )
  }
  uploadFileBasic(form: FormGroup) {
    this.noVideoFile = false;
    this.fileExists = false;
    this.file = this.selectedFiles.item(0);
    this.selectedFiles = undefined;
    this.progressUploaded = 0;
    this.uploadService.uploadFileBasic(form, this.file).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          console.log("event type upload")
          this.progressUploaded = Math.round(event.loaded / event.total * 100);
        } else if (event instanceof HttpResponse) {
          console.log('File is completely uploaded!');
          this.videoUploaded = true;
        }
      },
      error => {
        console.log(error);
        if (error.error.code == 15010) {
          this.fileExists = true;
        }
        else if (error.status == 400)
          this.noVideoFile = true;
      })
  }
  selectedFile(): boolean {
    if (this.selectedFiles == undefined) {
      return false;
    }
    if (this.selectedFiles.item(0) != null) {
      return true;
    }
    return false;
  }
}
