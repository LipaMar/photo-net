export enum InputTypes {
  TEXT = 'text',
  PASSWORD = 'password',
}

export enum MethodType {
  POST = 'POST',
  GET = 'GET',
}

export interface FormField {
  label: string;
  field: string;
  type: InputTypes;
}
