package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class BlueCube extends Enemy {

    public BlueCube() {
        super(5, 8);
    }


    @Override
    public void kill() {
        spawnEnemy(new RedCube());
    }


    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.BLUE);
    }
}
