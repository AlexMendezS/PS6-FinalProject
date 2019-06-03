import {Component, OnInit} from '@angular/core';

import {User} from '../../models/User.model';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-waitlist',
  templateUrl: './waitlist.component.html',
  styleUrls: ['./waitlist.component.css']
})
export class WaitlistComponent implements OnInit {

  user: User[];

  constructor(private userService: UserService) {
    this.getUser();
  }

  ngOnInit() {
  }

  getUser(): void {
    this.userService.students$.subscribe((students) => this.user = students);
    this.userService.getStudent();
  }

}
