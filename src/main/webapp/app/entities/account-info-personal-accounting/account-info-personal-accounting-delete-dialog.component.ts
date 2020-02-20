import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';
import { AccountInfoPersonalAccountingService } from './account-info-personal-accounting.service';

@Component({
  templateUrl: './account-info-personal-accounting-delete-dialog.component.html'
})
export class AccountInfoPersonalAccountingDeleteDialogComponent {
  accountInfo?: IAccountInfoPersonalAccounting;

  constructor(
    protected accountInfoService: AccountInfoPersonalAccountingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accountInfoListModification');
      this.activeModal.close();
    });
  }
}
