import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { EventInfoTypeUpdateComponent } from 'app/entities/event-info-type/event-info-type-update.component';
import { EventInfoTypeService } from 'app/entities/event-info-type/event-info-type.service';
import { EventInfoType } from 'app/shared/model/event-info-type.model';

describe('Component Tests', () => {
  describe('EventInfoType Management Update Component', () => {
    let comp: EventInfoTypeUpdateComponent;
    let fixture: ComponentFixture<EventInfoTypeUpdateComponent>;
    let service: EventInfoTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [EventInfoTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventInfoTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventInfoTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventInfoTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventInfoType(123);
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
        const entity = new EventInfoType();
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
