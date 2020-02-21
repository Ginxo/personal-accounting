import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PersonalAccountingTestModule } from '../../../test.module';
import { EventInfoDetailComponent } from 'app/entities/event-info/event-info-detail.component';
import { EventInfo } from 'app/shared/model/event-info.model';

describe('Component Tests', () => {
  describe('EventInfo Management Detail Component', () => {
    let comp: EventInfoDetailComponent;
    let fixture: ComponentFixture<EventInfoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ eventInfo: new EventInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PersonalAccountingTestModule],
        declarations: [EventInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventInfoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load eventInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
