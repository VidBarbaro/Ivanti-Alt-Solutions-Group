export class PackageVersion {
    public name: String;
    public readme: String;
    public url: string;

    // constructor(name: string, readme: string, url: string) {
    //     this.name = name;
    //     this.readme = readme;
    //     this.url = url;
    // }

    constructor(name: String, readme: String) {
        this.name = name;
        this.readme = readme;
    }
}