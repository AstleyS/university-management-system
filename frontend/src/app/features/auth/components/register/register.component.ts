import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {RegisterRequest} from '../../models/register.model';
import {Role} from '../../../../core/enums/Role';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerForm: FormGroup;
  errorMessage = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {

    this.registerForm = fb.group({

      username: [
        '',
        [Validators.required, Validators.minLength(3), Validators.maxLength(15)]
      ],

      password: [
        '',
        [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.#_\-])[A-Za-z\d@$!%*?&.#_\-]{8,}$/)]
      ],

      role: ['Select a role', [Validators.required]]

    });

  }

  onSubmit() {

    if (this.registerForm.invalid) return;

    this.loading = true;
    this.errorMessage = '';

    const formValue = this.registerForm.value;
    const payload: RegisterRequest = {
      username: formValue.username,
      password: formValue.password,
      // backend expects a collection (Set<Role>); send JSON array
      roles: [formValue.role as Role]
    };

    this.authService.register(
      payload
    ).subscribe({

      next: () => {
        this.router.navigate(["/login"]).then(r => this.loading = false);
      },

      error: (error) => {
        this.loading = false;
        console.error('Register error', error);

        // HttpErrorResponse.error can be a string or an object. Try to extract a useful message.
        let message = 'Registration failed';
        if (error?.error) {
          if (typeof error.error === 'string') {
            message = error.error;
          } else if (typeof error.error === 'object') {
            // common patterns: { message: '...' } or validation map
            if (error.error.message) message = error.error.message;
            else message = JSON.stringify(error.error);
          }
        } else if (error?.message) {
          message = error.message;
        } else if (error?.status && error?.statusText) {
          message = `${error.status} ${error.statusText}`;
        }

        this.errorMessage = message;
      }


    })

  }

  get username(){

    return this.registerForm.get('username');

  }


  get password(){

    return this.registerForm.get('password');

  }


}
