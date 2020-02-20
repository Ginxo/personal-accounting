import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountInfoTypePersonalAccounting } from 'app/shared/model/account-info-type-personal-accounting.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountInfoTypePersonalAccountingService } from './account-info-type-personal-accounting.service';
import { AccountInfoTypePersonalAccountingDeleteDialogComponent } from './account-info-type-personal-accounting-delete-dialog.component';

@Component({
  selector: 'jhi-account-info-type-personal-accounting',
  templateUrl: './account-info-type-personal-accounting.component.html'
})
export class AccountInfoTypePersonalAccountingComponent implements OnInit, OnDestroy {
  accountInfoTypes: IAccountInfoTypePersonalAccounting[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected accountInfoTypeService: AccountInfoTypePersonalAccountingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.accountInfoTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.accountInfoTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAccountInfoTypePersonalAccounting[]>) => this.paginateAccountInfoTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.accountInfoTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountInfoTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountInfoTypePersonalAccounting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountInfoTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountInfoTypeListModification', () => this.reset());
  }

  delete(accountInfoType: IAccountInfoTypePersonalAccounting): void {
    const modalRef = this.modalService.open(AccountInfoTypePersonalAccountingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountInfoType = accountInfoType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAccountInfoTypes(data: IAccountInfoTypePersonalAccounting[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.accountInfoTypes.push(data[i]);
      }
    }
  }
}
