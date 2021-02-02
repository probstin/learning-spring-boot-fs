import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-table',
  templateUrl: './student-table.component.html',
  styleUrls: ['./student-table.component.css']
})
export class StudentTableComponent implements OnInit {

  @Input() students: any[];

  displayedColumns: string[] = ['studentId', 'firstName', 'lastName', 'email', 'gender'];

  constructor() { }

  ngOnInit(): void {
    // generate key/value objects from input 
    // to dynamically render the table
  }

}
