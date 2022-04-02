export interface DiscoverDto {

  active: boolean;
  isPublic: boolean;
  userName: string;
  profilePicture: any;
  rating: number;
  rateCount: bigint;
  postsCount: number;
  categories: string[];
  price: number;
  city: string;
  observers: number;
}

export class DiscoverFilters {
  rateCountLessThan?: number | any;
  rateCountMoreThan?: number | any;
  ratingMoreThan: number | any = 0;
  userName?: string;
  categories?: string[] = [];
}


export interface SortOption {
  value: any;
  display: string;
  comparator?: (a: any, b: any) => number;
  extractor?: (a: any) => any;
}
