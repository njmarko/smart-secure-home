import { TestBed } from '@angular/core/testing';

import { DeviceReportService } from './device-report.service';

describe('DeviceReportService', () => {
  let service: DeviceReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
