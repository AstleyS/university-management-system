import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {TokenService} from '../../services/token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {

    this.loginForm = this.fb.group({
      username: [
        '',
        [
          Validators.required
        ]
      ],

      password: [
        '',
        [
          Validators.required
        ]
      ]

    });
  }

  onSubmit(): void {


    if(this.loginForm.invalid) return;

    this.loading = true;
    this.errorMessage = "";

    this.authService.login(
      this.loginForm.value
    )
      .subscribe({

        next: (response) => {

          this.tokenService.saveToken(
            response.token
          );

          this.router.navigate(['/dashboard']);

        },


        error: (error) => {

          this.loading = false;
          this.errorMessage =
            error.error?.message
            ??
            "Invalid credentials";
        },

        complete: () => {
          this.loading = false;
        }
      });
  }

  get username(){

    return this.loginForm.get('username');

  }

  get password(){

    return this.loginForm.get('password');

  }
}
