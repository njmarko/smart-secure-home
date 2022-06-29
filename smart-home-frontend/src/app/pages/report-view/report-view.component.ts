import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { Observer } from 'rxjs';
import { DeviceReport } from 'src/app/model/DeviceReport';
import { SearchDeviceReportRequest } from 'src/app/model/SearchDeviceReportRequest';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CurrentUserService } from 'src/app/services/currrent-user-service/current-user.service';
import { DeviceReportService } from 'src/app/services/device-report-service/device-report.service';
import { ErrorService } from 'src/app/services/error-service/error.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-report-view',
  templateUrl: './report-view.component.html',
  styleUrls: ['./report-view.component.css']
})
export class ReportViewComponent implements OnInit {

  displayedColumns: string[] = [
    'deviceName',
    'realEstateName',
    'alarmNumber',
  ];

  dataSource: MatTableDataSource<DeviceReport> =
    new MatTableDataSource<DeviceReport>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  dateForm: FormGroup;
  form: FormGroup;
  realEstateId: number;
  loggedInUser : any

  constructor(
    private deviceReportService: DeviceReportService,
    private errorService: ErrorService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private currUserSer: CurrentUserService,
  ) {
    this.dateForm = this.formBuilder.group({
      from: [new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000), Validators.required],
      to: [new Date(), Validators.required]
    })
    this.form = this.formBuilder.group({
      regex: [''],
      type: ['INFO', Validators.required]
    });
    this.realEstateId = this.route.snapshot.params['id'];
    this.loggedInUser = currUserSer.getCurrentUser();
  }

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
  }

  onReset(): void {
    this.form.reset();
    this.dateForm.reset();
  }

  onSubmit(): void {
    if (!this.form.valid) {
      return;
    }
    if (!this.dateForm.valid) {
      return;
    }
    this.fetchData(0, this.defaultPageSize);
  }

  onDateRangeSubmit(): void {
    if (!this.dateForm.valid) {
      return;
    }
    this.fetchData(0, this.defaultPageSize);
  }

  fetchData(pageIdx: number, pageSize: number): void {
    const request: SearchDeviceReportRequest = {
      regex: this.form.value.regex,
      from: moment(this.dateForm.value.from).format('yyy-MM-DD'),
      to: moment(this.dateForm.value.to).format('yyy-MM-DD')
    };
    this.waitingResults = true;
    this.deviceReportService
      .read(request, pageIdx, pageSize)
      .subscribe((page) => {
        this.pageNum = 1;
        this.pageSize = 10;
        this.totalPages = 1;
        this.dataSource.data = page;
        this.totalElements = 7;
        this.waitingResults = false;
      });
  }

  onSelectPage(event: any): void {
    this.fetchData(event.pageIndex, event.pageSize);
  }

  getDefaultEntityServiceHandler<TResponse = void>(
    page?: number
  ): Partial<Observer<TResponse>> {
    return {
      next: (_) => {
        this.fetchData(page ?? this.pageNum, this.pageSize);
      },
      error: (err) => {
        this.errorService.handle(err);
        this.waitingResults = false;
      },
    };
  }
}
