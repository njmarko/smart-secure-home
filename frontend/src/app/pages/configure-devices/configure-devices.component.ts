import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Device } from 'src/app/model/real-esatate/Device';
import { RealEstateService } from 'src/app/services/real-estate-service/real-estate.service';

@Component({
  selector: 'app-configure-devices',
  templateUrl: './configure-devices.component.html',
  styleUrls: ['./configure-devices.component.scss']
})
export class ConfigureDevicesComponent implements OnInit {

  realEstateId: number;
  devices: Device[] = [];
  displayedColumns: string[] = ['name', 'regexFilter', 'sendRate']
  form: FormGroup;

  constructor(private route: ActivatedRoute, private realEstateService: RealEstateService, private fb: FormBuilder) {
    this.realEstateId = this.route.snapshot.params['id'];
    this.form = fb.group({
      name: ['', Validators.required],
      regexFilter: ['', Validators.required],
      sendRate: [1000, Validators.compose([Validators.required, Validators.min(0)])]
    })
  }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.realEstateService.readDevices(this.realEstateId).subscribe(response => {
      this.devices = response ?? [];
      console.log(this.devices);
    })
  }

  configureDevice(): void {
    if (!this.form.valid) {
      return;
    }
    this.realEstateService.configureDevice(this.realEstateId, this.form.value).subscribe(_ => this.fetchData());
  }

}
