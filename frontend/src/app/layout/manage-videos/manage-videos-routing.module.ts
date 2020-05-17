import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ManageVideosComponent } from './manage-videos.component';

const routes: Routes = [
    {
        path: '',
        component: ManageVideosComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ManageVideosRoutingModule {}
