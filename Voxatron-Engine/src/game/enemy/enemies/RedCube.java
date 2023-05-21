package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class RedCube extends Enemy {

    public RedCube() {
        walkSpeed = 5;
    }

    @Override
    public void update() {
        stepOnPath();
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.RED);
    }

    @Override
    public int getEnergyGainOnKill() {
        return 1;
    }
}
