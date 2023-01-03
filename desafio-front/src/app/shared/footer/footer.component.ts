import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  @Input() class: string = 'footer-light' // Default class 
  public today: number = Date.now();

  constructor() { }

  ngOnInit(): void {
  }

}
