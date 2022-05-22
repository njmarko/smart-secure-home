import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { CreateRealEstateRequest } from 'src/app/model/real-esatate/CreateRealEstateRequest';
import { Stakeholder } from 'src/app/model/users/Stakeholder';
import { RealEstateService } from 'src/app/services/real-estate-service/real-estate.service';
import { UserService } from 'src/app/services/user-service/user.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-create-real-estate',
  templateUrl: './create-real-estate.component.html',
  styleUrls: ['./create-real-estate.component.scss']
})
export class CreateRealEstateComponent implements OnInit {

  form: FormGroup;
  searchForm: FormGroup;

  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'role', 'action'];
  allUsers: Stakeholder[] = [];
  users = new MatTableDataSource<Stakeholder>();

  stakeholders: number[] = [];

  constructor(
    private userService: UserService,
    private realEstateService: RealEstateService,
    private formBuilder: FormBuilder,
    private errorService: ErrorService,
    private snackbar: MatSnackBar
  ) {
    this.searchForm = this.formBuilder.group({
      query: [''],
    });
    this.form = this.formBuilder.group({
      name: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchStakeholders();
  }

  createRealEstate(): void {
    if (!this.form.valid) {
      return;
    }
    const request: CreateRealEstateRequest = {
      name: this.form.value.name,
      stakeholders: this.stakeholders
    }
    this.realEstateService.create(request).subscribe({
      next: _ => {
        this.form.reset();
        this.searchForm.reset();
        this.fetchStakeholders();
        this.snackbar.open("Succesffully created a new real estate.", "Confirm", { duration: 3000 });
      },
      error: err => this.errorService.handle(err)
    })
  }

  fetchStakeholders(): void {
    this.userService.getUsersBellowMe().subscribe(response => {
      const data: Stakeholder[] = response.map(user => ({
        user: user,
        added: false
      }))
      this.allUsers = data;
      this.users.data = data;
    })
  }

  search(): void {
    const lq = this.searchForm.value.query.toLowerCase();
    this.users.data = this.allUsers.filter(s => {
      const user = s.user;
      return user.firstName.toLowerCase().includes(lq) || user.lastName.toLowerCase().includes(lq) || user.email.toLowerCase().includes(lq) || user.role.toLocaleLowerCase().includes(lq);
    })
  }

  addStakeholder(user: Stakeholder): void {
    if (user.added) {
      return;
    }
    user.added = true;
    this.stakeholders.push(user.user.id);
  }

  removeStakeholder(user: Stakeholder): void {
    if (!user.added) {
      return;
    }
    user.added = false;
    this.stakeholders = this.stakeholders.filter(id => id != user.user.id);
  }

}
