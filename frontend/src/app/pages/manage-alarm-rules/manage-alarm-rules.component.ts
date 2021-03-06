import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Observer } from 'rxjs';
import { AlarmRuleDialogComponent } from 'src/app/components/alarm-rule-dialog/alarm-rule-dialog.component';
import { AlarmRule } from 'src/app/model/log/AlarmRule';
import { AlarmRuleService } from 'src/app/services/alarm-rule-service/alarm-rule.service';
import { ErrorService } from 'src/app/shared/error-service/error.service';

@Component({
  selector: 'app-manage-alarm-rules',
  templateUrl: './manage-alarm-rules.component.html',
  styleUrls: ['./manage-alarm-rules.component.scss']
})
export class ManageAlarmRulesComponent implements OnInit {
  displayedColumns: string[] = [
    'name',
    'message',
    'viewDetails',
    'deactivate'
  ];

  dataSource: MatTableDataSource<AlarmRule> =
    new MatTableDataSource<AlarmRule>();
  pageNum: number = 0;
  pageSize: number = 0;
  totalPages: number = 0;
  defaultPageSize: number = 10;
  totalElements: number = 0;
  waitingResults: boolean = true;
  loading: boolean = false;

  form: FormGroup;

  constructor(
    private alarmRuleService: AlarmRuleService,
    private errorService: ErrorService,
    private formBuilder: FormBuilder,
    public dialog: MatDialog
  ) {
    this.form = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required, Validators.maxLength(2000)])],
      when: ['', Validators.compose([Validators.required, Validators.maxLength(2000)])],
      then: ['', Validators.maxLength(2000)],
      message: ['', Validators.compose([Validators.required, Validators.maxLength(2000)])]
    });
  }

  ngOnInit(): void {
    this.fetchData(0, this.defaultPageSize);
  }

  fetchData(pageIdx: number, pageSize: number): void {
    this.waitingResults = true;
    this.alarmRuleService
      .read(pageIdx, pageSize)
      .subscribe((page) => {
        this.pageNum = page.pageable.pageNumber;
        this.pageSize = page.pageable.pageSize;
        this.totalPages = page.totalPages;
        this.dataSource.data = page.content;
        this.totalElements = page.totalElements;
        this.waitingResults = false;
      });
  }

  onSubmit(): void {
    if (!this.form.valid) {
      return;
    }
    this.loading = true;
    this.alarmRuleService.create(this.form.value).subscribe(this.getDefaultEntityServiceHandler());
  }

  onSelectPage(event: any): void {
    this.fetchData(event.pageIndex, event.pageSize);
  }

  openAlarmRuleDialog(alarm: AlarmRule): void {
    this.dialog.open(AlarmRuleDialogComponent, {
      width: '800px',
      data: alarm,
    });
  }

  deactivateRule(alarm: AlarmRule): void {
    this.loading = true;
    this.alarmRuleService.deactivate(alarm.id).subscribe(this.getDefaultEntityServiceHandler());
  }

  getDefaultEntityServiceHandler<TResponse = void>(
    page?: number
  ): Partial<Observer<TResponse>> {
    return {
      next: (_) => {
        this.fetchData(page ?? this.pageNum, this.pageSize);
        this.loading = false;
      },
      error: (err) => {
        this.errorService.handle(err);
        this.waitingResults = false;
        this.loading = false;
      },
    };
  }
}
