import { ISkillDTO } from 'src/app/models/skillDTO.model';
import { SkillService } from 'src/app/servisi/skill.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'list-of-skills-component',
    templateUrl: './list_of_skills.component.html',
    styleUrls: [
        './list_of_skills.component.css'
    ]
})

export class ListOfSkillsComponent implements OnInit {

    skills: Array<ISkillDTO> = [];

    skill: ISkillDTO = { id: -1, name: '' };


    //validation
    emptyName = false;

    constructor(private skillService: SkillService) { }

    ngOnInit() {
        this.getSkills();
    }

    getSkills() {

        this.skillService.getSkills().subscribe((data: Array<ISkillDTO>) => {
            this.skills = data;

        });

    }

    addSkill(): void {

        if (this.skill.name == '') {
            this.emptyName = true;
            return;
        }
        else {
            this.emptyName = false;
        }

        this.skillService.addSkill(this.skill).subscribe(
            (response) => { console.log(response); this.getSkills(); this.skill = { id: -1, name: '' }; },
            (error) => {
                console.log(error);

            });

        this.getSkills();

    }

    deleteSkill(skillId: string) {
        this.skillService.deleteSkill(skillId).subscribe(
            (response) => { console.log(response); this.getSkills(); },
            (error) => { console.log(error) }
        )
    }


    getSkillById(skillId: string) {
        this.skillService.getSkillById(skillId).subscribe(
            (data) => { this.skill = data; },
            (error) => { console.log(error) }
        );
    }

}
