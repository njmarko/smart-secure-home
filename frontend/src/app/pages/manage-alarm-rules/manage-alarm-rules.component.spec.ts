import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageAlarmRulesComponent } from './manage-alarm-rules.component';

describe('ManageAlarmRulesComponent', () => {
  let component: ManageAlarmRulesComponent;
  let fixture: ComponentFixture<ManageAlarmRulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageAlarmRulesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageAlarmRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
