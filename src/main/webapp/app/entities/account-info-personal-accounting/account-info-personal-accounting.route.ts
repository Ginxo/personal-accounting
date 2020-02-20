import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccountInfoPersonalAccounting, AccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';
import { AccountInfoPersonalAccountingService } from './account-info-personal-accounting.service';
import { AccountInfoPersonalAccountingComponent } from './account-info-personal-accounting.component';
import { AccountInfoPersonalAccountingDetailComponent } from './account-info-personal-accounting-detail.component';
import { AccountInfoPersonalAccountingUpdateComponent } from './account-info-personal-accounting-update.component';

@Injectable({ providedIn: 'root' })
export class AccountInfoPersonalAccountingResolve implements Resolve<IAccountInfoPersonalAccounting> {
  constructor(private service: AccountInfoPersonalAccountingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountInfoPersonalAccounting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountInfo: HttpResponse<AccountInfoPersonalAccounting>) => {
          if (accountInfo.body) {
            return of(accountInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountInfoPersonalAccounting());
  }
}

export const accountInfoRoute: Routes = [
  {
    path: '',
    component: AccountInfoPersonalAccountingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountInfoPersonalAccountingDetailComponent,
    resolve: {
      accountInfo: AccountInfoPersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountInfoPersonalAccountingUpdateComponent,
    resolve: {
      accountInfo: AccountInfoPersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountInfoPersonalAccountingUpdateComponent,
    resolve: {
      accountInfo: AccountInfoPersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
