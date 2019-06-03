import { Injectable } from '@angular/core';
import { BehaviorSubject, of } from 'rxjs/index';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  /**
   * Observable of Errors
   * Contains all the errors received through the handleError function.
   * By subscribing to it, you will be able to manage errors.
   * {BehaviorSubject<any>}
   */
  public errors$: BehaviorSubject<{ operation: string, error?: HttpErrorResponse }[]> = new BehaviorSubject(null);

  /**
   * List of errors.
   * {Array}
   */
  private errors: { operation: string, error?: HttpErrorResponse }[] = [];

  constructor() {
  }

  /**
   * HandleError function.
   * This function updates the and error list (errors) and the
   * errors$ observable with the error received in parameter.
   * It also returns an Observable with the result to return in case of failure.
   * Note that this parameter is optional.
   * Parameters:
   * {HttpErrorResponse} error
   * {string} operation
   * {T} result
   * returns {Observable<T>}
   */
  handleError<T>(error: HttpErrorResponse, operation: string, result?: T) {
    this.errors.push({operation, error});
    this.errors$.next([...this.errors]);

    return of(result as T);
  }

  /**
   * ClearErrors function.
   * This function clears the error list and the observable.
   */
  clearErrors() {
    this.errors = [];
    this.errors$.next(null);
  }
}
