import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'full-screen-modal',
  templateUrl: './full-screen-modal.component.html',
  styleUrls: ['./full-screen-modal.component.scss']
})
export class FullScreenModalComponent implements OnInit {

  @ViewChild('modal') modal: ElementRef;
  image: string;

  constructor() {
  }

  ngOnInit(): void {
  }

  closeModal() {
    this.modal.nativeElement.style.display = 'none';
  }

  openModal(photo: string) {
    this.image = photo;
    this.modal.nativeElement.style.display = 'block';
  }

}
