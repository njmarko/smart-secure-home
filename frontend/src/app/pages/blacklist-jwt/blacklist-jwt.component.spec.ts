import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlacklistJwtComponent } from './blacklist-jwt.component';

describe('BlacklistJwtComponent', () => {
  let component: BlacklistJwtComponent;
  let fixture: ComponentFixture<BlacklistJwtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlacklistJwtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlacklistJwtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
