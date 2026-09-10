import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../core/models/user.model';
import { AuthService } from '../../core/services/auth';
import { UserService } from '../../core/services/user';
import { RewardSummary } from './reward-summary/reward-summary';

// A4: Profile -> RewardSummary component hierarchy.
@Component({
  imports: [ReactiveFormsModule, RewardSummary],
  selector: 'app-profile',
  styleUrl: './profile.css',
  templateUrl: './profile.html',
})
export class Profile implements OnInit {
  private fb = inject(FormBuilder);

  user: User | null = null;
  loading = true;
  saving = false;
  successMessage = '';

  // A8/A9: reactive form for profile editing with validation.
  profileForm = this.fb.nonNullable.group({
    name: ['', Validators.required],
    phone: [''],
    profileImage: [''],
  });

  constructor(private auth: AuthService, private userService: UserService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    const currentUser = this.auth.currentUser();
    if (!currentUser) {
      this.loading = false;
      return;
    }

    this.userService.getUser(currentUser.id).subscribe({
      next: (user) => {
        this.user = user;
        this.profileForm.patchValue({ name: user.name, phone: user.phone, profileImage: user.profileImage });
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  save(): void {
    if (this.profileForm.invalid || !this.user) {
      this.profileForm.markAllAsTouched();
      return;
    }

    this.saving = true;
    this.successMessage = '';
    this.userService.updateUser(this.user.id, this.profileForm.getRawValue()).subscribe((user) => {
      this.user = user;
      this.saving = false;
      this.successMessage = 'Profile updated!';
      this.cdr.markForCheck();
    });
  }
}
