import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import User from 'src/app/model/users/User';
import { UserService } from 'src/app/services/user-service/user.service';
import { ConfirmationService } from 'src/app/shared/confirmation-service/confirmation.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrls: ['./users-view.component.scss']
})
export class UsersViewComponent implements OnInit {
  displayedColumns: string[] = [
    'FirstName',
    'LastName',
    'Username',
    'HashedPassword',
    'Email',
    'Enabled',
    'LastPasswordResetDate',
    'RealEstates',
    'Roles',
    'Actions',
  ];

  dataSource: MatTableDataSource<User> = new MatTableDataSource<User>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  constructor(
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private errorService: ErrorService,
  ) { }

  ngOnInit(): void {
    // TODO: GET Pageable Users from DB
    this.dataSource.data = [{
      username: "brka",
      password: "DHJAHSIKDBJA",
      firstName: "Dusan",
      lastName: "Brkic",
      email: "dusanbrk@gmail.com",
      enabled: true,
      lastPasswordResetDate: new Date(),
      realEstates: [{ name: "Kuca u Sremskoj Kamenici" }],
      roles: [{ name: "ROLE_ADMIN", privileges: [{ name: "BRISANJE" }, { name: "IZMENA" }] }, { name: "SUPER_ADMIN", privileges: [{ name: "MOTANJE_KABLOVA" }] }],
    }, {
      username: "brka",
      password: "DHJAHSIKDBJA",
      firstName: "Dusan",
      lastName: "Brkic",
      email: "dusanbrk@gmail.com",
      enabled: true,
      lastPasswordResetDate: new Date(),
      realEstates: [{ name: "Kuca u Sremskoj Kamenici" }],
      roles: [{ name: "ROLE_ADMIN", privileges: [{ name: "BRISANJE" }, { name: "IZMENA" }] }, { name: "SUPER_ADMIN", privileges: [{ name: "MOTANJE_KABLOVA" }] }],
  }]

    // this.fetchData(0, this.defaultPageSize);
  }

  fetchData(pageIdx: number, pageSize: number): void {
    // this.waitingResults = true;
    // this.certificateService.read(pageIdx, pageSize).subscribe((page) => {
    //   this.pageNum = page.pageable.pageNumber;
    //   this.pageSize = page.pageable.pageSize;
    //   this.totalPages = page.totalPages;
    //   this.dataSource.data = page.content;
    //   this.totalElements = page.totalElements;
    //   this.waitingResults = false;
    // });
  }

  onSelectPage(event: any): void {
  //   this.fetchData(event.pageIndex, event.pageSize);
  }

  // getDefaultEntityServiceHandler<TResponse = void>(
  //   page?: number
  // ): Partial<Observer<TResponse>> {
  //   return {
  //     next: (_) => {
  //       this.fetchData(page ?? this.pageNum, this.pageSize);
  //     },
  //     error: (err) => {
  //       this.errorService.handle(err);
  //       this.waitingResults = false;
  //     },
  //   };
  // }

  modifyRole(username : string){

  }

  deleteUser(username : string){

  }

}
