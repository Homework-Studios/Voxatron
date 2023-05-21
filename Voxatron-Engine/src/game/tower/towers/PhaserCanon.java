package game.tower.towers;

import com.raylib.Raylib;
import game.GameManager;
import game.enemy.Enemy;
import game.tower.EnergyConsumer;

/**
 * concept:
 * <p>
 * - a tower that shoots phaser beams
 * <p>
 * - single target tower
 * <p>
 * - deals damage over time
 * <p>
 * - damage is increased the longer the beam is on the target
 * <p>
 * - make as gimmicky as possible
 */
public class PhaserCanon extends EnergyConsumer {
    int consume = 1;
    int fired = 0;
    Enemy lastTarget = null;

    public PhaserCanon() {
        super(Type.PHASER_CANON);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
        if (target != null)
            Raylib.DrawCylinderEx(position, target.position, 0.1f * consume, 0.1f * consume, 12, type.getColor());
        drawRange();
        drawEnergy();
    }

    @Override
    public void gameTick() {
        if (hasEnergy(consume)) {
            if (target == null || !target.isAlive) {
                target = GameManager.instance.getClosestEnemy(position, range);
                if (target != null) {
                    consume = 1;
                    fired = 0;
                }
            }
            if (target != null) {
                consumeEnergy(consume * consume);
                fire();
                target.damage(consume * consume);
                if (fired >= 20) {
                    consume++;
                    fired = 0;
                }
                fired++;
            }
        } else {
            target = null;
        }
    }
}
