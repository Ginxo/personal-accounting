import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LANGUAGES } from 'app/core/language/language.constants';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { CalendarService } from 'app/entities/calendar/calendar.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { ICalendar } from 'app/shared/model/calendar.model';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { filter, flatMap, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  version: string;
  calendars$: Observable<ICalendar[] | null>;
  selectedCalendar: ICalendar | null = null;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private calendarService: CalendarService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
    this.calendars$ = this.accountService.identity().pipe(
      flatMap(account =>
        this.calendarService.query({ 'userId.equals': account !== null ? account.id : '' }).pipe(
          filter((response: HttpResponse<ICalendar[]>) => response.ok),
          map((response: HttpResponse<ICalendar[]>) => response.body),
          map((calendars: ICalendar[] | null) => {
            this.accountService.setSelectedCalendar(calendars !== null && calendars.length > 0 ? calendars[0] : null);
            this.selectedCalendar = this.accountService.getSelectedCalendar();
            return calendars;
          })
        )
      )
    );
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  changeCalendar(calendar: ICalendar): void {
    this.accountService.setSelectedCalendar(calendar);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
