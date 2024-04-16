import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models';
import { AuthService } from 'src/app/services/auth.service';
import { UserStore } from 'src/app/user.store';

@Component({
  selector: 'app-forms',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatCheckboxModule, MatRadioModule, MatButtonModule],
  templateUrl: './forms.component.html',
})
export class FormsComponent implements OnInit {

  private fb: FormBuilder = inject(FormBuilder)
  private router = inject(Router)
  private authSvc = inject(AuthService)
  private activatedRoute = inject(ActivatedRoute)
  private userStore = inject(UserStore)
  
  checked = true;
  user$!: Promise<void>
  newUser!: User

  form!: FormGroup
  
  constructor() { }

  ngOnInit(): void {
    this.form = this.createSignInForm()
  }

  onBack(): void {
    this.router.navigate(['/flexy/home']);
  }

  private createSignInForm(): FormGroup {
    return this.fb.group({
      email: this.fb.control<string>('', [Validators.required, Validators.minLength(4)]),
      password: this.fb.control<string>('', [Validators.required, Validators.minLength(4)])
    })
  }

  login() : void  {
    const user = this.form.value as User
    console.log('>>> Verifying:', user)
    
    this.user$ = this.authSvc.postLogin(user)
    .then(
      value => {
        alert(`successful: ${value.email}, ${value.createdOn}, ${value.jwtToken},`)
        this.newUser = value

        console.log('>>>Registered:', value.jwtToken)
      })
      .catch(
        error => {
          console.log('Server error', error)
          alert('Invalid request')
        }
      )

      this.userStore.addToStorage(this.newUser)
  }
}
