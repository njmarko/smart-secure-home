import { TestBed } from '@angular/core/testing';

import { DeviceMessageService } from './device-message.service';

describe('DeviceMessageService', () => {
  let service: DeviceMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
