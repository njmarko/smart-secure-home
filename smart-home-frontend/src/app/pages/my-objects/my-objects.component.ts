import { Component, OnInit } from '@angular/core';
import { RealEstate } from 'src/app/model/RealEstate';
import { ErrorService } from 'src/app/services/error-service/error.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-my-objects',
  templateUrl: './my-objects.component.html',
  styleUrls: ['./my-objects.component.css']
})
export class MyObjectsComponent implements OnInit {

  myObjects: RealEstate[] = [];

  constructor(private userService: UserService, private errorService: ErrorService) { }

  ngOnInit(): void {
    this.userService.getMyRealEstates().subscribe({
      next: response => this.myObjects = response,
      error: err => this.errorService.handle(err)
    });
  }

}
