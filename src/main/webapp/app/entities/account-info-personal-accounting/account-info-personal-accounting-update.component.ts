import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAccountInfoPersonalAccounting, AccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';
import { AccountInfoPersonalAccountingService } from './account-info-personal-accounting.service';
import { IAccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';
import { AccountInfoTypePersonalAccountingService } from 'app/entities/account-info-type-personal-accounting/account-info-type-personal-accounting.service';

@Component({
  selector: 'jhi-account-info-personal-accounting-update',
  templateUrl: './account-info-personal-accounting-update.component.html'
})
export class AccountInfoPersonalAccountingUpdateComponent implements OnInit {
  isSaving = false;
  accountinfotypes: IAccountInfoTypePersonalAccounting[] = [];
  startingDateDp: any;
  endingDateDp: any;

  editForm = this.fb.group({
    id: [],
    concept: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    startingDate: [null, [Validators.required]],
    endingDate: [],
    typeId: [null, Validators.required]
  });

  constructor(
    protected accountInfoService: AccountInfoPersonalAccountingService,
    protected accountInfoTypeService: AccountInfoTypePersonalAccountingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountInfo }) => {
      this.updateForm(accountInfo);

      this.accountInfoTypeService
        .query()
        .subscribe((res: HttpResponse<IAccountInfoTypePersonalAccounting[]>) => (this.accountinfotypes = res.body || []));
    });
  }

  updateForm(accountInfo: IAccountInfoPersonalAccounting): void {
    this.editForm.patchValue({
      id: accountInfo.id,
      concept: accountInfo.concept,
      userId: accountInfo.userId,
      startingDate: accountInfo.startingDate,
      endingDate: accountInfo.endingDate,
      typeId: accountInfo.typeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountInfo = this.createFromForm();
    if (accountInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.accountInfoService.update(accountInfo));
    } else {
      this.subscribeToSaveResponse(this.accountInfoService.create(accountInfo));
    }
  }

  private createFromForm(): IAccountInfoPersonalAccounting {
    return {
      ...new AccountInfoPersonalAccounting(),
      id: this.editForm.get(['id'])!.value,
      concept: this.editForm.get(['concept'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      startingDate: this.editForm.get(['startingDate'])!.value,
      endingDate: this.editForm.get(['endingDate'])!.value,
      typeId: this.editForm.get(['typeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountInfoPersonalAccounting>>): void {
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

  trackById(index: number, item: IAccountInfoTypePersonalAccounting): any {
    return item.id;
  }
}
