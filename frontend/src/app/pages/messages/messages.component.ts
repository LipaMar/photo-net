import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessagesService} from "./messages.service";
import {ChatDto, MessageDto, NewMessageDto} from "../../core/models/meesage.models";
import {LoginService} from "../../login/login.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {FormControl, FormGroup} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {DatePattern} from "../../core/enums/datePattern";

@Component({
  selector: 'messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit, OnDestroy {

  chats: ChatDto[];
  messages: MessageDto[] = [];
  loggedUser: string;
  subscriptions = new SubscriptionContainer();
  selectedChat: ChatDto | null;
  form = new FormGroup({content: new FormControl()});

  constructor(private messagesService: MessagesService,
              private loginService: LoginService,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.loginService.doGetIsLogged().subscribe(info => {
      this.loggedUser = info.userName;
      this.subscriptions.add = this.messagesService.getAllChats(this.loggedUser).subscribe(response => this.chats = response);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  populateMessages(chat: ChatDto) {
    this.messages = chat.messages;
    this.messages.map(message => {
      const timestamp = this.datePipe.transform(message.timestamp, DatePattern.TIME_DATE);
      message.timestamp = timestamp ? timestamp : message.timestamp;
    })
    this.selectedChat = chat;
  }

  positionBasedOnSender(message: MessageDto) {
    return message.author === this.loggedUser ? 'justify-content-end' : 'justify-content-start';
  }

  sendMessage() {
    const content = this.form.get("content")?.value;
    if (!this.selectedChat || !content || content.trim().length === 0) {
      return;
    }
    const message = {
      content: content,
      author: this.loggedUser,
      chatRoomId: this.selectedChat.id,
    } as NewMessageDto;
    this.subscriptions.add = this.messagesService.sendMessage(message).subscribe(() => {
      this.subscriptions.add = this.messagesService.getAllChats(this.loggedUser).subscribe(response => {
        this.chats = response;
        const chat = response.find(value => value.id === this.selectedChat?.id);
        if (chat) {
          this.populateMessages(chat);
        }
      });
    });
    this.form.get("content")?.reset();
  }

  nameBasedOnSender(chat: ChatDto) {
    return chat.sender === this.loggedUser ? chat.recipient : chat.sender;
  }
}
