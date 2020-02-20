import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { AccountInfoPersonalAccountingDetailComponent } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting-detail.component';
import { AccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

describe('Component Tests', () => {
  describe('AccountInfoPersonalAccounting Management Detail Component', () => {
    let comp: AccountInfoPersonalAccountingDetailComponent;
    let fixture: ComponentFixture<AccountInfoPersonalAccountingDetailComponent>;
    const route = ({ data: of({ accountInfo: new AccountInfoPersonalAccounting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [AccountInfoPersonalAccountingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountInfoPersonalAccountingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountInfoPersonalAccountingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accountInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
