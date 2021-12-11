export interface Pageable<Type> {
  content: Type[]
  pageable: {
    sort: {
      sorted: boolean,
      unsorted: boolean,
      empty: boolean
    },
    offset: number,
    pageNumber: number,
    pageSize: number,
    paged: boolean,
    unpaged: boolean
  },
  last: boolean,
  totalElements: number,
  totalPages: number,
  size: number,
  number: number,
  sort: {
    sorted: boolean,
    unsorted: boolean,
    empty: boolean
  },
  numberOfElements: number,
  empty: boolean
}
