import { NgModule } from '@angular/core';
import { PersonalAccountingSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { ForecastViewComponent } from './forecast/forecast.view.component';

@NgModule({
  imports: [PersonalAccountingSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ForecastViewComponent
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    PersonalAccountingSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    ForecastViewComponent,
    HasAnyAuthorityDirective
  ]
})
export class PersonalAccountingSharedModule {}
