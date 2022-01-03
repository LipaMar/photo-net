import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DatePipe} from "@angular/common";
import {CommentDto} from "../../core/models/profile.models";

@Component({
  selector: 'comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.scss']
})
export class CommentSectionComponent implements OnInit {

  @Input() comments: CommentDto[];
  @Output() commentAdded = new EventEmitter<CommentDto>();

  comment = new CommentDto();

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit(): void {
  }

  formatDate(date: Date) {
    return this.datePipe.transform(date, 'dd.MM.yyyy HH:mm');
  }

  sendComment() {
    this.commentAdded.emit(this.comment);
    this.comment.content="";
  }
}
