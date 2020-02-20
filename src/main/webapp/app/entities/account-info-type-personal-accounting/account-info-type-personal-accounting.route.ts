import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  IAccountInfoTypePersonalAccounting,
  AccountInfoTypePersonalAccounting
} from 'app/shared/model/account-info-type-personal-accounting.model';
import { AccountInfoTypePersonalAccountingService } from './account-info-type-personal-accounting.service';
import { AccountInfoTypePersonalAccountingComponent } from './account-info-type-personal-accounting.component';
import { AccountInfoTypePersonalAccountingDetailComponent } from './account-info-type-personal-accounting-detail.component';
import { AccountInfoTypePersonalAccountingUpdateComponent } from './account-info-type-personal-accounting-update.component';

@Injectable({ providedIn: 'root' })
export class AccountInfoTypePersonalAccountingResolve implements Resolve<IAccountInfoTypePersonalAccounting> {
  constructor(private service: AccountInfoTypePersonalAccountingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountInfoTypePersonalAccounting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountInfoType: HttpResponse<AccountInfoTypePersonalAccounting>) => {
          if (accountInfoType.body) {
            return of(accountInfoType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountInfoTypePersonalAccounting());
  }
}

export const accountInfoTypeRoute: Routes = [
  {
    path: '',
    component: AccountInfoTypePersonalAccountingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountInfoTypePersonalAccountingDetailComponent,
    resolve: {
      accountInfoType: AccountInfoTypePersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountInfoTypePersonalAccountingUpdateComponent,
    resolve: {
      accountInfoType: AccountInfoTypePersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountInfoTypePersonalAccountingUpdateComponent,
    resolve: {
      accountInfoType: AccountInfoTypePersonalAccountingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.accountInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
