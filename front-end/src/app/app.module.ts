import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {WaitlistComponent} from './waitlist/waitlist.component';
import {RouterModule, Routes} from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

const appRoutes: Routes = [
  {path: '', component: WaitlistComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    WaitlistComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
