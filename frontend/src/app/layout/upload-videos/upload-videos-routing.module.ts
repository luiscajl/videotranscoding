import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UploadVideosComponent } from './upload-videos.component';

const routes: Routes = [
    {
        path: '',
        component: UploadVideosComponent
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
        HttpClientModule],
    exports: [RouterModule]
})
export class UploadVideosRoutingModule { }
