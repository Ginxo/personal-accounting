import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PersonalAccountingSharedModule } from 'app/shared/shared.module';
import { AccountInfoPersonalAccountingComponent } from './account-info-personal-accounting.component';
import { AccountInfoPersonalAccountingDetailComponent } from './account-info-personal-accounting-detail.component';
import { AccountInfoPersonalAccountingUpdateComponent } from './account-info-personal-accounting-update.component';
import { AccountInfoPersonalAccountingDeleteDialogComponent } from './account-info-personal-accounting-delete-dialog.component';
import { accountInfoRoute } from './account-info-personal-accounting.route';

@NgModule({
  imports: [PersonalAccountingSharedModule, RouterModule.forChild(accountInfoRoute)],
  declarations: [
    AccountInfoPersonalAccountingComponent,
    AccountInfoPersonalAccountingDetailComponent,
    AccountInfoPersonalAccountingUpdateComponent,
    AccountInfoPersonalAccountingDeleteDialogComponent
  ],
  entryComponents: [AccountInfoPersonalAccountingDeleteDialogComponent]
})
export class PersonalAccountingAccountInfoPersonalAccountingModule {}
