import { Moment } from 'moment';

export interface IAccountInfoPersonalAccounting {
  id?: number;
  concept?: string;
  userId?: number;
  startingDate?: Moment;
  endingDate?: Moment;
  typeId?: number;
}

export class AccountInfoPersonalAccounting implements IAccountInfoPersonalAccounting {
  constructor(
    public id?: number,
    public concept?: string,
    public userId?: number,
    public startingDate?: Moment,
    public endingDate?: Moment,
    public typeId?: number
  ) {}
}
