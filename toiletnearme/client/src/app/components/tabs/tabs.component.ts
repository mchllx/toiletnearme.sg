import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { MaterialModule } from '../../ui/material.module';

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports: [MaterialModule, MatTabsModule, NgFor, NgIf, AsyncPipe],
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss']
})
export class TabsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
