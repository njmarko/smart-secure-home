import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Observer } from 'rxjs';
import { Alarm } from 'src/app/model/log/Alarm';
import { EventBusService } from 'src/app/services/event-bus/event-bus.service';
import { LogService } from 'src/app/servies/log-service/log.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-alarms-view',
  templateUrl: './alarms-view.component.html',
  styleUrls: ['./alarms-view.component.scss']
})
export class AlarmsViewComponent implements OnInit {
  displayedColumns: string[] = [
    'timestamp',
    'message',
  ];

  dataSource: MatTableDataSource<Alarm> =
    new MatTableDataSource<Alarm>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;

  constructor(
    private logService: LogService,
    private errorService: ErrorService,
    private eventBus: EventBusService
  ) { }

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
    this.eventBus.onAlarmOccurred.subscribe(_ => this.fetchData(0, this.defaultPageSize));
  }

  fetchData(pageIdx: number, pageSize: number): void {
    this.waitingResults = true;
    this.logService
      .readAlarms(pageIdx, pageSize)
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
