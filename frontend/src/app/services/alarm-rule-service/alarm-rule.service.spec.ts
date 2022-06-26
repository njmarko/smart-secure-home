import { TestBed } from '@angular/core/testing';

import { AlarmRuleService } from './alarm-rule.service';

describe('AlarmRuleService', () => {
  let service: AlarmRuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlarmRuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
