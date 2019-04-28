import { Component, OnInit } from '@angular/core';
import { $ } from 'protractor';
import { Router } from '@angular/router';

@Component({
    selector: 'navbar-component',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {


    constructor(private ruter: Router) { }

    ngOnInit() { }

    prikaziKnjige() {
        this.ruter.navigate(["/jobCandidates"]);
    }
}