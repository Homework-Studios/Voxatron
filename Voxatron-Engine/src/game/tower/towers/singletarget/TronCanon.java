package game.tower.towers.singletarget;

import com.raylib.Raylib;
import game.tower.Tower;

/**
 * concept:
 * <p>
 * no idea
 * <p>
 * maybe a tower that switches between different modes
 */
public class TronCanon extends Tower {
    public TronCanon() {
        super(Type.TRON_CANON);
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
