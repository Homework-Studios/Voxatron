package game.tower.towers.aoe;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
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
    private float rotation = 0;

    public PylonCanon() {
        super(Type.PYLON_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        model = asset.getNewModel();
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
        Raylib.DrawModel(model, position.toRaylibVector3(), 5, type.getColor());
    }
}
