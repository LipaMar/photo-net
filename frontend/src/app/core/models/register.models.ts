export class RegisterDto {
  constructor(public userName: string,
              public email: string,
              public active: boolean,
              public password: string) {
  }
}
