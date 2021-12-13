import {Creator} from '../model/creator';

export class Package {
    public id: number;
    public title: string;
    public creator: Creator;
    public intro: string;

    constructor() {
        this.title = '';
        this.creator = new Creator('');
        this.intro = '';
    }
}