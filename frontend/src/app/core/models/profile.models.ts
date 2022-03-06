import {DiscoverDto} from "./discover.models";
import {IMasonryGalleryImage} from "ngx-masonry-gallery";


export interface PostDto {
  id: number,
  photo: any,
  comments: number,
  likes: number,
  author: string
  timestamp: string
}

export interface PostDisplay extends PostDto {
  liked?: boolean;
  timestampToDisplay?: any;
}

export interface ProfileDto extends DiscoverDto {
  bio: string,
  posts: PostDto[],
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

export enum MeetingStatus {
  FREE = 'FREE',
  NEW = 'NEW',
  ACCEPTED = 'ACCEPTED',
  ARCHIVAL = 'ARCHIVAL',
  CANCELED = 'CANCELED'
}

export interface MeetingDto {
  id?: number,
  date: string,
  timeStart: string,
  status: MeetingStatus | string,
  userBooked?: string,
  owner: string;
  price?: number | string;
}

export class MeetingDisplay implements MeetingDto {
  id?: number | undefined;
  date: string;
  timeStart: string;
  status: string;
  userBooked?: string | undefined;
  owner: string;
  price?: string | number | undefined;

  constructor(meetingDto: MeetingDto, public statusDisplay: string) {
    this.id = meetingDto.id;
    this.date = meetingDto.date;
    this.timeStart = meetingDto.timeStart;
    this.status = meetingDto.status;
    this.userBooked = meetingDto.userBooked;
    this.owner = meetingDto.owner;
    this.price = meetingDto.price;
  }

}

export interface ScheduleDto {
  owner: string,
  disabled: boolean,
  meetings: MeetingDto[],
  saveDate?: string;
}

export interface BookMeetingDto {
  id: number;
  date: string;
  timeStart: string;
  price: number;
  photographer: string;
}

export class PostImage extends IMasonryGalleryImage {

  constructor(imageUrl: string, public post?: PostDto) {
    super();
    this.imageUrl = imageUrl;
  }
}

