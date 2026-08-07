import {Routes} from '@angular/router';
import {ProfessorListComponent} from './components/professor-list/professor-list.component';

export const PROFESSOR_ROUTES: Routes = [

  {
    path: 'professors',
    component: ProfessorListComponent
  }

]
