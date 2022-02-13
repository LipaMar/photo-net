import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingListFilterComponent } from './meeting-list-filter.component';

describe('ListFilterComponent', () => {
  let component: MeetingListFilterComponent;
  let fixture: ComponentFixture<MeetingListFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeetingListFilterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingListFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
