import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullScreenModalComponent } from './full-screen-modal.component';

describe('FullScreenModalComponent', () => {
  let component: FullScreenModalComponent;
  let fixture: ComponentFixture<FullScreenModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullScreenModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FullScreenModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
