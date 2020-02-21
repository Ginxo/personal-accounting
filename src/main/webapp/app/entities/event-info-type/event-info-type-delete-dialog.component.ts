import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventInfoType } from 'app/shared/model/event-info-type.model';
import { EventInfoTypeService } from './event-info-type.service';

@Component({
  templateUrl: './event-info-type-delete-dialog.component.html'
})
export class EventInfoTypeDeleteDialogComponent {
  eventInfoType?: IEventInfoType;

  constructor(
    protected eventInfoTypeService: EventInfoTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventInfoTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventInfoTypeListModification');
      this.activeModal.close();
    });
  }
}
