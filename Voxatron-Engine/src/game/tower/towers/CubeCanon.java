package game.tower.towers;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import game.tower.Tower;

/**
 * concept:
 * <p>
 * - a tower that shoots cubes
 * <p>
 * - single target tower
 * <p>
 * - no aoe damage
 */
public class CubeCanon extends Tower {

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

        target = GameManager.instance.getClosetEnemy(position, range);

        aabb.min(new Raylib.Vector3().x(position.x() - 2.5f).y(position.y() - 2.5f).z(position.z() - 2.5f));
        aabb.max(new Raylib.Vector3().x(position.x() + 2.5f).y(position.y() + 2.5f).z(position.z() + 2.5f));
    }

    @Override
    public void render() {

        drawRange();

        Raylib.DrawCube(position, 5, 5, 5, type.getColor());

        // draw a line to the target if not null
        if (target != null) {
            Raylib.DrawLine3D(position, target.position, Jaylib.WHITE);
        }
    }

    @Override
    public void gameTick() {

    }
}
