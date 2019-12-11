import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/service/authentication.service';
import {RestclientService} from "../../service/restclient.service";

declare var $:any;

@Component({
    moduleId:module.id,
    selector: 'login-cmp',
    templateUrl: './login.component.html'
})

export class LoginComponent implements OnInit{
    focus;
    focus1;
    focus2;
    test: Date = new Date();
    private toggleButton;
    private sidebarVisible: boolean;
    private nativeElement: Node;
    public username: String;
    public password: String;
    public invalidLogin = false;


    constructor(private element: ElementRef, private authenticationService: AuthenticationService, private router: Router,
                private restClientService: RestclientService) {
        this.nativeElement = element.nativeElement;
        this.sidebarVisible = false;
    }

    login(username, password) {
        let success = false;
        this.authenticationService.login(username, password).subscribe(response => {success = (response === 'true');
                if (success) {
                    sessionStorage.setItem('username', username)
                    this.router.navigate(['/pages/dashboard'])
                    this.invalidLogin = false
                } else {
                    this.authenticationService.logOut()
                    this.router.navigate(['/pages/login'])
                    this.invalidLogin = true
                }},
            error => {alert('error: ' + JSON.stringify(error))});
    }

    checkFullPageBackgroundImage() {
        var $page = $('.full-page');
        var image_src = $page.data('image');

        if(image_src !== undefined){
            var image_container = '<div class="full-page-background" style="background-image: url(' + image_src + ') "/>'
            $page.append(image_container);
        }
    };

    ngOnInit(){
        this.checkFullPageBackgroundImage();
        var body = document.getElementsByTagName('body')[0];
        body.classList.add('login-page');
        var navbar : HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];

        setTimeout(function() {
            // after 1000 ms we add the class animated to the login/register card
            $('.card').removeClass('card-hidden');
        }, 700)
    }

    ngOnDestroy() {
        var body = document.getElementsByTagName('body')[0];
        body.classList.remove('login-page');
    }

    sidebarToggle() {
        var toggleButton = this.toggleButton;
        var body = document.getElementsByTagName('body')[0];
        var sidebar = document.getElementsByClassName('navbar-collapse')[0];
        if(this.sidebarVisible == false){
            setTimeout(function(){
                toggleButton.classList.add('toggled');
            },500);
            body.classList.add('nav-open');
            this.sidebarVisible = true;
        } else {
            this.toggleButton.classList.remove('toggled');
            this.sidebarVisible = false;
            body.classList.remove('nav-open');
        }
    }
}
