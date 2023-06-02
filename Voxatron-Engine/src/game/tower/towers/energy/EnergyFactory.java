package game.tower.towers.energy;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.render.shader.ShaderManager;
import game.tower.EnergyConsumer;
import game.tower.Factory;

public class EnergyFactory extends Factory {

    private final Raylib.Model model;
    private EnergyConsumer[] consumers = new EnergyConsumer[0];

    public EnergyFactory() {
        super(Type.ENERGY_FACTORY);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/EnergyFactory");
        model = asset.getModel();
        model.materials().shader(ShaderManager.instance.lightShader);
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

        if (model != null)
            Raylib.DrawModel(model, position.toRaylibVector3(), 3, type.getColor());
        // draw a line to all the energy consumers in range

        for (EnergyConsumer consumer : consumers) {
            Raylib.DrawLine3D(position.toRaylibVector3(), consumer.position.toRaylibVector3(), type.getColor());
        }

    }
}
