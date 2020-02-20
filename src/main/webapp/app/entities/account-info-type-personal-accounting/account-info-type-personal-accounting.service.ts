import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';

type EntityResponseType = HttpResponse<IAccountInfoTypePersonalAccounting>;
type EntityArrayResponseType = HttpResponse<IAccountInfoTypePersonalAccounting[]>;

@Injectable({ providedIn: 'root' })
export class AccountInfoTypePersonalAccountingService {
  public resourceUrl = SERVER_API_URL + 'api/account-info-types';

  constructor(protected http: HttpClient) {}

  create(accountInfoType: IAccountInfoTypePersonalAccounting): Observable<EntityResponseType> {
    return this.http.post<IAccountInfoTypePersonalAccounting>(this.resourceUrl, accountInfoType, { observe: 'response' });
  }

  update(accountInfoType: IAccountInfoTypePersonalAccounting): Observable<EntityResponseType> {
    return this.http.put<IAccountInfoTypePersonalAccounting>(this.resourceUrl, accountInfoType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountInfoTypePersonalAccounting>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountInfoTypePersonalAccounting[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
