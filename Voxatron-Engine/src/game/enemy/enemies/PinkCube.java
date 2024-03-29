package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class PinkCube extends Enemy {
    public PinkCube() {
        super(25, 10, 30);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.PINK);
    }

    @Override
    public void kill() {
        spawnEnemy(new YellowCube());
        spawnEnemy(new YellowCube());
    }
}
