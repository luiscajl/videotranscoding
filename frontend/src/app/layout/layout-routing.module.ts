import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: '', redirectTo: 'dashboard' },
            { path: 'dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) },
            { path: 'upload-videos', loadChildren: () => import('./upload-videos/upload-videos.module').then(m => m.UploadVideosModule) },
            { path: 'watch-video/:id', loadChildren: () => import('./watch-video/watch-video.module').then(m => m.WatchVideoModule), data: { originalId: 'some value' } },
            { path: 'manage-videos', loadChildren: () => import('./manage-videos/manage-videos.module').then(m => m.ManageVideosModule) },
            { path: 'manage-video/:id', loadChildren: () => import('./manage-video/manage-video.module').then(m => m.ManageVideoModule) }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]

})
export class LayoutRoutingModule { }
