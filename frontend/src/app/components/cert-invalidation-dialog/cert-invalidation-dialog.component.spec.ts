import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertInvalidationDialogComponent } from './cert-invalidation-dialog.component';

describe('CertInvalidationDialogComponent', () => {
  let component: CertInvalidationDialogComponent;
  let fixture: ComponentFixture<CertInvalidationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertInvalidationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertInvalidationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
