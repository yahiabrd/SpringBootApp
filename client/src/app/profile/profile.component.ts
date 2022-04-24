import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../models/user.model';
import { UsersService } from '../service/users.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private router:Router, private route:ActivatedRoute, private usersService:UsersService) { }

  emailId:string = ""
  profile!:User;

  ngOnInit(): void {
    this.route.params.subscribe(
      params=>{
        this.emailId = params.profileId
      }
    )
    this.usersService.getUser(this.emailId).subscribe(
      success => {
        this.profile = success
      },
      err => {
        this.router.navigateByUrl('/home')
      }
    )
  }
}
