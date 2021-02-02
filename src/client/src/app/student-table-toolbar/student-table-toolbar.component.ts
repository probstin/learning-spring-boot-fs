import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-table-toolbar',
  templateUrl: './student-table-toolbar.component.html',
  styleUrls: ['./student-table-toolbar.component.css']
})
export class StudentTableToolbarComponent implements OnInit {

  @Input() studentsCount: number;

  constructor() { }

  ngOnInit(): void {

  }

  addStudent(): void {
    
  }

}
