import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../../services/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  email:string="";
  password:string="";

  constructor(
    private api:ApiService,
    private router:Router
  ){}

login(){

  console.log("LOGIN CLICKED");

  const payload={
    email:this.email,
    password:this.password
  };

  this.api.login(payload).subscribe({
    next:(res:any)=>{
      console.log(res);
      localStorage.setItem("token",res.data.token);
      this.router.navigate(['/dashboard']);
    },
    error:(err)=>{
      console.error(err);
      alert("Login failed");
    }
  });


}

}

