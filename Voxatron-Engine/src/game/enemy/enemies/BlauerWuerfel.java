package game.enemy.enemies;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.enemy.Gegner;

public class BlauerWuerfel extends Gegner {

    public BlauerWuerfel() {
        super(5, 8, 3);
    }


    @Override
    public void kill() {
        spawnEnemy(new RoterWuerfel());
    }


    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 2, 2, 2, Jaylib.BLUE);
    }
}
