import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventInfo } from 'app/shared/model/event-info.model';
import { EventInfoService } from './event-info.service';

@Component({
  templateUrl: './event-info-delete-dialog.component.html'
})
export class EventInfoDeleteDialogComponent {
  eventInfo?: IEventInfo;

  constructor(protected eventInfoService: EventInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventInfoListModification');
      this.activeModal.close();
    });
  }
}
