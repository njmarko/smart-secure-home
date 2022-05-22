import { TestBed } from '@angular/core/testing';

import { BlacklistedJwtService } from './blacklisted-jwt.service';

describe('BlacklistedJwtService', () => {
  let service: BlacklistedJwtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BlacklistedJwtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
