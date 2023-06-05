package spiel.gegner.gegner;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import spiel.gegner.Gegner;

public class RoterWuerfel extends Gegner {

    public RoterWuerfel() {
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
