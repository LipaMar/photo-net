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
  rateCountLessThan: number;
  rateCountMoreThan: number;
  ratingLessThan: number;
  ratingMoreThan: number;
  userName: string;
}


export interface SortOption {
  value: any;
  display: string;
}
