import {Routes} from '@angular/router';
import {ProfessorListComponent} from './components/professor-list/professor-list.component';
import {ProfessorCreateFormComponent} from './components/professor-create-form/professor-create-form.component';
import {ProfessorDetailComponent} from './components/professor-detail/professor-detail.component';

export const PROFESSOR_ROUTES: Routes = [

  {
    path: 'professors',
    component: ProfessorListComponent
  },

  {
    path: 'professors/new',
    component: ProfessorCreateFormComponent
  },

  {
    path: 'professors/:id',
    component: ProfessorDetailComponent
  }

]
