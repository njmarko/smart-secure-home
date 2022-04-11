import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificatesViewComponent } from './certificates-view.component';

describe('CertificatesViewComponent', () => {
  let component: CertificatesViewComponent;
  let fixture: ComponentFixture<CertificatesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CertificatesViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificatesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
