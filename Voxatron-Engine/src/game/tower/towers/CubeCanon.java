package game.tower.towers;

import com.raylib.Jaylib;
import com.raylib.Raylib;
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

    public CubeCanon() {
        super(Type.CUBE_CANON);
    }

    @Override
    public void update() {
        if (IsClicked(aabb)) {
            parentScene.removeElement3d(this);
            GameManager.instance.sell(type.getCost());
        }

        tryFire();
        if (hasEnergy(25) && canFire()) {
            target = GameManager.instance.getClosestEnemy(position, range);

            if (target != null) {
                consumeEnergy(25);
                fire();
                target.damage(25);
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

        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, type.getColor());

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
