import { Moment } from 'moment';
import { AmountType } from 'app/shared/model/enumerations/amount-type.model';

export interface IEventInfo {
  id?: number;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  amount?: number;
  amountType?: AmountType;
  iterateInformationContentType?: string;
  iterateInformation?: any;
  colour?: string;
  calendarId?: number;
  typeId?: number;
}

export class EventInfo implements IEventInfo {
  constructor(
    public id?: number,
    public name?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public amount?: number,
    public amountType?: AmountType,
    public iterateInformationContentType?: string,
    public iterateInformation?: any,
    public colour?: string,
    public calendarId?: number,
    public typeId?: number
  ) {}
}
