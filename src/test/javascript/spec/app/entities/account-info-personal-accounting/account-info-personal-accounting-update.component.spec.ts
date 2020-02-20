import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { AccountInfoPersonalAccountingUpdateComponent } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting-update.component';
import { AccountInfoPersonalAccountingService } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting.service';
import { AccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

describe('Component Tests', () => {
  describe('AccountInfoPersonalAccounting Management Update Component', () => {
    let comp: AccountInfoPersonalAccountingUpdateComponent;
    let fixture: ComponentFixture<AccountInfoPersonalAccountingUpdateComponent>;
    let service: AccountInfoPersonalAccountingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [AccountInfoPersonalAccountingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountInfoPersonalAccountingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountInfoPersonalAccountingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountInfoPersonalAccountingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountInfoPersonalAccounting(123);
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
        const entity = new AccountInfoPersonalAccounting();
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
