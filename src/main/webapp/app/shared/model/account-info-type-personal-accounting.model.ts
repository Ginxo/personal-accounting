export interface IAccountInfoTypePersonalAccounting {
  id?: number;
  name?: string;
  userId?: number;
  cron?: string;
  isOneTime?: boolean;
}

export class AccountInfoTypePersonalAccounting implements IAccountInfoTypePersonalAccounting {
  constructor(public id?: number, public name?: string, public userId?: number, public cron?: string, public isOneTime?: boolean) {
    this.isOneTime = this.isOneTime || false;
  }
}
