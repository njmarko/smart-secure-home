import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { Observer } from 'rxjs';
import { DeviceMessage } from 'src/app/model/DeviceMessage';
import { SearchMessagesRequest } from 'src/app/model/SearchMessagesRequest';
import { DeviceMessageService } from 'src/app/services/device-message-service/device-message.service';
import { ErrorService } from 'src/app/services/error-service/error.service';

@Component({
  selector: 'app-message-view',
  templateUrl: './message-view.component.html',
  styleUrls: ['./message-view.component.css']
})
export class MessageViewComponent implements OnInit {
  displayedColumns: string[] = [
    'messageType',
    'timestamp',
    'content',
    'deviceId'
  ];

  dataSource: MatTableDataSource<DeviceMessage> =
    new MatTableDataSource<DeviceMessage>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  dateForm: FormGroup;
  form: FormGroup;
  realEstateId: number;

  constructor(
    private deviceMessageService: DeviceMessageService,
    private errorService: ErrorService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute
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
    if (!this.form.valid) {
      return;
    }
    this.fetchData(0, this.defaultPageSize);
  }

  fetchData(pageIdx: number, pageSize: number): void {
    const request: SearchMessagesRequest = {
      realEstateId: this.realEstateId,
      messageType: this.form.value.type,
      regex: this.form.value.regex,
      from: moment(this.dateForm.value.from).format('yyy-MM-DD'),
      to: moment(this.dateForm.value.to).format('yyy-MM-DD')
    };
    this.waitingResults = true;
    this.deviceMessageService
      .read(request, pageIdx, pageSize)
      .subscribe((page) => {
        this.pageNum = page.pageable.pageNumber;
        this.pageSize = page.pageable.pageSize;
        this.totalPages = page.totalPages;
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
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
