import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PersonalAccountingSharedModule } from 'app/shared/shared.module';
import { EventInfoComponent } from './event-info.component';
import { EventInfoDetailComponent } from './event-info-detail.component';
import { EventInfoUpdateComponent } from './event-info-update.component';
import { EventInfoDeleteDialogComponent } from './event-info-delete-dialog.component';
import { eventInfoRoute } from './event-info.route';
import { ColorPickerModule } from 'ngx-color-picker';

@NgModule({
  imports: [PersonalAccountingSharedModule, RouterModule.forChild(eventInfoRoute), ColorPickerModule],
  declarations: [EventInfoComponent, EventInfoDetailComponent, EventInfoUpdateComponent, EventInfoDeleteDialogComponent],
  entryComponents: [EventInfoDeleteDialogComponent]
})
export class PersonalAccountingEventInfoModule {}
