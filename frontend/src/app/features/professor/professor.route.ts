import {Routes} from '@angular/router';
import {ProfessorListComponent} from './components/professor-list/professor-list.component';
import {ProfessorCreatFormComponent} from './components/professor-create-form/professor-creat-form.component';
import {ProfessorDetailComponent} from './components/professor-detail/professor-detail.component';

export const PROFESSOR_ROUTES: Routes = [

  {
    path: 'professors',
    component: ProfessorListComponent
  },

  {
    path: 'professors/new',
    component: ProfessorCreatFormComponent
  },

  {
    path: 'professors/:id',
    component: ProfessorDetailComponent
  }

]
