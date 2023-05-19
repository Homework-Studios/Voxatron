package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

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
public class PhaserCanon extends Tower {
    public PhaserCanon() {
        super(Type.PHASER_CANON);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
    }
}
