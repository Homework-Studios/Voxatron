package game.tower.towers.energy;

import com.raylib.Raylib;
import game.tower.EnergyConsumer;
import game.tower.Factory;

public class EnergyFactory extends Factory {

    private EnergyConsumer[] consumers = new EnergyConsumer[0];

    public EnergyFactory() {
        super(Type.ENERGY_FACTORY);

        range = 100;
    }

    @Override
    public void gameTick() {
        if (currentTick >= provideEveryTick) {
            addEnergy(provideEnergy(5));
            currentTick = 0;
        }

        currentTick++;
    }

    @Override
    public void update() {
        consumers = gameManager.getClosestEnergyConsumers(position, range);
    }

    @Override
    public void render() {
        drawRange();

        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, type.getColor());

        drawEnergy();

        // draw a line to all the energy consumers in range

        for (EnergyConsumer consumer : consumers) {
            Raylib.DrawLine3D(position.toRaylibVector3(), consumer.position.toRaylibVector3(), type.getColor());
        }

    }
}
