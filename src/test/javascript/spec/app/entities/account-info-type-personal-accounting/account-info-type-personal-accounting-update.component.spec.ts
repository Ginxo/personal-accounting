import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { AccountInfoTypePersonalAccountingUpdateComponent } from 'app/entities/account-info-type-personal-accounting/account-info-type-personal-accounting-update.component';
import { AccountInfoTypePersonalAccountingService } from 'app/entities/account-info-type-personal-accounting/account-info-type-personal-accounting.service';
import { AccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';

describe('Component Tests', () => {
  describe('AccountInfoTypePersonalAccounting Management Update Component', () => {
    let comp: AccountInfoTypePersonalAccountingUpdateComponent;
    let fixture: ComponentFixture<AccountInfoTypePersonalAccountingUpdateComponent>;
    let service: AccountInfoTypePersonalAccountingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [AccountInfoTypePersonalAccountingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountInfoTypePersonalAccountingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountInfoTypePersonalAccountingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountInfoTypePersonalAccountingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountInfoTypePersonalAccounting(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountInfoTypePersonalAccounting();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
