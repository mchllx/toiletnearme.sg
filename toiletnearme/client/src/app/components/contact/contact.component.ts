import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

import emailjs, { type EmailJSResponseStatus } from '@emailjs/browser';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatCheckboxModule, MatRadioModule, MatButtonModule],
  templateUrl: './contact.component.html',
})
export class ContactComponent implements OnInit {

  private fb: FormBuilder = inject(FormBuilder)
  private router = inject(Router)
  private authSvc = inject(AuthService)
  private activatedRoute = inject(ActivatedRoute)

  form!: FormGroup
  
  constructor() { }

  ngOnInit(): void {
    this.form = this.createContactForm()
  }

  onBack(): void {
    this.router.navigate(['/home']);
  }

  public sendEmail(event: Event) {
    console.log("preparing email:", event)

    emailjs
      .sendForm('service_vxjsa7b', 'template_cizn3s3', event.target as HTMLFormElement, {
        publicKey: '92z_-v16LrIjP5Mya',
      })
      .then(
        () => {
          alert("Thank you for your submission!")
          console.log('SUCCESS!')
        },
        (error) => {
          alert("Something went wrong with the server")
          console.log('FAILED...', (error as EmailJSResponseStatus).text);
        },
      )
  }

  private createContactForm(): FormGroup {
    return this.fb.group({
      user_name: this.fb.control<string>('', [Validators.required, Validators.minLength(2)]),
      user_email: this.fb.control<string>('', [Validators.required, Validators.minLength(4)]),
      message: this.fb.control<string>('', [Validators.required, Validators.minLength(1), Validators.maxLength(120)])
    })
  }
}
