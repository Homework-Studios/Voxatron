package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class BlueCube extends Enemy {

    public BlueCube() {
        walkSpeed = 10;
    }

    @Override
    public int getEnergyGainOnKill() {
        return 2;
    }

    @Override
    public void update() {
        stepOnPath();
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.BLUE);
    }
}
