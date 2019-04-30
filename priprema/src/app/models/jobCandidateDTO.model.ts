import { ISkillDTO } from "./skillDTO.model";


export interface IJobCandidateDTO {

    id: Number;
    name: string;
    email: string;
    telephoneNumber: string;
    dateOfBirth: Date;
    skills: Array<ISkillDTO>;

}