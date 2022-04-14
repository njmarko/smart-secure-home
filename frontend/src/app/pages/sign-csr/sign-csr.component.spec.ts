import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignCsrComponent } from './sign-csr.component';

describe('SignCsrComponent', () => {
  let component: SignCsrComponent;
  let fixture: ComponentFixture<SignCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SignCsrComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
