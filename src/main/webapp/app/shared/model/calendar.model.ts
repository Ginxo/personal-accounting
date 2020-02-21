export interface ICalendar {
  id?: number;
  name?: string;
  colour?: string;
  description?: string;
  timeZone?: string;
  enabled?: boolean;
  userId?: number;
}

export class Calendar implements ICalendar {
  constructor(
    public id?: number,
    public name?: string,
    public colour?: string,
    public description?: string,
    public timeZone?: string,
    public enabled?: boolean,
    public userId?: number
  ) {
    this.enabled = this.enabled || false;
  }
}
