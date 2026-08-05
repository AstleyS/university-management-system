import { Component, OnInit } from '@angular/core';
import { ApiService } from '../core/service/api.service';

@Component({
  selector: 'app-hello',
  standalone: true,
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.scss'
})
export class HelloComponent implements OnInit {

  message = ''

  constructor(private apiService: ApiService) {}

  ngOnInit() {

    this.apiService.getHello().subscribe(response => {
      this.message = response
    })

  }


}
