package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Enemy;

public class Sphere extends Enemy {
    //TODO: make immune to none tank damage
    public Sphere() {
        super(50, 6);
    }

    @Override
    public void render() {
        Raylib.DrawSphere(position.toRaylibVector3(), 3, Jaylib.LIGHTGRAY);
    }

    @Override
    public void kill() {
        for (int i = 0; i < 3; i++) {
            spawnEnemy(new PinkCube());
        }
    }
}
