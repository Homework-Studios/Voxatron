package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class GreenCube extends Enemy {
    public GreenCube() {
        super(15, 5);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 1, 1, 1, Jaylib.GREEN);
    }

    @Override
    public void kill() {
        spawnEnemy(new BlueCube());
    }
}
