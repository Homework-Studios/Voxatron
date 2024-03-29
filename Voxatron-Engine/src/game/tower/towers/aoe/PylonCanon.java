package game.tower.towers.aoe;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import game.GameManager;
import game.enemy.Enemy;
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
    private int delay;
    private int shot;

    public PylonCanon() {
        super(Type.PYLON_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/PylonModel");
        model = asset.getNewModel();
    }

    @Override
    public void gameTick() {
        if (hasEnergy(25)) {
            delay++;
            if (delay >= 60) {
                delay = 0;
                for (Enemy enemy : GameManager.instance.getEnemiesInRangeFromPosition(position, range)) {
                    enemy.damage(5);
                }
                consumeEnergy(25);
                shot = 5;
            }
        }
    }

    @Override
    public void update() {
        rotation += 0.01f;
        if (rotation > 360) rotation = 0;
        model.transform(Raylib.MatrixRotateY(rotation));
        if (shot > 0) {
            shot--;
        }
    }

    @Override
    public void render() {
        Vector3 pos = new Vector3(position).add(0, (float) Math.sin(rotation), 0);
        Raylib.DrawModel(model, pos.toRaylibVector3(), 3, type.getColor());
        drawEnergy();
        drawRange();
        if (shot > 0) {
            Vector3 copy = new Vector3(pos).add(0, 5, 0);
            Raylib.DrawCircle3D(copy.toRaylibVector3(), range, new Vector3(1, 0, 0).toRaylibVector3(), 90, Jaylib.WHITE);
        }
    }
}
