import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WaitlistComponent } from './waitlist.component';

describe('WaitlistComponent', () => {
  let component: WaitlistComponent;
  let fixture: ComponentFixture<WaitlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WaitlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WaitlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
