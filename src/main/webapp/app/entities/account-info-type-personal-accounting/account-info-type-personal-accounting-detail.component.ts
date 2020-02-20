import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';

@Component({
  selector: 'jhi-account-info-type-personal-accounting-detail',
  templateUrl: './account-info-type-personal-accounting-detail.component.html'
})
export class AccountInfoTypePersonalAccountingDetailComponent implements OnInit {
  accountInfoType: IAccountInfoTypePersonalAccounting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountInfoType }) => (this.accountInfoType = accountInfoType));
  }

  previousState(): void {
    window.history.back();
  }
}
