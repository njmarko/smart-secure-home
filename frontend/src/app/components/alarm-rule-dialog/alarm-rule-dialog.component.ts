import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AlarmRule } from 'src/app/model/log/AlarmRule';

@Component({
  selector: 'app-alarm-rule-dialog',
  templateUrl: './alarm-rule-dialog.component.html',
  styleUrls: ['./alarm-rule-dialog.component.scss']
})
export class AlarmRuleDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AlarmRuleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public alarmRule: AlarmRule
  ) {
  }

  ngOnInit(): void {
  }

}
