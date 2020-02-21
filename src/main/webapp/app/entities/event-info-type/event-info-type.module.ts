import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PersonalAccountingSharedModule } from 'app/shared/shared.module';
import { EventInfoTypeComponent } from './event-info-type.component';
import { EventInfoTypeDetailComponent } from './event-info-type-detail.component';
import { EventInfoTypeUpdateComponent } from './event-info-type-update.component';
import { EventInfoTypeDeleteDialogComponent } from './event-info-type-delete-dialog.component';
import { eventInfoTypeRoute } from './event-info-type.route';

@NgModule({
  imports: [PersonalAccountingSharedModule, RouterModule.forChild(eventInfoTypeRoute)],
  declarations: [EventInfoTypeComponent, EventInfoTypeDetailComponent, EventInfoTypeUpdateComponent, EventInfoTypeDeleteDialogComponent],
  entryComponents: [EventInfoTypeDeleteDialogComponent]
})
export class PersonalAccountingEventInfoTypeModule {}
