package game.tower.towers.aoe;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import engine.render.shader.ShaderManager;
import game.tower.EnergyConsumer;

/**
 * concept:
 * <p>
 * - a tower that does aoe damage
 * - consumes energy
 * <p>
 * - deals aoe damage to all enemies in range by shooting a schockwave?
 */
public class PylonCanon extends EnergyConsumer {
    private final Raylib.Model model;
    private final Raylib.Model sphere;
    private float rotation = 0;

    public PylonCanon() {
        super(Type.PYLON_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        model = asset.getNewModel();

        sphere = Raylib.LoadModelFromMesh(Raylib.GenMeshSphere(1, 32, 32));
        sphere.materials().shader(ShaderManager.instance.lightShader);
    }

    @Override
    public void gameTick() {

    }

    //TODO: implement
    @Override
    public void update() {
        rotation += 0.01f;
        if (rotation > 360) rotation = 0;
        model.transform(Raylib.MatrixRotateY(rotation));
    }

    @Override
    public void render() {
        Vector3 pos = new Vector3(position).add(0, (float) Math.sin(rotation), 0);
        Raylib.DrawModel(model, pos.toRaylibVector3(), 3, type.getColor());
    }
}
