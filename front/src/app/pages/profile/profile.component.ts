  import { Component, OnInit } from '@angular/core';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { ProfileService, UserProfile } from 'src/app/services/profile.service';
  import { SubscriptionService, UserThemeSubscriptionDto } from 'src/app/services/subscription.service';

  @Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
  })
  export class ProfileComponent implements OnInit {
    profileForm!: FormGroup;
    subscribedThemes: UserThemeSubscriptionDto[] = [];

    constructor(
      private fb: FormBuilder,
      private profileService: ProfileService,
      private subscriptionService: SubscriptionService
      ) { }

    ngOnInit(): void {
      // Initialize the form with empty/default values
      this.profileForm = this.fb.group({
        name: [''],
        email: [''],
        password: ['']
      });

      // Fetch the user profile and patch the form + set validators
      this.profileService.getUserProfile().subscribe({
        next: (profile: UserProfile) => {
          // Update values
          this.profileForm.patchValue({
            name: profile.name,
            email: profile.email
          });

          // Apply email validator only if there's a value
          const emailControl = this.profileForm.get('email');
          if (emailControl) {
            const validators = profile.email?.trim() ? [Validators.email] : [];
            emailControl.setValidators(validators);
            emailControl.updateValueAndValidity();
          }
        },
        error: err => {
          console.error('Failed to load profile:', err);
        }
      });

      // Fetch user's subscribed themes
      this.subscriptionService.getUserSubscriptions().subscribe({
        next: (subs) => {
          this.subscribedThemes = subs;
        },
        error: (err) => {
          console.error('Error loading subscriptions:', err);
        }
      });
    }

    onSubmit(): void {
      if (this.profileForm.valid) {
        const formValue = this.profileForm.value;

        // Remove password if empty
        if (!formValue.password) {
          delete formValue.password;
        }

        this.profileService.updateUserProfile(formValue).subscribe({
          next: (response) => {
            // Always update the token
            localStorage.setItem('authToken', response.token);
            alert('Profil mis à jour.');
          },
          error: (error) => {
            console.error('Erreur de mise à jour du profil :', error);
          }
        });
      }
    }

    get isFormDisabled(): boolean {
      if (!this.profileForm) return true; // avoid access before init
      const { name, email, password } = this.profileForm.value;
      const allFieldsEmpty = !name?.trim() && !email?.trim() && !password?.trim();
      return this.profileForm.invalid || allFieldsEmpty;
    }

  }
