import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventInfo } from 'app/shared/model/event-info.model';

type EntityResponseType = HttpResponse<IEventInfo>;
type EntityArrayResponseType = HttpResponse<IEventInfo[]>;

@Injectable({ providedIn: 'root' })
export class EventInfoService {
  public resourceUrl = SERVER_API_URL + 'api/event-infos';

  constructor(protected http: HttpClient) {}

  create(eventInfo: IEventInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventInfo);
    return this.http
      .post<IEventInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eventInfo: IEventInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventInfo);
    return this.http
      .put<IEventInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEventInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEventInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(eventInfo: IEventInfo): IEventInfo {
    const copy: IEventInfo = Object.assign({}, eventInfo, {
      date: eventInfo.date && eventInfo.date.isValid() ? eventInfo.date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((eventInfo: IEventInfo) => {
        eventInfo.date = eventInfo.date ? moment(eventInfo.date) : undefined;
      });
    }
    return res;
  }
}
