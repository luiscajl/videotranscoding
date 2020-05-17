import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpClient, HttpRequest } from '@angular/common/http';

import * as globals from '../../globals';
import { FormGroup } from '@angular/forms';

@Injectable()
export class UploadFileService {

  constructor(private httpClient: HttpClient) { }

  uploadFileExpert(form: FormGroup, file: File): Observable<any> {
    let formdata: FormData = new FormData();
    formdata.append('file', file, file.name);
    formdata.append('conversionType', form.get('conversionType').value);
    return this.sendConversion(globals.CONVERSION_EXPERT_URL, formdata);
  }
  uploadFileBasic(form: FormGroup, file: File): Observable<any> {
    let formdata: FormData = new FormData();
    formdata.append('file', file, file.name);
    formdata.append('conversionType', form.get('conversionType').value);
    return this.sendConversion(globals.CONVERSION_BASIC_URL, formdata);

  }

  sendConversion(url: string, formData: FormData): Observable<any> {
    return this.httpClient.post(url, formData, {
      reportProgress: true,
      responseType: 'json'
    });
  }
}
