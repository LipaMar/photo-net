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
  comments:{
    author: string;
    target: string;
    content: string;
    added: Date;
  }[]
}
