export class PackageVersion {
    public name: string;
    public readme: string;
    public url: string;

    constructor(name: string, readme: string, url: string) {
        this.name = name;
        this.readme = readme;
        this.url = url;
    }
}