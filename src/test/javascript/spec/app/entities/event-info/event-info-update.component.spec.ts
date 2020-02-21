import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { EventInfoUpdateComponent } from 'app/entities/event-info/event-info-update.component';
import { EventInfoService } from 'app/entities/event-info/event-info.service';
import { EventInfo } from 'app/shared/model/event-info.model';

describe('Component Tests', () => {
  describe('EventInfo Management Update Component', () => {
    let comp: EventInfoUpdateComponent;
    let fixture: ComponentFixture<EventInfoUpdateComponent>;
    let service: EventInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [EventInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventInfo(123);
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
        const entity = new EventInfo();
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
