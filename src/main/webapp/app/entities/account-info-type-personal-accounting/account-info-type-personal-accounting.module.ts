import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PersonalAccountingSharedModule } from 'app/shared/shared.module';
import { AccountInfoTypePersonalAccountingComponent } from './account-info-type-personal-accounting.component';
import { AccountInfoTypePersonalAccountingDetailComponent } from './account-info-type-personal-accounting-detail.component';
import { AccountInfoTypePersonalAccountingUpdateComponent } from './account-info-type-personal-accounting-update.component';
import { AccountInfoTypePersonalAccountingDeleteDialogComponent } from './account-info-type-personal-accounting-delete-dialog.component';
import { accountInfoTypeRoute } from './account-info-type-personal-accounting.route';

@NgModule({
  imports: [PersonalAccountingSharedModule, RouterModule.forChild(accountInfoTypeRoute)],
  declarations: [
    AccountInfoTypePersonalAccountingComponent,
    AccountInfoTypePersonalAccountingDetailComponent,
    AccountInfoTypePersonalAccountingUpdateComponent,
    AccountInfoTypePersonalAccountingDeleteDialogComponent
  ],
  entryComponents: [AccountInfoTypePersonalAccountingDeleteDialogComponent]
})
export class PersonalAccountingAccountInfoTypePersonalAccountingModule {}
