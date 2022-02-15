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


