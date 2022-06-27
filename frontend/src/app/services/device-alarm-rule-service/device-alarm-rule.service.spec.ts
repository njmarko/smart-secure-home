import { TestBed } from '@angular/core/testing';

import { DeviceAlarmRuleService} from './device-alarm-rule.service';

describe('DeviceAlarmRuleServiceService', () => {
  let service: DeviceAlarmRuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceAlarmRuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
