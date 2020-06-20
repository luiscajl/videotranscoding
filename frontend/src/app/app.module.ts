import { environment } from './../environments/environment';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { UserService } from './shared/services/user.service'
import { UploadFileService } from './shared/services/upload-file.service';
import { MediaService } from './shared/services/media.service';

import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';

const oktaConfig = {
    issuer: environment.oktaIssuer,
    redirectUri: window.location.origin + '/implicit/callback',
    clientId: environment.oktaClientId,
    scopes: ['openid', 'profile']
};
@NgModule({
    imports: [
        CommonModule,
        BrowserModule.withServerTransition({ appId: 'serverApp' }),
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        ReactiveFormsModule,
        OktaAuthModule,
        FormsModule,
        BrowserModule.withServerTransition({ appId: 'my-app' }),
        NgbModule

    ],
    declarations: [AppComponent],
    providers: [
        UserService,
        MediaService,
        UploadFileService,
        { provide: OKTA_CONFIG, useValue: oktaConfig },
        { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
    bootstrap: [AppComponent]
})
export class AppModule { }
