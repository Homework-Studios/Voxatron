package game.tower.towers.aoe;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import game.tower.EnergyConsumer;

import static com.raylib.Raylib.Model;
import static com.raylib.Raylib.ModelAnimation;

/**
 * concept:
 * <p>
 * - a tower that does aoe damage
 * - consumes energy
 * <p>
 * - deals aoe damage to all enemies in range by shooting a schockwave?
 */
public class PylonCanon extends EnergyConsumer {
    private final Model model;
    private ModelAnimation animation;
    private int animFrameCounter;

    public PylonCanon() {
        super(Type.PYLON_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        model = asset.getModel();
    }

    @Override
    public void gameTick() {

    }

    //TODO: implement
    @Override
    public void update() {


    }

    @Override
    public void render() {
        Raylib.DrawModel(model, position.toRaylibVector3(), 5, type.getColor());
    }
}
