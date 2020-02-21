import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PersonalAccountingSharedModule } from 'app/shared/shared.module';
import { CalendarComponent } from './calendar.component';
import { CalendarDetailComponent } from './calendar-detail.component';
import { CalendarUpdateComponent } from './calendar-update.component';
import { CalendarDeleteDialogComponent } from './calendar-delete-dialog.component';
import { calendarRoute } from './calendar.route';

@NgModule({
  imports: [PersonalAccountingSharedModule, RouterModule.forChild(calendarRoute)],
  declarations: [CalendarComponent, CalendarDetailComponent, CalendarUpdateComponent, CalendarDeleteDialogComponent],
  entryComponents: [CalendarDeleteDialogComponent]
})
export class PersonalAccountingCalendarModule {}
