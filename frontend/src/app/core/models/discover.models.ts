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
export interface DiscoverFilters {

  userName: string;
  ratingMoreThan: number;
  ratingLessThan: number;
  rateCountMoreThan: number;
  rateCountLessThan: number;

}


export interface SortOption {
  value: any;
  display: string;
}
