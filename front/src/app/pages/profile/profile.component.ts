  import { Component, OnInit } from '@angular/core';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { ProfileService, UserProfile } from 'src/app/services/profile.service';

  @Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
  })
  export class ProfileComponent implements OnInit {
    profileForm!: FormGroup;

    constructor(private fb: FormBuilder, private profileService: ProfileService) { }

    ngOnInit(): void {
      this.profileForm = this.fb.group({
        name: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required]
      });

      this.profileService.getUserProfile().subscribe({
        next: (profile: UserProfile) => {
          this.profileForm.patchValue({
            name: profile.name,
            email: profile.email
          });
        },
        error: err => {
          console.error('Failed to load profile:', err);
        }
      });

    }

    onSubmit(): void {
      if (this.profileForm.valid) {
        console.log(this.profileForm.value);
        // Submit to API or service
      }
    }

  }
