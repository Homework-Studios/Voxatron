package game.tower.towers;

import com.raylib.Raylib;
import game.tower.EnergyConsumer;
import game.tower.Factory;

public class EnergyFactory extends Factory {

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
        gameTick();
    }

    @Override
    public void render() {
        drawRange();

        Raylib.DrawCube(position, 5, 5, 5, type.getColor());

        drawEnergy();

        // draw a line to all the energy consumers in range
        EnergyConsumer[] consumers = gameManager.getClosestEnergyConsumers(position, range);

        for (EnergyConsumer consumer : consumers) {
            Raylib.DrawLine3D(position, consumer.position, type.getColor());
        }
    }
}
