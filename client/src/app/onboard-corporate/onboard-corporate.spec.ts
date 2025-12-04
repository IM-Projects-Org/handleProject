import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnboardCorporate } from './onboard-corporate';

describe('OnboardCorporate', () => {
  let component: OnboardCorporate;
  let fixture: ComponentFixture<OnboardCorporate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OnboardCorporate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OnboardCorporate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
