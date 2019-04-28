import { ISkillDTO } from "./skillDTO.model";


export interface IJobCandidateDTO {

    id: Number;
    name: String;
    email: String;
    telephoneNumber: String;
    dateOfBirth: Date;
    skills: Array<ISkillDTO>;

}