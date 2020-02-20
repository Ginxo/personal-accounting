import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AccountInfoPersonalAccountingService } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting.service';
import { IAccountInfoPersonalAccounting, AccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

describe('Service Tests', () => {
  describe('AccountInfoPersonalAccounting Service', () => {
    let injector: TestBed;
    let service: AccountInfoPersonalAccountingService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccountInfoPersonalAccounting;
    let expectedResult: IAccountInfoPersonalAccounting | IAccountInfoPersonalAccounting[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AccountInfoPersonalAccountingService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AccountInfoPersonalAccounting(0, 'AAAAAAA', 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startingDate: currentDate.format(DATE_FORMAT),
            endingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AccountInfoPersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startingDate: currentDate.format(DATE_FORMAT),
            endingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
            endingDate: currentDate
          },
          returnedFromService
        );

        service.create(new AccountInfoPersonalAccounting()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AccountInfoPersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            concept: 'BBBBBB',
            userId: 1,
            startingDate: currentDate.format(DATE_FORMAT),
            endingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
            endingDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AccountInfoPersonalAccounting', () => {
        const returnedFromService = Object.assign(
          {
            concept: 'BBBBBB',
            userId: 1,
            startingDate: currentDate.format(DATE_FORMAT),
            endingDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startingDate: currentDate,
            endingDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AccountInfoPersonalAccounting', () => {
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
