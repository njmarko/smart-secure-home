import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import * as moment from 'moment';
import { Observer } from 'rxjs';
import { Log } from 'src/app/model/log/Log';
import { SearchLogsRequest } from 'src/app/model/log/SearchLogsRequest';
import { LogService } from 'src/app/services/log-service/log.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-logs-view',
  templateUrl: './logs-view.component.html',
  styleUrls: ['./logs-view.component.scss']
})
export class LogsViewComponent implements OnInit {
  displayedColumns: string[] = [
    'timestamp',
    'logType',
    'content',
  ];

  dataSource: MatTableDataSource<Log> =
    new MatTableDataSource<Log>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  dateForm: FormGroup;
  form: FormGroup;

  constructor(
    private logService: LogService,
    private errorService: ErrorService,
    private formBuilder: FormBuilder
  ) {
    this.dateForm = this.formBuilder.group({
      from: [new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000), Validators.required],
      to: [new Date(), Validators.required]
    })
    this.form = this.formBuilder.group({
      regex: [''],
      type: ['INFO', Validators.required]
    });
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
    const request: SearchLogsRequest = {
      type: this.form.value.type,
      regex: this.form.value.regex,
      from: moment(this.dateForm.value.from).format('yyy-MM-DD'),
      to: moment(this.dateForm.value.to).format('yyy-MM-DD')
    };
    this.waitingResults = true;
    this.logService
      .readLogs(request, pageIdx, pageSize)
      .subscribe((page) => {
        this.pageNum = page.pageable.pageNumber;
        this.pageSize = page.pageable.pageSize;
        this.totalPages = page.totalPages;
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
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
