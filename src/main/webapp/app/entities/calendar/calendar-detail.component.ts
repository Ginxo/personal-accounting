import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalendar } from 'app/shared/model/calendar.model';

@Component({
  selector: 'jhi-calendar-detail',
  templateUrl: './calendar-detail.component.html'
})
export class CalendarDetailComponent implements OnInit {
  calendar: ICalendar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => (this.calendar = calendar));
  }

  previousState(): void {
    window.history.back();
  }
}
