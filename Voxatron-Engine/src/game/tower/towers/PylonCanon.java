package game.tower.towers;

import com.raylib.Raylib;
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
    public PylonCanon(Type type) {
        super(Type.PYLON_CANON);
    }

    @Override
    public void gameTick() {
        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, type.getColor());
    }

    //TODO: implement
    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
