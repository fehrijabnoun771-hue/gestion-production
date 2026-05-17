import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Techniciens } from './techniciens';

describe('Techniciens', () => {
  let component: Techniciens;
  let fixture: ComponentFixture<Techniciens>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Techniciens],
    }).compileComponents();

    fixture = TestBed.createComponent(Techniciens);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
