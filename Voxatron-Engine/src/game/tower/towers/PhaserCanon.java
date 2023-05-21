package game.tower.towers;

import com.raylib.Raylib;
import game.GameManager;
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

    public PhaserCanon() {
        super(Type.PHASER_CANON);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
        drawRange();
        drawEnergy();
    }

    @Override
    public void gameTick() {
        if (hasEnergy(consume)) {
            target = GameManager.instance.getClosetEnemy(position, range);

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
            consume = 1;
            fired = 0;
        }
    }
}
