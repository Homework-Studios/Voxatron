package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class TestTank extends Enemy {
    public TestTank() {
        super(1010000000, 1, 250);
    }

    @Override
    public void kill() {


    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 10, 10, 10, Jaylib.GREEN);
    }
}
