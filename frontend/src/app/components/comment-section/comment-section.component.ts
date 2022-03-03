import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DatePipe} from "@angular/common";
import {CommentDto} from "../../core/models/profile.models";
import {DatePattern} from "../../core/enums/datePattern";

@Component({
  selector: 'comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.scss']
})
export class CommentSectionComponent implements OnInit {

  @Input() comments: CommentDto[];
  @Input() addCommentsEnabled: boolean = true;
  @Output() commentAdded = new EventEmitter<CommentDto>();


  comment = new CommentDto();

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit(): void {
  }

  formatDate(date: Date) {
    return this.datePipe.transform(date, DatePattern.DATE_HOUR_MIN);
  }

  sendComment() {
    this.commentAdded.emit(this.comment);
    this.comment.content="";
  }

  getColor(anonymous: boolean) {
    return anonymous ? "anon" : "normal";
  }
}
