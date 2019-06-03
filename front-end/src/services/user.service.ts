import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs/index';
import {User} from '../models/User.model';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, filter, take, tap} from 'rxjs/internal/operators';
import {ErrorService} from './error';
import {httpOptionsBase, serverUrl} from '../configs/server.config';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private studentList: User[] = [];

  private url = serverUrl + '/students';

  private httpOptions = httpOptionsBase;

  /**
   * Observable which contains the list of the students.
   */
  public students$: BehaviorSubject<User[]> = new BehaviorSubject(this.studentList);

  /**
   * StudentViewed: Observable which stores the current student viewed (student details).
   * {BehaviorSubject<User>}
   */
  public studentViewed$: BehaviorSubject<User> = new BehaviorSubject<User>(null);

  constructor(public http: HttpClient, private errorService: ErrorService) {
  }

  getStudent() {
    this.http.get<User[]>(this.url)
      .pipe(
        take(1),
        catchError((err: HttpErrorResponse) => this.errorService.handleError<User[]>(err, 'get /students', [])))
      .subscribe((students: User[]) => {
        this.students$.next(students);
        this.studentList = students;

        this.sortBy('studentNumber');
      });
  }

  getStudentsByObservable(): Observable<User[]> {
    this.log(this.studentList.toString());
    return this.http.get<User[]>(this.url).pipe(
      tap(_ => this.log('fetched students'))
    );
  }

  getStudentById(id: number): Observable<User> {
    const url = `${this.url}/${id}`;
    return this.http.get<User>(url).pipe(
      tap(_ => this.log(`fetched university id=${id}`))
    );
  }

  updateStudent(student: User) {
    const urlWithId = this.url + '/' + student.id.toString();
    this.http.put<User>(urlWithId, student, this.httpOptions)
      .pipe(
        take(1),
        catchError((err: HttpErrorResponse) =>
          this.errorService.handleError<User>(err, 'put /students by id=${student.id}'))
      ).subscribe((newStudent) => this.getStudent());
  }

  addStudent(student: User) {
    this.http.post<User>(this.url, student, this.httpOptions)
      .pipe(
        take(1),
        catchError((err: HttpErrorResponse) =>
          this.errorService.handleError<User>(err, 'post /students'))
      ).subscribe((newStudent) => this.getStudent());
  }

  deleteStudent(student: User) {
    const urlWithId = this.url + '/' + student.id;
    this.http.delete<User>(urlWithId, this.httpOptions)
      .pipe(
        take(1),
        catchError((err: HttpErrorResponse) =>
          this.errorService.handleError<User>(err, `delete /students by id=${student.id}`))
      ).subscribe((newStudent) => this.getStudent());
  }


  updatePosition(user: User[]) {


    for (const student of user) {
      if (student.studentNumber !== 0) {
        student.studentNumber = student.studentNumber - 1;
        if(student.studentNumber == 1){
          student.notif = "vous êtes le suivant !"
        }
        else student.notif = "Attendez votre tour svp"
      }
      else student.notif = "Merci, au revoir !";
      this.log('' + student.studentNumber);
      this.updateStudent(student);
      this.getStudent();
    }


  }


  /** Log a UserService message with the MessageService */
  private log(message: string) {
    console.log(message);
  }
  sortBy(field: string) {
    this.studentList.sort((a: any, b: any) => {
      if (a[field] < b[field]) {
        return -1;
      } else if (a[field] > b[field]) {
        return 1;
      } else {
        return 0;
      }
    });
  }
}
