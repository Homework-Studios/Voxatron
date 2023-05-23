package game.tower.towers.singletarget;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.assets.AssetManager;
import engine.assets.basic.ModelAsset;
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

    public Raylib.BoundingBox aabb = new Raylib.BoundingBox();
    private final Raylib.Model base;
    private final Raylib.Model canon;

    public CubeCanon() {
        super(Type.CUBE_CANON);
        ModelAsset asset = new AssetManager<ModelAsset>().getAsset("Game/Towers/CubeCanon");
        base = asset.getNewModel("cubecanonbase");
        canon = asset.getNewModel("cubecanoncanon");
    }

    @Override
    public void update() {
        if (IsClicked(aabb)) {
            parentScene.removeElement3d(this);
            GameManager.instance.sell(type.getCost());
        }
        target = GameManager.instance.getClosestEnemy(position, range);
        if(target != null) //TODO: fix this
            canon.transform(Raylib.MatrixLookAt(position.toRaylibVector3(), target.position.toRaylibVector3(), new Raylib.Vector3().y(1)));
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

        aabb.min(new Raylib.Vector3().x(position.x - 2.5f).y(position.y - 2.5f).z(position.z - 2.5f));
        aabb.max(new Raylib.Vector3().x(position.x + 2.5f).y(position.y + 2.5f).z(position.z + 2.5f));
    }

    @Override
    public void render() {

        drawRange();


        Raylib.DrawModel(base, position.toRaylibVector3(), 5, type.getColor());
        Raylib.DrawModel(canon, position.toRaylibVector3(), 5, type.getColor());

        drawEnergy();

        // draw a line to the target if not null
        if (target != null) {
            Raylib.DrawLine3D(position.toRaylibVector3(), target.position.toRaylibVector3(), Jaylib.WHITE);
        }
    }

    @Override
    public void gameTick() {

    }
}
