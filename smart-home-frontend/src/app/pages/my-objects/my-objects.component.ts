import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-objects',
  templateUrl: './my-objects.component.html',
  styleUrls: ['./my-objects.component.css']
})
export class MyObjectsComponent implements OnInit {

  myObjects = [
    {
      name: "Kuca"
    },
    {
      name: "Supa"
    },
    {
      name: "Pusnica"
    },
    {
      name: "Kuca za kera"
    },
    {
      name: "Pomocni objekat"
    }
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
