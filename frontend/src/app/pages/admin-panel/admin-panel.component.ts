import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {AdminPanelService} from "./admin-panel.service";
import {SubscriptionContainer} from "../../core/utils/subscription-container";
import {UserInfoDto} from "../../core/models/user-info.models";

@Component({
  selector: 'admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit, OnDestroy {

  displayedColumns: string[] = ['userName', 'email', 'active', 'operations'];
  dataSource: MatTableDataSource<any>;
  subscriptions = new SubscriptionContainer();

  constructor(private service: AdminPanelService) {
  }

  ngOnInit(): void {
    this.subscriptions.add = this.service.getUsersList().subscribe(usersInfo => {
      this.dataSource = new MatTableDataSource(usersInfo);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnDestroy(): void {
    this.subscriptions.dispose();
  }

  ban(element: UserInfoDto) {
    this.subscriptions.add = this.service.ban(element).subscribe(() => {
      this.subscriptions.add = this.service.getUsersList().subscribe(usersInfo => {
        this.dataSource = new MatTableDataSource(usersInfo);
      });
    });
  }

  unban(element: UserInfoDto) {
    this.subscriptions.add = this.service.unban(element).subscribe(() => {
      this.subscriptions.add = this.service.getUsersList().subscribe(usersInfo => {
        this.dataSource = new MatTableDataSource(usersInfo);
      });
    });
  }
}
