import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'

import {User} from '../models/user.model'
import { Observable } from 'rxjs';
import { UserDTO } from '../models/userDTO.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private userUrl = 'http://localhost:8081'

  constructor(private http:HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/users')
  }

  addUser(user:User) : Observable<User> {
    return <Observable<User>> this.http.post(this.userUrl + "/user/add", user)
  }

  deleteUser(email:string) : Observable<User> {
    return <Observable<User>> this.http.delete(this.userUrl + "/user/delete/" + email)
  }

  updateUser(user:User) : Observable<User> {
    return <Observable<User>> this.http.put(this.userUrl + "/user/update", user)
  }

  getUser(email:string) : Observable<User> {
    return this.http.get<User>(this.userUrl + "/user/email/" + email)
  }

  login(userDTO:UserDTO) : Observable<any>{
    return this.http.post(this.userUrl + "/user/login", userDTO);
  }
}
