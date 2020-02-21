import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventInfoType } from 'app/shared/model/event-info-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EventInfoTypeService } from './event-info-type.service';
import { EventInfoTypeDeleteDialogComponent } from './event-info-type-delete-dialog.component';

@Component({
  selector: 'jhi-event-info-type',
  templateUrl: './event-info-type.component.html'
})
export class EventInfoTypeComponent implements OnInit, OnDestroy {
  eventInfoTypes: IEventInfoType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected eventInfoTypeService: EventInfoTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.eventInfoTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.eventInfoTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEventInfoType[]>) => this.paginateEventInfoTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.eventInfoTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEventInfoTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEventInfoType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEventInfoTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('eventInfoTypeListModification', () => this.reset());
  }

  delete(eventInfoType: IEventInfoType): void {
    const modalRef = this.modalService.open(EventInfoTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eventInfoType = eventInfoType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEventInfoTypes(data: IEventInfoType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.eventInfoTypes.push(data[i]);
      }
    }
  }
}
