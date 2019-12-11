import { EventEmitter, Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Router } from "@angular/router";

// THIS SERVICE CONTAINS GENERAL FEATURES

@Injectable({
  providedIn: "root"
})
export class RestclientService {
  public navigationState = new Map<string, string>();
  private defaultProjectIndex = 1; // TODO get this from user profile config
  protected projectIndex;
  public projectsForUser: Array<String>;
  public API_URL: String =
    "http://" + environment.host + ":" + environment.port + "/";
  public projectHasActiveUploads = false;
  public projectHasActiveComputations = false;
  private source;
  public emitter;

  constructor(private http: HttpClient, private router: Router) {
    this.projectIndex = this.defaultProjectIndex;
    this.getProjectsForUser().subscribe(projects => {
      this.projectsForUser = projects;
      console.log("Projects for user: " + JSON.stringify(this.projectsForUser));
    });
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };
  }

  private getProjectsForUser() {
    const httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    };
    return this.http.get<Array<String>>(
      this.API_URL + "api/projects",
      httpOptions
    );
  }

  switchProject(projectIndex) {
    this.projectIndex = projectIndex;
    this.router.navigate([this.router.url]);
  }

  switchProjectByName(projectName: String) {
    this.projectIndex = this.projectsForUser.indexOf(projectName) + 1; // array starts at 0
    this.router.navigate([this.router.url]);

    console.log(this.router.routeReuseStrategy);
  }

  getProjectName() {
    return this.projectsForUser[this.projectIndex];
  }

  getProjectIndex() {
    return this.projectIndex;
  }

  getNotifications(): void {
    this.source = new EventSource(this.API_URL + "api/notification");
    this.emitter = new EventEmitter<Notification>();
    this.source.addEventListener("message", message => {
      let n: Notification = message.data;
      this.emitter.emit(n);
    });
  }
}
