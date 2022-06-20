import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigureDevicesComponent } from './configure-devices.component';

describe('ConfigureDevicesComponent', () => {
  let component: ConfigureDevicesComponent;
  let fixture: ComponentFixture<ConfigureDevicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigureDevicesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigureDevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
