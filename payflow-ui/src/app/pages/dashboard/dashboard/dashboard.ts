import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  transactions:any[] = [];

  constructor(private api:ApiService){}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(){
    this.api.getTransactions().subscribe((res:any)=>{
      this.transactions = res.data.content || res.data;
    });
  }

}
