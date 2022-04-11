import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrFormComponent } from './csr-form.component';

describe('CsrFormComponent', () => {
  let component: CsrFormComponent;
  let fixture: ComponentFixture<CsrFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
