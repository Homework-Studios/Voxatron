package spiel.gegner.gegner;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import spiel.gegner.Gegner;

public class GelberWuerfel extends Gegner {
    public GelberWuerfel() {
        super(10, 15, 15);
    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 3, 3, 3, Jaylib.YELLOW);
    }

    @Override
    public void kill() {
        beschwoereGegner(new GruenerFuerfel());
    }
}
