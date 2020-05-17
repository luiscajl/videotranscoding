import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WatchVideoComponent } from './watch-video.component';

const routes: Routes = [
    {
        path: '',
        component: WatchVideoComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WatchVideoRoutingModule {}
