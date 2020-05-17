import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { NgbModal, ModalDismissReasons, NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../../shared/services/user.service';
import { User } from '../../../shared/models/user.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SidebarComponent } from '../sidebar/sidebar.component';



@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    pushRightClass: string = 'push-right';
    modalRef: NgbModalRef;
    closeResult: string;
    userLogged: User;
    editProfile: FormGroup;
    editUser: EditUser;
    constructor(public router: Router, private modalService: NgbModal, private userService: UserService) {
        this.router.events.subscribe(val => {
            if (
                val instanceof NavigationEnd &&
                window.innerWidth <= 992 &&
                this.isToggled()
            ) {
                this.toggleSidebar();
            }
        });
    }


    ngOnInit() {
        this.userLogged = this.userService.getUserLogged();
        // this.editProfile = new FormGroup({
        //     nick: new FormControl(this.userLogged.name),
        //     email: new FormControl(this.userLogged.email),
        //     hashedPassword: new FormControl('', [
        //         Validators.required,
        //         Validators.minLength(8)
        //     ]),
        //     passwordRepeat: new FormControl('', [Validators.required, matchOtherValidator('hashedPassword')])
        // })
    }
    onSubmit(form: FormGroup) {
        let valuesForm: any = form.value;
        this.editUser = valuesForm;
        // this.userService.editUser(this.editUser, this.userService.getLoggedUser().userId
        // ).subscribe(
        //     result => {
        //         console.log(result)
        //         this.userService.loginUser(this.editUser.nick, this.editUser.hashedPassword).subscribe();
        //         this.userService.setUserLogged(result);;
        //         this.ngOnInit();
        //     },
        //     error => console.log(error))
        // this.modalRef.close();
    }

    isToggled(): boolean {
        const dom: Element = document.querySelector('body');
        return dom.classList.contains(this.pushRightClass);
    }

    toggleSidebar() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle(this.pushRightClass);
    }

    rltAndLtr() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle('rtl');
    }

    onLoggedout() {
        // this.userService.deleteUserLogged();
        // this.router.navigate(['/login']);
    }

    open(content) {
        this.ngOnInit();
        this.modalRef = this.modalService.open(content);
        this.modalRef.result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

} export interface EditUser {
    nick: string;
    email: string;
    hashedPassword: string;
}
