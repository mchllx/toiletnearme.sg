import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeatherModule } from 'angular-feather';
import { allIcons } from 'angular-feather/icons';
import { FormsComponent } from './forms/forms.component';
import { FormsModule } from '@angular/forms';
import { ContactComponent } from './contact/contact.component';


@NgModule({
  imports: [
    CommonModule,
    FeatherModule.pick(allIcons),
    FormsComponent,
    FormsModule,
    ContactComponent

  ],
  exports: [
    FormsComponent,
    ContactComponent
  ]
})
export class ComponentsModule { }
