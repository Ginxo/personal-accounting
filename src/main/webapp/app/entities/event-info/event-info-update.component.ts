import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEventInfo, EventInfo } from 'app/shared/model/event-info.model';
import { EventInfoService } from './event-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICalendar } from 'app/shared/model/calendar.model';
import { CalendarService } from 'app/entities/calendar/calendar.service';
import { IEventInfoType } from 'app/shared/model/event-info-type.model';
import { EventInfoTypeService } from 'app/entities/event-info-type/event-info-type.service';

type SelectableEntity = ICalendar | IEventInfoType;

@Component({
  selector: 'jhi-event-info-update',
  templateUrl: './event-info-update.component.html'
})
export class EventInfoUpdateComponent implements OnInit {
  isSaving = false;
  calendars: ICalendar[] = [];
  eventinfotypes: IEventInfoType[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    amountType: [null, [Validators.required]],
    iterateInformation: [],
    colour: [null, [Validators.required]],
    calendarId: [null, Validators.required],
    typeId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected eventInfoService: EventInfoService,
    protected calendarService: CalendarService,
    protected eventInfoTypeService: EventInfoTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInfo }) => {
      this.updateForm(eventInfo);

      this.calendarService.query().subscribe((res: HttpResponse<ICalendar[]>) => (this.calendars = res.body || []));

      this.eventInfoTypeService.query().subscribe((res: HttpResponse<IEventInfoType[]>) => (this.eventinfotypes = res.body || []));
    });
  }

  updateForm(eventInfo: IEventInfo): void {
    this.editForm.patchValue({
      id: eventInfo.id,
      name: eventInfo.name,
      startDate: eventInfo.startDate,
      endDate: eventInfo.endDate,
      amount: eventInfo.amount,
      amountType: eventInfo.amountType,
      iterateInformation: eventInfo.iterateInformation,
      colour: eventInfo.colour,
      calendarId: eventInfo.calendarId,
      typeId: eventInfo.typeId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('personalAccountingApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventInfo = this.createFromForm();
    if (eventInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.eventInfoService.update(eventInfo));
    } else {
      this.subscribeToSaveResponse(this.eventInfoService.create(eventInfo));
    }
  }

  private createFromForm(): IEventInfo {
    return {
      ...new EventInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      amountType: this.editForm.get(['amountType'])!.value,
      iterateInformation: this.editForm.get(['iterateInformation'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      calendarId: this.editForm.get(['calendarId'])!.value,
      typeId: this.editForm.get(['typeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
