import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { AccountInfoTypePersonalAccountingDetailComponent } from 'app/entities/account-info-type-personal-accounting/account-info-type-personal-accounting-detail.component';
import { AccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';

describe('Component Tests', () => {
  describe('AccountInfoTypePersonalAccounting Management Detail Component', () => {
    let comp: AccountInfoTypePersonalAccountingDetailComponent;
    let fixture: ComponentFixture<AccountInfoTypePersonalAccountingDetailComponent>;
    const route = ({ data: of({ accountInfoType: new AccountInfoTypePersonalAccounting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [AccountInfoTypePersonalAccountingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountInfoTypePersonalAccountingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountInfoTypePersonalAccountingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountInfoType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountInfoType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
