import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { SectionComponent } from './section/section.component';
import { FormsModule } from '@angular/forms';

import { RouteModule } from './app-routing.module';
import { ListOfCandidatesComponent } from './section/list_of_candidates/list_of_candidates.component';
import { ListOfSkillsComponent } from './section/list_of_skills/list_of_skills.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { JobCandidateService } from './servisi/jobCandidate.service';
import { SkillService } from './servisi/skill.service';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalBackdrop } from '@ng-bootstrap/ng-bootstrap/modal/modal-backdrop';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavbarComponent,
    FooterComponent,
    SectionComponent,
    ListOfCandidatesComponent,
    ListOfSkillsComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouteModule,
    FormsModule,
    NgbModalModule

  ],
  providers: [
    JobCandidateService,
    SkillService

  ],
  entryComponents: [ListOfCandidatesComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
