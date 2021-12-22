export interface SortParams {
  field: string;
  order: Order | string;
}

export enum Order {
  ASC,
  DESC
}
