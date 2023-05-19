package game.tower.towers;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.tower.Tower;
import util.VectorUtils;

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
    int gainCD = 0;
    int maxGainCD = 200;

    public EnergyCollector() {
        super(Type.ENERGY_COLLECTOR);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.Vector3 loc = VectorUtils.copy(position);
        loc.y(loc.y() + 5);
        Raylib.DrawCubeWires(loc, 5, 10, 5, Jaylib.BLACK);
        Raylib.DrawCube(loc.y(loc.y() + 5 * (float) gainCD / maxGainCD - 5), 5, (float) gainCD / maxGainCD * 10, 5, type.getColor());
    }

    //TODO: fix
    @Override
    public void gameTick() {
        gainCD++;
        if (gainCD >= maxGainCD) {
            gainCD = 0;
            gameManager.addEnergy(25);
        }
    }
}
