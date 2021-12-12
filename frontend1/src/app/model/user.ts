export class User {
    
    public id: number;
    public name: string;
    public username: string;
    public email: string;
    public roles: [];
    public token: string;
 
    constructor() {
        this.name = '';
        this.username = '';
        this.email = '';
        this.roles = [] ;
        this.token = '';
    }



}