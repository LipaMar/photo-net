import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChipsSelectComponent } from './chips-select.component';

describe('ChipsSelectComponent', () => {
  let component: ChipsSelectComponent;
  let fixture: ComponentFixture<ChipsSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChipsSelectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChipsSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
