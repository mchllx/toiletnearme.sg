import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MaterialModule } from '../../ui/material.module';

@Component({
  selector: 'app-buttons',
  standalone: true,
  imports: [MaterialModule, MatButtonModule, MatTooltipModule, MatIconModule],
  templateUrl: './buttons.component.html',
  styleUrls: ['./buttons.component.scss']
})
export class ButtonsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
