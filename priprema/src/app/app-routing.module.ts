import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListOfCandidatesComponent } from './section/list_of_candidates/list_of_candidates.component';
import { ListOfSkillsComponent } from './section/list_of_skills/list_of_skills.component';


const routes: Routes = [
  { path: 'jobCandidates', component: ListOfCandidatesComponent },
  { path: 'skill', component: ListOfSkillsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class RouteModule { }

