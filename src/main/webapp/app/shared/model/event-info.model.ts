import { Moment } from 'moment';
import { AmountType } from 'app/shared/model/enumerations/amount-type.model';
import { IEventInfoType } from './event-info-type.model';

export interface IEventInfo {
  id?: number;
  name?: string;
  date?: Moment;
  amount?: number;
  amountType?: AmountType;
  iterateInformation?: any;
  colour?: string;
  calendarId?: number;
  typeId?: number;
  type?: IEventInfoType;
}

export class EventInfo implements IEventInfo {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public amount?: number,
    public amountType?: AmountType,
    public iterateInformation?: any,
    public colour?: string,
    public calendarId?: number,
    public typeId?: number,
    public type?: IEventInfoType
  ) {}
}
