import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEventInfoType, EventInfoType } from 'app/shared/model/event-info-type.model';
import { EventInfoTypeService } from './event-info-type.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-event-info-type-update',
  templateUrl: './event-info-type-update.component.html'
})
export class EventInfoTypeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    icon: [null, [Validators.required]],
    userId: [null, Validators.required]
  });

  constructor(
    protected eventInfoTypeService: EventInfoTypeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventInfoType }) => {
      this.updateForm(eventInfoType);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(eventInfoType: IEventInfoType): void {
    this.editForm.patchValue({
      id: eventInfoType.id,
      name: eventInfoType.name,
      icon: eventInfoType.icon,
      userId: eventInfoType.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventInfoType = this.createFromForm();
    if (eventInfoType.id !== undefined) {
      this.subscribeToSaveResponse(this.eventInfoTypeService.update(eventInfoType));
    } else {
      this.subscribeToSaveResponse(this.eventInfoTypeService.create(eventInfoType));
    }
  }

  private createFromForm(): IEventInfoType {
    return {
      ...new EventInfoType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      icon: this.editForm.get(['icon'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventInfoType>>): void {
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
