import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpClient, HttpRequest } from '@angular/common/http';

import { FormGroup } from '@angular/forms';
import { environment } from 'src/environments/environment';

@Injectable()
export class UploadFileService {

  constructor(private httpClient: HttpClient) { }

  uploadFileExpert(form: FormGroup, file: File): Observable<any> {
    let formdata: FormData = new FormData();
    formdata.append('file', file, file.name);
    formdata.append('conversionType', form.get('conversionType').value);
    return this.sendConversion(environment.apiUrl+'conversion/expert', formdata);
  }
  uploadFileBasic(form: FormGroup, file: File): Observable<any> {
    let formdata: FormData = new FormData();
    formdata.append('file', file, file.name);
    formdata.append('conversionType', form.get('conversionType').value);
    return this.sendConversion(environment.apiUrl+'conversion/basic', formdata);

  }

  sendConversion(url: string, formData: FormData): Observable<any> {
    return this.httpClient.post(url, formData, {
      reportProgress: true,
      responseType: 'json'
    });
  }
}
