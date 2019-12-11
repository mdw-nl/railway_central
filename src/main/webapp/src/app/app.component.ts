import { Component } from '@angular/core';
import { RestclientService} from './service/restclient.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  constructor(public restClient: RestclientService){

  }
}
