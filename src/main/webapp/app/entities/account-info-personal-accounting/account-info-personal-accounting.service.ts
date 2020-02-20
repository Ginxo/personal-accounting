import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

type EntityResponseType = HttpResponse<IAccountInfoPersonalAccounting>;
type EntityArrayResponseType = HttpResponse<IAccountInfoPersonalAccounting[]>;

@Injectable({ providedIn: 'root' })
export class AccountInfoPersonalAccountingService {
  public resourceUrl = SERVER_API_URL + 'api/account-infos';

  constructor(protected http: HttpClient) {}

  create(accountInfo: IAccountInfoPersonalAccounting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountInfo);
    return this.http
      .post<IAccountInfoPersonalAccounting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountInfo: IAccountInfoPersonalAccounting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountInfo);
    return this.http
      .put<IAccountInfoPersonalAccounting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountInfoPersonalAccounting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountInfoPersonalAccounting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountInfo: IAccountInfoPersonalAccounting): IAccountInfoPersonalAccounting {
    const copy: IAccountInfoPersonalAccounting = Object.assign({}, accountInfo, {
      startingDate:
        accountInfo.startingDate && accountInfo.startingDate.isValid() ? accountInfo.startingDate.format(DATE_FORMAT) : undefined,
      endingDate: accountInfo.endingDate && accountInfo.endingDate.isValid() ? accountInfo.endingDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startingDate = res.body.startingDate ? moment(res.body.startingDate) : undefined;
      res.body.endingDate = res.body.endingDate ? moment(res.body.endingDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountInfo: IAccountInfoPersonalAccounting) => {
        accountInfo.startingDate = accountInfo.startingDate ? moment(accountInfo.startingDate) : undefined;
        accountInfo.endingDate = accountInfo.endingDate ? moment(accountInfo.endingDate) : undefined;
      });
    }
    return res;
  }
}
