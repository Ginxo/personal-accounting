import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventInfoType } from 'app/shared/model/event-info-type.model';

type EntityResponseType = HttpResponse<IEventInfoType>;
type EntityArrayResponseType = HttpResponse<IEventInfoType[]>;

@Injectable({ providedIn: 'root' })
export class EventInfoTypeService {
  public resourceUrl = SERVER_API_URL + 'api/event-info-types';

  constructor(protected http: HttpClient) {}

  create(eventInfoType: IEventInfoType): Observable<EntityResponseType> {
    return this.http.post<IEventInfoType>(this.resourceUrl, eventInfoType, { observe: 'response' });
  }

  update(eventInfoType: IEventInfoType): Observable<EntityResponseType> {
    return this.http.put<IEventInfoType>(this.resourceUrl, eventInfoType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventInfoType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventInfoType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
