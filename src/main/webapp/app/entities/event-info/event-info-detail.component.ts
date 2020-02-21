import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEventInfo } from 'app/shared/model/event-info.model';

@Component({
  selector: 'jhi-event-info-detail',
  templateUrl: './event-info-detail.component.html'
})
export class EventInfoDetailComponent implements OnInit {
  eventInfo: IEventInfo | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInfo }) => (this.eventInfo = eventInfo));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
