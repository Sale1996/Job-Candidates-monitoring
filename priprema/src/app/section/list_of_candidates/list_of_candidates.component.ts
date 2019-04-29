import { Component, OnInit } from '@angular/core';
import { IJobCandidateDTO } from '../../models/jobCandidateDTO.model';
import { JobCandidateService } from '../../servisi/jobCandidate.service';
import { SkillService } from 'src/app/servisi/skill.service';
import { ISkillDTO } from 'src/app/models/skillDTO.model';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'list-of-candidates-component',
    templateUrl: './list_of_candidates.component.html',
    styleUrls: [
        './list_of_candidates.component.css'
    ]
})

export class ListOfCandidatesComponent implements OnInit {

    jobCandidates: Array<IJobCandidateDTO> = [];
    skills: Array<ISkillDTO> = [];
    jobCandidate: IJobCandidateDTO = { id: -1, name: '', email: '', telephoneNumber: '', dateOfBirth: null, skills: [] };
    selectedSkills = [];
    candidateName: string = '';
    skillsForSearching = [];
    closeResult: string;
    selectedCandidate: IJobCandidateDTO;
    newCandidateSkill = {};


    constructor(
        private candidateService: JobCandidateService,
        private skillService: SkillService,
        private modalService: NgbModal
    ) { }

    ngOnInit() {
        this.getCandidates();
        this.getSkills();
    }

    getSkills() {
        this.skillService.getSkills().subscribe((data: Array<ISkillDTO>) => {
            this.skills = data;

        });
    }
    getCandidates() {
        //ovo fazon uzimamo get zahtev iz servisa i kazemo mu e kada nabavis knjige onda to "data" ubaci u knjige
        this.candidateService.getCandidates().subscribe((data: Array<IJobCandidateDTO>) => {
            this.jobCandidates = data;

            console.log(data);
        });

    }

    addCandidate(): void {

        console.log(this.selectedSkills);

        for (var i = 0; i < this.selectedSkills.length; i++) {
            var skillId = Number(this.selectedSkills[i]);
            this.jobCandidate.skills.push({ id: skillId, name: '' });
        }



        this.candidateService.addCandidate(this.jobCandidate).subscribe(
            (response) => { console.log(response); this.getCandidates(); this.jobCandidate = { id: -1, name: '', email: '', telephoneNumber: '', dateOfBirth: null, skills: [] }; },
            (error) => {
                console.log(error);
            }
        );

    }


    deleteCandidate(candidateId: string) {
        this.candidateService.deleteCandidate(candidateId).subscribe(
            (response) => { console.log(response); this.getCandidates(); },
            (error) => { console.log(error) }
        )
    }



    getCandidateById(candidateId: string) {
        this.candidateService.getJobCandidateById(candidateId).subscribe(
            (data) => { this.jobCandidate = data; },
            (error) => { console.log(error) }
        );
    }


    getCandidatesByName() {

        if (this.candidateName == '') {
            this.getCandidates();
        }
        else {
            this.candidateService.getAllJobCandidatesByName(this.candidateName).subscribe((data: Array<IJobCandidateDTO>) => {
                this.jobCandidates = data;
                console.log(data);
            });
        }

    }

    getCandidatesBySkills() {

        console.log(this.skillsForSearching);

        var skillIds: Array<Number> = [];

        for (var i = 0; i < this.skillsForSearching.length; i++) {
            skillIds.push(Number(this.skillsForSearching[i]));
        }


        this.candidateService.getAllJobCandidatesBySkills(skillIds).subscribe((data) => {
            this.jobCandidates = data;
            console.log(data);

        }, (error) => {
            console.log(error); this.jobCandidates = [];
        });

    }

    addCandidateSkill(candidateId, skillId) {

        this.candidateService.candidateAddSkill(candidateId, skillId).subscribe((data) => {
            this.getCandidates();
            var selectedSkill = this.skills.find(item => item.id == skillId);
            this.selectedCandidate.skills = this.selectedCandidate.skills.concat(selectedSkill);

        })
    }

    removeCandidateSkill(candidateId, skillId) {

        this.candidateService.candidateRemoveSkill(candidateId, skillId).subscribe((data) => {
            this.getCandidates();
            this.selectedCandidate.skills = this.selectedCandidate.skills.filter(item => item.id !== skillId);
        })
    }

    open(content, candidate: IJobCandidateDTO) {
        this.selectedCandidate = candidate;
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }


}