import {DiscoverDto} from "./discover.models";


export interface ProfileDto extends DiscoverDto {
  bio: string,
  posts: {
    id: number,
    photo: any,
    comments: number,
    likes: number,
    author: string
  }[],
  comments: CommentDto[] | any
}

export interface ProfileUpdateDto extends DiscoverDto {
  bio: string | any,
  userName: string | any,
  price: number | any,
  city: string | any,
  categories: string[] | any,
}

export class CommentDto {
  author?: string;
  target: string;
  content: string;
  added: Date;
  anonymous: boolean = false;
}
