import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrsViewComponent } from './csrs-view.component';

describe('CsrsViewComponent', () => {
  let component: CsrsViewComponent;
  let fixture: ComponentFixture<CsrsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CsrsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
