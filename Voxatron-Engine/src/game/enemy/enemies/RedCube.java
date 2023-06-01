package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class RedCube extends Enemy {

    public RedCube() {
        super(3, 5, 1);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 2, 2, 2, Jaylib.RED);
    }

    @Override
    public void kill() {

    }
}
