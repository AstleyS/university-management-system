import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HelloComponent} from './hello/hello.component';
import {RegisterComponent} from './features/auth/components/register/register.component';
import {LoginComponent} from './features/auth/components/login/login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {}
