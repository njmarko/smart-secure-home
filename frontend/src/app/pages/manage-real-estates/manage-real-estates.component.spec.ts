import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRealEstatesComponent } from './manage-real-estates.component';

describe('ManageRealEstatesComponent', () => {
  let component: ManageRealEstatesComponent;
  let fixture: ComponentFixture<ManageRealEstatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageRealEstatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageRealEstatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
