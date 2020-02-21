import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventInfoType, EventInfoType } from 'app/shared/model/event-info-type.model';
import { EventInfoTypeService } from './event-info-type.service';
import { EventInfoTypeComponent } from './event-info-type.component';
import { EventInfoTypeDetailComponent } from './event-info-type-detail.component';
import { EventInfoTypeUpdateComponent } from './event-info-type-update.component';

@Injectable({ providedIn: 'root' })
export class EventInfoTypeResolve implements Resolve<IEventInfoType> {
  constructor(private service: EventInfoTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventInfoType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventInfoType: HttpResponse<EventInfoType>) => {
          if (eventInfoType.body) {
            return of(eventInfoType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventInfoType());
  }
}

export const eventInfoTypeRoute: Routes = [
  {
    path: '',
    component: EventInfoTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventInfoTypeDetailComponent,
    resolve: {
      eventInfoType: EventInfoTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventInfoTypeUpdateComponent,
    resolve: {
      eventInfoType: EventInfoTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventInfoTypeUpdateComponent,
    resolve: {
      eventInfoType: EventInfoTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfoType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
