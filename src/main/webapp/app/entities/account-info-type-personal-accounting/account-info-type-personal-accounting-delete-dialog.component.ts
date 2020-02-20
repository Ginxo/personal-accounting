import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';
import { AccountInfoTypePersonalAccountingService } from './account-info-type-personal-accounting.service';

@Component({
  templateUrl: './account-info-type-personal-accounting-delete-dialog.component.html'
})
export class AccountInfoTypePersonalAccountingDeleteDialogComponent {
  accountInfoType?: IAccountInfoTypePersonalAccounting;

  constructor(
    protected accountInfoTypeService: AccountInfoTypePersonalAccountingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountInfoTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountInfoTypeListModification');
      this.activeModal.close();
    });
  }
}
