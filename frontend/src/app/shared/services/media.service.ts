import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Conversion } from '../models/conversion.model';
import { environment } from 'src/environments/environment';

@Injectable()
export class MediaService {

    constructor(private httpClient: HttpClient) { }

    getTypeBasic(): Observable<any> {
        return this.httpClient.get(environment.apiUrl + 'conversion/basic');
    }
    getTypeExpert(): Observable<any> {
        return this.httpClient.get(environment.apiUrl + 'conversion/expert');
    }
    getAllMediaByPageable(page: number): Observable<any> {
        return this.httpClient.get(environment.apiUrl + 'media' + '?page=' + String(page) + '&size=9');
    }
    getAllMediaByPageableForDashboard(page: number): Observable<any> {
        return this.httpClient.get(environment.apiUrl + 'media' + '?page=' + String(page) + '&size=1000');
    }
    getOriginalById(id: number): Observable<any> {
        const url = environment.apiUrl + 'media' + '/' + id;
        return this.httpClient.get(url);
    }
    downloadById(id: number) {
        const url = environment.apiUrl + 'media' + '/' + id + '/content?forceSave=true';
        window.location.replace(url);
    }
    watchById(id: number): string {
        return environment.apiUrl + 'media' + '/' + id + '/content?forceSave=false';
    }
    getErroredOnConversion(conversion: Conversion): boolean {
        if (conversion.fileSize.includes("0.00") && conversion.fileSize.length == 7) {
            return true;
        }
        else return false;
    }
    canPlayVideo(video: any): boolean {
        if (video.path.includes(".mkv")) {
            return false;
        }
        else {
            return true;
        }
    }
    deleteVideo(id: number): Observable<any> {
        const url = environment.apiUrl + 'media' + '/' + id;
        return this.httpClient.delete(url);
    }
}
