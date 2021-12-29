export interface DiscoverDto {

  active: boolean;
  userName: string;
  profilePicture: any;
  rating: number;
  rateCount: bigint;
  postsCount: number;
  categories: string[];
  price: number;
  city: string;

}

export class DiscoverFilters {
  rateCountLessThan?: number | any;
  rateCountMoreThan?: number | any;
  ratingMoreThan: number = 0;
  userName?: string;
  categories?: string[] = [];
}


export interface SortOption {
  value: any;
  display: string;
}
