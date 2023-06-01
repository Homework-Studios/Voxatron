package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class YellowCube extends Enemy {
    public YellowCube() {
        super(10, 15, 15);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.YELLOW);
    }

    @Override
    public void kill() {
        spawnEnemy(new GreenCube());
    }
}
