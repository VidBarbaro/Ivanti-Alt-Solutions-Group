export class SystemRequirements {
    public processorType: string;
    public ram: string;
    public graphicsCard: string;

    constructor(processorType: string, ram: string, graphicsCard: string) {
        this.processorType = processorType;
        this.ram = ram;
        this.graphicsCard = graphicsCard;
    }
}