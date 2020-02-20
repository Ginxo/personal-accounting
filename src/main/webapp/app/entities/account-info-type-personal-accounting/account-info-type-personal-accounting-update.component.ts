import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  IAccountInfoTypePersonalAccounting,
  AccountInfoTypePersonalAccounting
} from 'app/shared/model/account-info-type-personal-accounting.model';
import { AccountInfoTypePersonalAccountingService } from './account-info-type-personal-accounting.service';

@Component({
  selector: 'jhi-account-info-type-personal-accounting-update',
  templateUrl: './account-info-type-personal-accounting-update.component.html'
})
export class AccountInfoTypePersonalAccountingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    cron: [],
    isOneTime: []
  });

  constructor(
    protected accountInfoTypeService: AccountInfoTypePersonalAccountingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountInfoType }) => {
      this.updateForm(accountInfoType);
    });
  }

  updateForm(accountInfoType: IAccountInfoTypePersonalAccounting): void {
    this.editForm.patchValue({
      id: accountInfoType.id,
      name: accountInfoType.name,
      userId: accountInfoType.userId,
      cron: accountInfoType.cron,
      isOneTime: accountInfoType.isOneTime
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountInfoType = this.createFromForm();
    if (accountInfoType.id !== undefined) {
      this.subscribeToSaveResponse(this.accountInfoTypeService.update(accountInfoType));
    } else {
      this.subscribeToSaveResponse(this.accountInfoTypeService.create(accountInfoType));
    }
  }

  private createFromForm(): IAccountInfoTypePersonalAccounting {
    return {
      ...new AccountInfoTypePersonalAccounting(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      cron: this.editForm.get(['cron'])!.value,
      isOneTime: this.editForm.get(['isOneTime'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountInfoTypePersonalAccounting>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
