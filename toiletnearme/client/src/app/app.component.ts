import { AfterViewInit, Component, Input, OnInit, ViewChild, inject } from '@angular/core';
import { Observable, of, switchMap, tap } from 'rxjs';
import { Router } from '@angular/router';
import {} from './models';
import { CartStore } from './toilet.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
 
  ngOnInit(): void { 
  }
}
