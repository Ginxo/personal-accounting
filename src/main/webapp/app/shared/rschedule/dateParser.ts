const ISO_DATE_FORMAT = /\d{4}-\d{2}-\d{2}/;

export const parseIsoDateStrToDate = (key: any, value: any): any => {
  return typeof value === 'string' && ISO_DATE_FORMAT.test(value) ? new Date(value) : value;
};
