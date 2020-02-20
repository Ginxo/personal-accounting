import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountInfoPersonalAccounting } from 'app/shared/model/account-info-personal-accounting.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountInfoPersonalAccountingService } from './account-info-personal-accounting.service';
import { AccountInfoPersonalAccountingDeleteDialogComponent } from './account-info-personal-accounting-delete-dialog.component';

@Component({
  selector: 'jhi-account-info-personal-accounting',
  templateUrl: './account-info-personal-accounting.component.html'
})
export class AccountInfoPersonalAccountingComponent implements OnInit, OnDestroy {
  accountInfos: IAccountInfoPersonalAccounting[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected accountInfoService: AccountInfoPersonalAccountingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.accountInfos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.accountInfoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAccountInfoPersonalAccounting[]>) => this.paginateAccountInfos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.accountInfos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccountInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccountInfoPersonalAccounting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccountInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('accountInfoListModification', () => this.reset());
  }

  delete(accountInfo: IAccountInfoPersonalAccounting): void {
    const modalRef = this.modalService.open(AccountInfoPersonalAccountingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountInfo = accountInfo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAccountInfos(data: IAccountInfoPersonalAccounting[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.accountInfos.push(data[i]);
      }
    }
  }
}
