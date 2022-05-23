import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { StakeholdersRealEstate } from 'src/app/model/users/StakeholdersRealEsate';
import { UpdateUserRealEstates } from 'src/app/model/users/UpdateUserRealEstates';
import { UserDetails } from 'src/app/model/users/UserDetails';
import { RealEstateService } from 'src/app/services/real-estate-service/real-estate.service';
import { UserService } from 'src/app/services/user-service/user.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-manage-real-estates',
  templateUrl: './manage-real-estates.component.html',
  styleUrls: ['./manage-real-estates.component.scss']
})
export class ManageRealEstatesComponent implements OnInit {

  loaded: boolean = false;
  user!: UserDetails;
  displayedColumns: string[] = ['name', 'action'];
  realEstates = new MatTableDataSource<StakeholdersRealEstate>();

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private realEstateService: RealEstateService,
    private errorService: ErrorService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.userService.getUserDetails(this.route.snapshot.params['id']).subscribe(response => {
      this.user = response;
      this.realEstateService.read().subscribe(response => {
        this.realEstates.data = response.map(re => ({
          realEstate: re,
          added: this.user.realEstates.includes(re.id)
        }));
        this.loaded = true;
      });
    });
  }

  addRealEstate(re: StakeholdersRealEstate): void {
    if (this.user.realEstates.includes(re.realEstate.id)) {
      return;
    }
    re.added = true;
    this.user.realEstates.push(re.realEstate.id);
  }

  removeRealEstate(re: StakeholdersRealEstate): void {
    if (!this.user.realEstates.includes(re.realEstate.id)) {
      return;
    }
    re.added = false;
    this.user.realEstates = this.user.realEstates.filter(x => x != re.realEstate.id);
  }

  saveChanges(): void {
    const request: UpdateUserRealEstates = {
      realEstates: this.user.realEstates
    };
    this.userService.updateRealEstates(this.user.id, request).subscribe({
      next: _ => this.router.navigate(['/users']),
      error: err => this.errorService.handle(err)
    });
  }

}
