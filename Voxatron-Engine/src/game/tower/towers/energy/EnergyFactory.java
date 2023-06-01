package game.tower.towers.energy;

import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.shader.ShaderManager;
import game.tower.EnergyConsumer;
import game.tower.Factory;

public class EnergyFactory extends Factory {

    private EnergyConsumer[] consumers = new EnergyConsumer[0];
    private final Raylib.Model model;

    public EnergyFactory() {
        super(Type.ENERGY_FACTORY);
        model = Raylib.LoadModelFromMesh(Raylib.GenMeshCube(5, 5, 5));
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

        Vector3 pos = new Vector3(position).add(0, 5, 0);
        Raylib.DrawModel(model, pos.toRaylibVector3(), 1, type.getColor());
        // draw a line to all the energy consumers in range

        for (EnergyConsumer consumer : consumers) {
            Raylib.DrawLine3D(position.toRaylibVector3(), consumer.position.toRaylibVector3(), type.getColor());
        }

    }
}
