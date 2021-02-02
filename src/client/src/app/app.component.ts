import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  students: any[];
  loadingStudents: boolean = true;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.getStudents();
  }

  getStudents() {
    this.http
      .get<any[]>('http://localhost:5000/api/students')
      .subscribe(
        students => {
          this.students = students;
          this.loadingStudents = false;
        },
        () => {
          this.students = [];
          this.loadingStudents = false;
        }
      );
  }

}
