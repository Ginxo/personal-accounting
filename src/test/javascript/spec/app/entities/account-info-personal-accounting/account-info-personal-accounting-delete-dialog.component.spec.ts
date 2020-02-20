import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PersonalAccountingTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AccountInfoPersonalAccountingDeleteDialogComponent } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting-delete-dialog.component';
import { AccountInfoPersonalAccountingService } from 'app/entities/account-info-personal-accounting/account-info-personal-accounting.service';

describe('Component Tests', () => {
  describe('AccountInfoPersonalAccounting Management Delete Component', () => {
    let comp: AccountInfoPersonalAccountingDeleteDialogComponent;
    let fixture: ComponentFixture<AccountInfoPersonalAccountingDeleteDialogComponent>;
    let service: AccountInfoPersonalAccountingService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [AccountInfoPersonalAccountingDeleteDialogComponent]
      })
        .overrideTemplate(AccountInfoPersonalAccountingDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountInfoPersonalAccountingDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountInfoPersonalAccountingService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
