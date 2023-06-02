package spiel.gegner.gegner;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import spiel.gegner.Gegner;

public class GruenerFuerfel extends Gegner {
    public GruenerFuerfel() {
        super(15, 5, 8);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.GREEN);
    }

    @Override
    public void kill() {
        spawnEnemy(new BlauerWuerfel());
    }
}
