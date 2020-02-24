import { IEventInfo } from '../model/event-info.model';
import { IForecastInfo, ForecastInfo } from '../model/forecast-info.model';
import { AmountType } from '../model/enumerations/amount-type.model';

export const mapEventToForecast = (event: IEventInfo, totalAmount: number): IForecastInfo => {
  const foreCastInfo = new ForecastInfo();
  const currentAmountType = event.amountType !== undefined ? event.amountType : AmountType.SUM;
  const currentAmount = event.amount !== undefined ? event.amount : 0;
  foreCastInfo.name = event.name;
  foreCastInfo.amount = currentAmount;
  const newTotalAmount = AmountType.FIX.valueOf() === currentAmountType ? currentAmount : totalAmount + currentAmount;
  foreCastInfo.totalAmount = newTotalAmount;
  foreCastInfo.amountDifference = newTotalAmount - totalAmount;
  foreCastInfo.colour = event.colour;
  foreCastInfo.date = event.date;
  foreCastInfo.icon = event.type !== undefined ? event.type.icon : '';
  foreCastInfo.eventInfo = event;
  return foreCastInfo;
};
