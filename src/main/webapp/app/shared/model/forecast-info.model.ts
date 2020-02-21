import { Moment } from 'moment';
import { IEventInfo } from './event-info.model';

export interface IForecastInfo {
  name?: string;
  date?: Moment;
  amount?: number;
  totalAmount?: number;
  amountDifference?: number;
  colour?: string;
  icon?: string;
  eventInfo?: IEventInfo;
}

export class ForecastInfo implements IForecastInfo {
  constructor(
    public name?: string,
    public date?: Moment,
    public amount?: number,
    public totalAmount?: number,
    public amountDifference?: number,
    public colour?: string,
    public icon?: string,
    public eventInfo?: IEventInfo
  ) {}
}
