export interface IEventInfoType {
  id?: number;
  name?: string;
  icon?: string;
  userId?: number;
}

export class EventInfoType implements IEventInfoType {
  constructor(public id?: number, public name?: string, public icon?: string, public userId?: number) {}
}
