import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonalAccountingTestModule } from '../../../test.module';
import { EventInfoTypeDetailComponent } from 'app/entities/event-info-type/event-info-type-detail.component';
import { EventInfoType } from 'app/shared/model/event-info-type.model';

describe('Component Tests', () => {
  describe('EventInfoType Management Detail Component', () => {
    let comp: EventInfoTypeDetailComponent;
    let fixture: ComponentFixture<EventInfoTypeDetailComponent>;
    const route = ({ data: of({ eventInfoType: new EventInfoType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [EventInfoTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventInfoTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventInfoTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eventInfoType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventInfoType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
