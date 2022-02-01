import {ModalConfig} from "../modal/modal.config";

export class CalendarModalConfig implements ModalConfig{
  closeButtonLabel = "Zapisz";
  dismissButtonLabel = "Anuluj";
  modalTitle: string = "";

  constructor() {
  }

  disableCloseButton(): boolean {
    return false;
  }

  disableDismissButton(): boolean {
    return false;
  }

  hideCloseButton(): boolean {
    return false;
  }

  hideDismissButton(): boolean {
    return false;
  }




}
