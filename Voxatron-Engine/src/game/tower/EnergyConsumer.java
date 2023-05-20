package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.Renderer;

public abstract class EnergyConsumer extends Tower
{

    public int availableEnergy = 0;
    public int maxEnergy = 100;

    public EnergyConsumer(Type type) {
        super(type);
    }

    public void consumeEnergy(int amount) {
        availableEnergy -= amount;
    }

    public int addEnergy(int amount) {
        // try to add as much energy as possible
        if (amount + availableEnergy <= maxEnergy) {
            availableEnergy += amount;
            return 0;
        }

        // add as much energy as possible
        int addedEnergy = maxEnergy - availableEnergy;
        availableEnergy = maxEnergy;

        // all leftover energy is returned
        return amount - addedEnergy;
    }

    public boolean hasEnergy(int amount) {
        return availableEnergy >= amount;
    }

    public int getAvailableEnergy() {
        return availableEnergy;
    }

    public void drawEnergy() {
        Raylib.EndMode3D();
        Raylib.Vector2 screenPos = Raylib.GetWorldToScreen(position, Renderer.camera.getCamera());

        Raylib.DrawText(String.valueOf(availableEnergy), (int) screenPos.x(), (int) screenPos.y(), 20, Jaylib.WHITE);
        Raylib.BeginMode3D(Renderer.camera.getCamera());
    }
}
