package game.tower;

public abstract class Factory extends Tower {

    public int provideEveryTick = 20;
    public int currentTick = 0;

    public float currentEnergy = 0;
    public int maxEnergy = 1000;

    public Factory(Type type) {
        super(type);
    }

    public float provideEnergy(int amount) {
        EnergyConsumer[] consumers = gameManager.getClosestEnergyConsumers(position, range);

        gameManager.addEnergy(amount);

        if (consumers.length > 5) {
            consumers = new EnergyConsumer[]{consumers[0], consumers[1], consumers[2], consumers[3], consumers[4]};
        }
        int energyPerConsumer = amount / (consumers.length == 0 ? 1 : consumers.length);

        float leftoverEnergy = 0;
        for (EnergyConsumer consumer : consumers) {
            leftoverEnergy += consumer.addEnergy(energyPerConsumer);
        }

        return leftoverEnergy;
    }

    // add energy to the factory as long as there is space
    public void addEnergy(float amount) {
        currentEnergy += amount;
        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
        }
    }
}
