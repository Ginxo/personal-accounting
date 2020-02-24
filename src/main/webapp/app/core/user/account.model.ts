import { ICalendar } from 'app/shared/model/calendar.model';

export class Account {
  constructor(
    public id: any,
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string,
    public selectedCalendar: ICalendar | null
  ) {}
}
