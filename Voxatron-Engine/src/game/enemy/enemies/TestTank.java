package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class TestTank extends Enemy {
    public TestTank() {
        walkSpeed = 0.5f;
        health = 1000000000;
    }

    @Override
    public int getEnergyGainOnKill() {
        return 99999999;
    }

    @Override
    public void update() {
        stepOnPath();
    }

    @Override
    public void render() {
        Raylib.DrawCube(position, 10, 10, 10, Jaylib.GREEN);
    }
}
