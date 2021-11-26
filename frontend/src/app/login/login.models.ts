export class Credentials {
  constructor(public username: string,
              public password: string) {
  }
}

export interface LoggedDto {
  userName: string;
  active: boolean;
}
