import {Creator} from '../model/creator';

export class Package {
    public id: number;
    public title: string;
    public creator: Creator;
    public intro: string;

    constructor(title: string, creator: Creator, intro: string) {
        this.title = title;
        this.creator = creator;
        this.intro = intro;
    }
}