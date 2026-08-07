import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-course-detail',
  imports: [],
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.scss'
})
export class CourseDetailComponent implements OnInit{

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(){

    this.route.paramMap.subscribe(params => {
      const id = Number(
        params.get('id')
      );

      console.log(id);

    });

  }


}
