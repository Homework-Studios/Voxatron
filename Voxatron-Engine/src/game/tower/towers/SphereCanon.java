package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

/**
 * concept:
 * <p>
 * - a tower that shoots spheres
 * <p>
 * - aoe tower
 * <p>
 * - deals aoe damage by exploding on impact
 */
public class SphereCanon extends Tower {

    public SphereCanon() {
        super(Type.SPHERE_CANON);

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, type.getColor());
    }

    @Override
    public void gameTick() {

    }
}