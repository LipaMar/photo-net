import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastrService {

  constructor(private toastr: ToastrService) { }
}
