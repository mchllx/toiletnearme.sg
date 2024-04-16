import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';

interface sidebarMenu {
  link: string;
  icon: string;
  menu: string;
}

@Component({
  selector: 'app-full',
  templateUrl: './full.component.html',
  styleUrls: ['./full.component.scss']
})
export class FullComponent {

  search: boolean = false;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver) { }

  routerActive: string = "activelink";

  sidebarMenu: sidebarMenu[] = [
    {
      link: "/home",
      icon: "map-pin",
      menu: "Google Map",
    },
    {
      link: "/toilets",
      icon: "droplet",
      menu: "Toilets with Bidet",
    },
    {
      link: "/community",
      icon: "smile",
      menu: "Our Community",
    },
    {
      link: "/login",
      icon: "log-in",
      menu: "Sign In",
    },
    {
      link: "/contact",
      icon: "mail",
      menu: "Contact",
    },
  ]

}
