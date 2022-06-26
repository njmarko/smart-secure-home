import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmRuleDialogComponent } from './alarm-rule-dialog.component';

describe('AlarmRuleDialogComponent', () => {
  let component: AlarmRuleDialogComponent;
  let fixture: ComponentFixture<AlarmRuleDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmRuleDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmRuleDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
