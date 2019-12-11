import { Component, OnInit, AfterViewInit, AfterViewChecked, AfterContentInit, ElementRef } from '@angular/core';
import { AuthenticationService } from 'app/service/authentication.service';


var misc:any ={
    navbar_menu_visible: 0,
    active_collapse: true,
    disabled_collapse_init: 0,
}

//Metadata
export interface RouteInfo {
    path: string;
    title: string;
    type: string;
    collapse?: string;
    icontype: string;
    // icon: string;
    children?: ChildrenItems[];
}

export interface ChildrenItems {
    path: string;
    title: string;
    ab: string;
    type?: string;
}

//Menu Items
export const ROUTES: RouteInfo[] = [{
        path: '/pages/dashboard',
        title: 'Dashboard',
        type: 'link',
        icontype: 'nc-icon nc-layout-11'
    }, {
        path: '/pages/stations',
        title: 'Stations',
        type: 'link',
        icontype: 'nc-icon nc-layout-11'
    }, {
        path: '/pages/trains',
        title: 'Trains',
        type: 'link',
        icontype: 'nc-icon nc-layout-11'
    }, {
        path: '/pages/results',
        title: 'Results',
        type: 'link',
        icontype: 'nc-icon nc-layout-11'
}];

@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent {
    private toggleButton;
    private nativeElement;
    private sidebarVisible;

    constructor (private authenticationService: AuthenticationService,  private element : ElementRef){
        this.nativeElement = element.nativeElement
        this.sidebarVisible = false;
    }
    
    public menuItems: any[];
    isNotMobileMenu(){
        if( window.outerWidth > 991){
            return false;
        }
        return true;
    }

    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);
        const body = document.getElementsByTagName('body')[0];
        const sidebar: HTMLElement = this.element.nativeElement;
        this.toggleButton = sidebar.getElementsByClassName('navbar-toggler')[0];
        if (body.classList.contains('sidebar-mini')) {
            misc.sidebar_mini_active = true;
        }
    }
    ngAfterViewInit(){
    }

    logOut() {
        this.authenticationService.logOut()
    }

    minimizeSidebar(){
        const body = document.getElementsByTagName('body')[0];
        
        if (misc.sidebar_mini_active === true) {
            body.classList.remove('sidebar-mini');
            misc.sidebar_mini_active = false;
        } else {
            setTimeout(function() {
                body.classList.add('sidebar-mini');
                misc.sidebar_mini_active = true;
            }, 300);
        }
  
        // we simulate the window Resize so the charts will get updated in realtime.
        const simulateWindowResize = setInterval(function() {
            window.dispatchEvent(new Event('resize'));
        }, 180);
  
        // we stop the simulation of Window Resize after the animations are completed
        setTimeout(function() {
            clearInterval(simulateWindowResize);
        }, 1000);
      }

      sidebarOpen(){
        var toggleButton = this.toggleButton;
        var html = document.getElementsByTagName('html')[0];
        setTimeout(function(){
            toggleButton.classList.add('toggled');
        },500);
        const mainPanel =  <HTMLElement>document.getElementsByClassName('main-panel')[0];
        if (window.innerWidth < 991) {
          mainPanel.style.position = 'fixed';
        }
        html.classList.add('nav-open');
        this.sidebarVisible = true;
    }
    sidebarClose(){
        var html = document.getElementsByTagName('html')[0];
        this.toggleButton.classList.remove('toggled');
        this.sidebarVisible = false;
        html.classList.remove('nav-open');
        const mainPanel =  <HTMLElement>document.getElementsByClassName('main-panel')[0];

        if (window.innerWidth < 991) {
          setTimeout(function(){
            mainPanel.style.position = '';
          }, 500);
        }
    }
    sidebarToggle(){
        // var toggleButton = this.toggleButton;
        // var body = document.getElementsByTagName('body')[0];
        if(this.sidebarVisible == false){
            this.sidebarOpen();
        } else {
            this.sidebarClose();
        }
    }
}
