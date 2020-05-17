import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { json } from 'express';
@Injectable()
export class UserService {

    constructor() { }

    getUserLogged(): User {
        return JSON.parse(localStorage.getItem('userLogged'));
    }
    setUserLogged(user: User) {
        localStorage.setItem('userLogged', JSON.stringify(user));
    }

}
