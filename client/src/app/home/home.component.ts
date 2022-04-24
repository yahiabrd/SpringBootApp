import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../models/user.model';
import { UsersService } from '../service/users.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private userService:UsersService, private formBuilder: FormBuilder) {}

  addUserForm!: FormGroup;
  updateForm!: FormGroup
  showSection:boolean = false
  showUpdateSection:boolean = false;
  users:Array<User> = []
  successMsg:string = "";
  errorMsg:string = ""

  currentUser:User = {
    firstName : "",
    lastName : "",
    email : "",
    password : ""
  };

  ngOnInit(): void {
    this.addUserForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern("^[A-Za-z0-9]*[0-9][A-Za-z0-9]*$")]]
    })
    this.updateForm = this.formBuilder.group({
      firstName: [''],
      lastName: [''],
      email: [''],
    })
    
    this.refreshUser()
  }

  submitAddUserForm() {
    this.successMsg = ""
    this.errorMsg = ""
    this.userService.addUser(this.addUserForm.value).subscribe(
      success=>{
        this.refreshUser()
        this.successMsg = "User created"
        this.showSection = false;
        this.addUserForm.reset()
      },
      err =>{
        this.errorMsg = err.error
      }
    )
  }

  submitUpdateForm() {
    this.successMsg = ""
    this.errorMsg = ""
    this.userService.updateUser(this.updateForm.value).subscribe(
      success=>{
        this.refreshUser()
        this.successMsg = "User successfully updated"
        this.showUpdateSection = false;
        this.updateForm.reset()
        this.currentUser = {
          firstName : "",
          lastName : "",
          email : "",
          password : ""
        }
      },
      err =>{
        this.errorMsg = err.error
      }
    )
  }

  refreshUser(){
    this.userService.getUsers().subscribe(
      user=>{
        this.users = user
      },
      err => {
        this.errorMsg = err.statusText
        console.log(err)
      }
    )
  }

  updateUser(user:User){
    this.showSection = false;
    this.showUpdateSection = !this.showUpdateSection;
    //init
    this.currentUser = {
      firstName : user.firstName,
      lastName : user.lastName,
      email : user.email,
      password : ""
    }

    this.updateForm = this.formBuilder.group({
      firstName: [this.currentUser.firstName, Validators.required],
      lastName: [this.currentUser.lastName, Validators.required],
      email: [this.currentUser.email, Validators.required],
    })
    //this.updateForm.controls['email'].disable();
  }

  deleteUser(user:User) {
    this.successMsg = ""
    this.errorMsg = ""
    this.showSection = false;
    this.showUpdateSection = false;
    this.addUserForm.reset()

    this.userService.deleteUser(user.email).subscribe();

    for (let index = 0; index < this.users.length; index++) {
      if(this.users[index].email == user.email) {
        this.users.splice(index, 1);
      }
    }

    this.successMsg = "User successfully deleted";
  }


  // addUser2() {
  //   this.users = []
  //   let promise = new Promise<void>((resolve, reject) => {
  //     this.userService.getUsers()
  //       .toPromise()
  //       .then(
  //         res => {
  //           this.users = res;
  //           resolve();
  //         }
  //       );
  //   });
  //   return promise;
  // }
}
