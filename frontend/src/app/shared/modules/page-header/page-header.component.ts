import { Component, OnInit, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-page-header',
    templateUrl: './page-header.component.html',
    styleUrls: ['./page-header.component.scss']
})
export class PageHeaderComponent implements OnInit {
    @Input() heading: string;
    @Input() icon: string;
    @Input() heading2: string;
    @Input() icon2: string;
    @Input() uriRedirect: string;
    secondItem: boolean;
    constructor() {
    }

    ngOnInit() {
        if (this.heading2 != undefined) {
            this.secondItem = true;
        }
    }
}
