import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { RealEstate } from 'src/app/model/real-esatate/RealEstate';
import { RealEstateService } from 'src/app/services/real-estate-service/real-estate.service';

@Component({
  selector: 'app-view-real-estates',
  templateUrl: './view-real-estates.component.html',
  styleUrls: ['./view-real-estates.component.scss']
})
export class ViewRealEstatesComponent implements OnInit {

  displayedColumns: string[] = ['name', 'action'];
  realEstates = new MatTableDataSource<RealEstate>();

  constructor(private realEstateService: RealEstateService, private router: Router) { }

  ngOnInit(): void {
    this.realEstateService.read().subscribe(response => {
      this.realEstates.data = response;
    });
  }

  configureDevices(re: RealEstate): void {
    this.router.navigate([`real-estates/${re.id}/configure-devices`])
  }

}
