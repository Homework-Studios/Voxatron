package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

/**
 * concept:
 * <p>
 * - a tower that releases energy
 * <p>
 * - energy is used to build towers
 * <p>
 * - releasing energy costs energy this tower increases damage dealt to enemies when releasing
 */
public class EnergyReleaser extends Tower {
    public EnergyReleaser() {
        super(Type.ENERGY_RELEASER);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
    }

    @Override
    public void gameTick() {

    }
}
