import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { User } from 'app/core/user/user.model';
import { EventInfoTypeService } from 'app/entities/event-info-type/event-info-type.service';
import { EventInfoService } from 'app/entities/event-info/event-info.service';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DATE_FORMAT } from '../constants/input.constants';
import { IEventInfo } from '../model/event-info.model';
import { IForecastInfo } from '../model/forecast-info.model';
import { treatEventRecurrency } from '../rschedule/treatEventRecurrency';
import { mapEventToForecast } from './forecast.mapper';

@Component({
  selector: 'jhi-forecast-view',
  templateUrl: './forecast.view.component.html',
  styleUrls: ['./forecast.view.component.scss']
})
export class ForecastViewComponent implements OnInit {
  @Input()
  user?: User;
  forecasts$?: Observable<IForecastInfo[]>;

  filterForm = this.fb.group({
    fromDate: [moment().toDate()],
    toDate: [
      moment()
        .add(1, 'year')
        .toDate()
    ]
  });

  constructor(private eventInfoService: EventInfoService, private eventInfoTypeService: EventInfoTypeService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.executeForecast();
  }

  executeForecast(): void {
    this.forecasts$ = this.eventInfoService
      .query({
        'startDate.greaterThanOrEqual': moment(this.filterForm.get('fromDate')!.value).format(DATE_FORMAT),
        'startDate.lessThanOrEqual': moment(this.filterForm.get('toDate')!.value).format(DATE_FORMAT)
      })
      .pipe(
        filter((response: HttpResponse<IEventInfo[]>) => response.ok),
        map((element: HttpResponse<IEventInfo[]>) => element.body),
        map((elements: IEventInfo[] | null) => this.generateForecast(elements !== null ? elements : []))
      );
  }

  private generateForecast(events: IEventInfo[]): IForecastInfo[] {
    const sortedEvents = events
      .flatMap(e => treatEventRecurrency(e))
      .sort((a, b) => {
        return a.date !== undefined && b.date !== undefined ? Number(a.date.toDate()) - Number(b.date.toDate()) : 0;
      });

    let totalAmount = 0;
    return sortedEvents.map(event => {
      const foreCastInfo = mapEventToForecast(event, totalAmount);
      totalAmount = foreCastInfo.totalAmount !== undefined ? foreCastInfo.totalAmount : 0;
      return foreCastInfo;
    });
  }
}
