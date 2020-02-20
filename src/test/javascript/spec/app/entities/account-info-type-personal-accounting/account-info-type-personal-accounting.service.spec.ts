import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AccountInfoTypePersonalAccountingService } from 'app/entities/account-info-type-personal-accounting/account-info-type-personal-accounting.service';
import {
  IAccountInfoTypePersonalAccounting,
  AccountInfoTypePersonalAccounting
} from 'app/shared/model/account-info-type-personal-accounting.model';

describe('Service Tests', () => {
  describe('AccountInfoTypePersonalAccounting Service', () => {
    let injector: TestBed;
    let service: AccountInfoTypePersonalAccountingService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccountInfoTypePersonalAccounting;
    let expectedResult: IAccountInfoTypePersonalAccounting | IAccountInfoTypePersonalAccounting[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AccountInfoTypePersonalAccountingService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AccountInfoTypePersonalAccounting(0, 'AAAAAAA', 0, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AccountInfoTypePersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AccountInfoTypePersonalAccounting()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AccountInfoTypePersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            userId: 1,
            cron: 'BBBBBB',
            isOneTime: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AccountInfoTypePersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            userId: 1,
            cron: 'BBBBBB',
            isOneTime: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AccountInfoTypePersonalAccounting', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
