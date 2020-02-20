import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

@Component({
  selector: 'jhi-account-info-personal-accounting-detail',
  templateUrl: './account-info-personal-accounting-detail.component.html'
})
export class AccountInfoPersonalAccountingDetailComponent implements OnInit {
  accountInfo: IAccountInfoPersonalAccounting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountInfo }) => (this.accountInfo = accountInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
