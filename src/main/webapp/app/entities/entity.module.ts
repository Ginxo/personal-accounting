import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'account-info-personal-accounting',
        loadChildren: () =>
          import('./account-info-personal-accounting/account-info-personal-accounting.module').then(
            m => m.PersonalAccountingAccountInfoPersonalAccountingModule
          )
      },
      {
        path: 'account-info-type-personal-accounting',
        loadChildren: () =>
          import('./account-info-type-personal-accounting/account-info-type-personal-accounting.module').then(
            m => m.PersonalAccountingAccountInfoTypePersonalAccountingModule
          )
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PersonalAccountingEntityModule {}
