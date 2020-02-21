import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalendar, Calendar } from 'app/shared/model/calendar.model';
import { CalendarService } from './calendar.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-calendar-update',
  templateUrl: './calendar-update.component.html'
})
export class CalendarUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    colour: [null, [Validators.required]],
    description: [],
    timeZone: [null, [Validators.required]],
    enabled: [null, [Validators.required]],
    userId: [null, Validators.required]
  });

  constructor(
    protected calendarService: CalendarService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendar }) => {
      this.updateForm(calendar);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(calendar: ICalendar): void {
    this.editForm.patchValue({
      id: calendar.id,
      name: calendar.name,
      colour: calendar.colour,
      description: calendar.description,
      timeZone: calendar.timeZone,
      enabled: calendar.enabled,
      userId: calendar.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendar = this.createFromForm();
    if (calendar.id !== undefined) {
      this.subscribeToSaveResponse(this.calendarService.update(calendar));
    } else {
      this.subscribeToSaveResponse(this.calendarService.create(calendar));
    }
  }

  private createFromForm(): ICalendar {
    return {
      ...new Calendar(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      colour: this.editForm.get(['colour'])!.value,
      description: this.editForm.get(['description'])!.value,
      timeZone: this.editForm.get(['timeZone'])!.value,
      enabled: this.editForm.get(['enabled'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendar>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
