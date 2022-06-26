import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuchEmptyComponent } from './such-empty.component';

describe('SuchEmptyComponent', () => {
  let component: SuchEmptyComponent;
  let fixture: ComponentFixture<SuchEmptyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuchEmptyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuchEmptyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
