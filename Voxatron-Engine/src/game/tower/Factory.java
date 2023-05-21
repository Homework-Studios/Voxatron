package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.Renderer;

public abstract class Factory extends Tower {

    public int provideEveryTick = 20;
    public int currentTick = 0;

    public float currentEnergy = 0;
    public int maxEnergy = 1000;
    public float efficiency = 0.1f;

    public Factory(Type type) {
        super(type);
    }

    public float provideEnergy(int amount) {
        EnergyConsumer[] consumers = gameManager.getClosestEnergyConsumers(position, range);

        gameManager.addEnergy(amount);

        int energyPerConsumer = amount / (consumers.length == 0 ? 1 : consumers.length);

        float leftoverEnergy = 0;
        for (EnergyConsumer consumer : consumers) {
            leftoverEnergy += consumer.addEnergy(energyPerConsumer);
        }

        leftoverEnergy *= efficiency;

        return leftoverEnergy;
    }

    // add energy to the factory as long as there is space
    public void addEnergy(float amount) {
        currentEnergy += amount;
        if (currentEnergy > maxEnergy) {
            currentEnergy = maxEnergy;
        }
    }

    public void drawEnergy() {
        Raylib.EndMode3D();
        Raylib.Vector2 screenPos = Raylib.GetWorldToScreen(position.toRaylibVector3(), Renderer.camera.getCamera());

        Raylib.DrawText(String.valueOf(currentEnergy), (int) screenPos.x(), (int) screenPos.y(), 20, Jaylib.WHITE);
        Raylib.BeginMode3D(Renderer.camera.getCamera());
    }
}
