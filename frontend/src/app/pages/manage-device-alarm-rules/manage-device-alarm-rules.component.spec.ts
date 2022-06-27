import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageDeviceAlarmRulesComponent } from './manage-device-alarm-rules.component';

describe('ManageDeviceAlarmRulesComponent', () => {
  let component: ManageDeviceAlarmRulesComponent;
  let fixture: ComponentFixture<ManageDeviceAlarmRulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageDeviceAlarmRulesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageDeviceAlarmRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
