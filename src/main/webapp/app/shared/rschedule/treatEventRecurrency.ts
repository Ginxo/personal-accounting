import { Schedule, StandardDateAdapter } from './rschedule';
import * as moment from 'moment';
import { EventInfo, IEventInfo } from '../model/event-info.model';
import { parseIsoDateStrToDate } from './dateParser';

const occurrenceToEvent = (dateAdapter: StandardDateAdapter, event: IEventInfo) => {
  return new EventInfo(
    event.id,
    event.name,
    moment(dateAdapter.date),
    event.amount,
    event.amountType,
    event.iterateInformation,
    event.colour,
    event.calendarId,
    event.typeId,
    event.type
  );
};

export const treatEventRecurrency = (event: IEventInfo): IEventInfo[] => {
  return ![null, undefined, ''].includes(event.iterateInformation)
    ? new Schedule({
        rrules: [JSON.parse(event.iterateInformation, parseIsoDateStrToDate)]
      })
        .occurrences()
        .toArray()
        .map(o => occurrenceToEvent(o, event))
    : [event];
};
