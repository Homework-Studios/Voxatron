package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

/**
 * concept:
 * <p>
 * - a tower that collects energy
 * <p>
 * - energy is used to build towers
 * <p>
 * - energy is collected like elixir in cr maybe?
 * <p>
 * - price of this tower increases as you buy more?
 */
public class EnergyCollector extends Tower {
    public EnergyCollector() {
        super(Type.ENERGY_COLLECTOR);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 5, 5, 5, type.getColor());
    }
}
