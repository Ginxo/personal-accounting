import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventInfo, EventInfo } from 'app/shared/model/event-info.model';
import { EventInfoService } from './event-info.service';
import { EventInfoComponent } from './event-info.component';
import { EventInfoDetailComponent } from './event-info-detail.component';
import { EventInfoUpdateComponent } from './event-info-update.component';

@Injectable({ providedIn: 'root' })
export class EventInfoResolve implements Resolve<IEventInfo> {
  constructor(private service: EventInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventInfo: HttpResponse<EventInfo>) => {
          if (eventInfo.body) {
            return of(eventInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventInfo());
  }
}

export const eventInfoRoute: Routes = [
  {
    path: '',
    component: EventInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventInfoDetailComponent,
    resolve: {
      eventInfo: EventInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventInfoUpdateComponent,
    resolve: {
      eventInfo: EventInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventInfoUpdateComponent,
    resolve: {
      eventInfo: EventInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'personalAccountingApp.eventInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
