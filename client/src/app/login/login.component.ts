import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { UsersService } from '../service/users.service';
import { SHA1 as sha1 } from 'crypto-js';
import { UserDTO } from '../models/userDTO.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService:AuthService, private router:Router, private userService:UsersService, private formBuilder: FormBuilder) { }

  loginForm!:FormGroup
  email:string = ""
  password:string = ""
  loggedInEmail!:string
  errorMsg:string = ""

  ngOnInit(): void {
    if(this.authService.loggedIn()){this.router.navigateByUrl('/home')}

    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  login(){
    this.email = String(this.loginForm.get('email')?.value); 
    this.password = String(this.loginForm.get('password')?.value); 
    let userDTO = new UserDTO(this.email, this.password);
    this.userService.login(userDTO).subscribe(
      res => {
        localStorage.setItem('token', res.jwt)
        this.router.navigateByUrl('/home')
      },
      err => {
        if(err.message.includes("Http failure response")) {
          this.errorMsg = "Unable to connect to the server";
        }else {
          this.errorMsg = "Bad credentials"
        }
      }
    )
  }

}
