import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventInfoType } from 'app/shared/model/event-info-type.model';

@Component({
  selector: 'jhi-event-info-type-detail',
  templateUrl: './event-info-type-detail.component.html'
})
export class EventInfoTypeDetailComponent implements OnInit {
  eventInfoType: IEventInfoType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInfoType }) => (this.eventInfoType = eventInfoType));
  }

  previousState(): void {
    window.history.back();
  }
}
