package game.tower.towers;

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
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
    }
}
