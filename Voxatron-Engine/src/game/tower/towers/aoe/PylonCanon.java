package game.tower.towers.aoe;

import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import game.tower.EnergyConsumer;

import static com.raylib.Raylib.*;

/**
 * concept:
 * <p>
 * - a tower that does aoe damage
 * - consumes energy
 * <p>
 * - deals aoe damage to all enemies in range by shooting a schockwave?
 */
public class PylonCanon extends EnergyConsumer {
    private int animFrameCounter;

    public PylonCanon() {
        super(Type.PYLON_CANON);
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
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        Model model = asset.getModel("pylonmodel");
        ModelAnimation animation = asset.getAnimation("pylonmodel");


        if (IsKeyDown(KEY_I)) {
            UpdateModelAnimation(model, animation, animFrameCounter);
            if (animFrameCounter >= animation.frameCount()) animFrameCounter = 0;
        }
        animFrameCounter++;
        Raylib.DrawModel(model, position.toRaylibVector3(), 10, type.getColor());
    }
}
