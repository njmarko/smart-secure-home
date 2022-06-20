import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewRealEstatesComponent } from './view-real-estates.component';

describe('ViewRealEstatesComponent', () => {
  let component: ViewRealEstatesComponent;
  let fixture: ComponentFixture<ViewRealEstatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewRealEstatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewRealEstatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
