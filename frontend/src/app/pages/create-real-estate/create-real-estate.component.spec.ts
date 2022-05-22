import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRealEstateComponent } from './create-real-estate.component';

describe('CreateRealEstateComponent', () => {
  let component: CreateRealEstateComponent;
  let fixture: ComponentFixture<CreateRealEstateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateRealEstateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRealEstateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
