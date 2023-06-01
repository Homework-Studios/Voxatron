package game.tower.towers.singletarget;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
import engine.math.Vector3;
import game.GameManager;
import game.tower.EnergyConsumer;

/**
 * concept:
 * <p>
 * - a tower that shoots cubes
 * <p>
 * - single target tower
 * <p>
 * - no aoe damage
 */
public class CubeCanon extends EnergyConsumer {

    private final Raylib.Model base;
    private final Raylib.Model canon;

    public CubeCanon() {
        super(Type.CUBE_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/CubeCanon");
        base = asset.getModel("cubecanonbase");
        canon = asset.getNewModel("cubecanoncanon");
    }

    @Override
    public void update() {
        target = GameManager.instance.getFurthestEnemyInRange(position, range);
        if (target != null && target.position.distance(position) < range)
            //make canon look at target
            canon.transform(Raylib.MatrixRotateY((float) Math.atan2(target.position.x - position.x, target.position.z - position.z)));
        tryFire();
        if (hasEnergy(5) && canFire()) {
            if (target != null) {
                consumeEnergy(5);
                fire();
                target.damage(5);
            }
        } else {
            target = null;
        }
    }

    @Override
    public void render() {

        drawRange();

        Raylib.DrawModel(base, position.toRaylibVector3(), 2, type.getColor());
        Raylib.DrawModel(canon, position.toRaylibVector3(), 2, type.getColor());


        drawEnergy();

        // draw a line to the target if not null
        if (target != null) {
            Vector3 copy = new Vector3(position).add(0, 4, 0);
            Raylib.DrawLine3D(copy.toRaylibVector3(), target.position.toRaylibVector3(), Jaylib.WHITE);
        }
    }

    @Override
    public void gameTick() {

    }
}
