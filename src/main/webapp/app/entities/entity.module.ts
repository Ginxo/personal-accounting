import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'calendar',
        loadChildren: () => import('./calendar/calendar.module').then(m => m.PersonalAccountingCalendarModule)
      },
      {
        path: 'event-info',
        loadChildren: () => import('./event-info/event-info.module').then(m => m.PersonalAccountingEventInfoModule)
      },
      {
        path: 'event-info-type',
        loadChildren: () => import('./event-info-type/event-info-type.module').then(m => m.PersonalAccountingEventInfoTypeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PersonalAccountingEntityModule {}
