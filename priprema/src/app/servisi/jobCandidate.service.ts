import { Injectable } from '@angular/core';
import { IJobCandidateDTO } from '../models/jobCandidateDTO.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';



const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'my-auth-token'
    })
};

@Injectable()
export class JobCandidateService {

    //uvek stavljaj da je prazan niz kako ne bi dolazio do errora
    candidates: IJobCandidateDTO[] = [];



    constructor(private httpClient: HttpClient) { }

    getCandidates() {
        return this.httpClient.get<IJobCandidateDTO[]>('http://localhost:8090/api/jobcandidate/');
    }

    addCandidate(candidate: IJobCandidateDTO) {

        let body = JSON.stringify(candidate);

        if (candidate.id) {
            return this.httpClient.put('http://localhost:8090/api/jobcandidate/' + candidate.id, body, httpOptions);
        }
        else {
            return this.httpClient.post('http://localhost:8090/api/jobcandidate/', body, httpOptions);
        }


    }

    deleteCandidate(candidateId: string) {

        return this.httpClient.delete('http://localhost:8090/api/jobcandidate/' + candidateId);

    }

    getJobCandidateById(candidateId: string) {

        return this.httpClient.get<IJobCandidateDTO>('http://localhost:8090/api/jobcandidate/' + candidateId);

    }
}