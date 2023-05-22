package game.tower.towers.aoe;

import com.raylib.Raylib;
import game.GameManager;
import game.enemy.Enemy;
import game.tower.EnergyConsumer;

/**
 * concept:
 * <p>
 * - a tower that shoots spheres
 * <p>
 * - aoe tower
 * <p>
 * - deals aoe damage by exploding on impact
 */
public class SphereCanon extends EnergyConsumer {

    public SphereCanon() {
        super(Type.SPHERE_CANON);
        range = 100;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, type.getColor());
        if (target != null)
            Raylib.DrawSphereWires(target.position.toRaylibVector3(), 10, 10, 10, type.getColor());
        drawEnergy();
        drawRange();
    }

    @Override
    public void gameTick() {
        tryFire();
        if (hasEnergy(50) && canFire()) {
            target = GameManager.instance.getClosestEnemy(position, range);

            if (target != null) {
                consumeEnergy(50);
                fire();
                final float range = 10;
                for (Enemy enemy : GameManager.instance.getEnemiesInRangeFromPosition(target.position, range)) {
                    enemy.damage(50);
                }
            }
        } else {
            target = null;
        }
    }
}