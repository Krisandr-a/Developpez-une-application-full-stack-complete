import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
    ) {
    this.loginForm = this.fb.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    // login is email or username
    if (this.loginForm.valid) {
      const { login, password } = this.loginForm.value;

      this.authService.login({ login, password }).subscribe({
        next: (response) => {
          this.authService.saveToken(response.token);
          this.errorMessage = null;
          this.router.navigate(['/articles']); // or your home/dashboard route
        },
        error: (err) => {
          this.errorMessage = 'Invalid login credentials';
          console.error('Login error:', err);
        }
      });
    }
  }


}
