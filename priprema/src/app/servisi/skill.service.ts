import { Injectable } from '@angular/core';
import { IJobCandidateDTO } from '../models/jobCandidateDTO.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ISkillDTO } from '../models/skillDTO.model';


const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'my-auth-token'
    })
};

@Injectable()
export class SkillService {

    constructor(private httpClient: HttpClient) { }



    getSkills() {
        return this.httpClient.get<ISkillDTO[]>('http://localhost:8090/api/skill/');
    }

    addSkill(skill: ISkillDTO) {

        let body = JSON.stringify(skill);

        if (skill.id != null) {
            return this.httpClient.put('http://localhost:8090/api/skill/' + skill.id, body, httpOptions);
        }
        else {
            return this.httpClient.post('http://localhost:8090/api/skill/', body, httpOptions);
        }

    }

    deleteSkill(skillId: string) {

        return this.httpClient.delete('http://localhost:8090/api/skill/' + skillId);

    }

    getSkillById(skillId: string) {

        return this.httpClient.get<ISkillDTO>('http://localhost:8090/api/skill/' + skillId);

    }


}