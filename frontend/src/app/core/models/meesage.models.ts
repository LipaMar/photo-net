export interface ChatDto {
  id: number,
  sender: string,
  recipient: string,
  messages: MessageDto[],
}

export interface MessageDto extends NewMessageDto {
  id: number,
  timestamp: string,
}

export interface NewMessageDto {
  content: string,
  chatRoomId: number,
  author: string,
}
