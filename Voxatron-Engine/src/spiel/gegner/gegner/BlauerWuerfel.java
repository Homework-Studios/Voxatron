package spiel.gegner.gegner;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import spiel.gegner.Gegner;

public class BlauerWuerfel extends Gegner {

    public BlauerWuerfel() {
        super(5, 8, 3);
    }


    @Override
    public void kill() {
        beschwoereGegner(new RoterWuerfel());
    }


    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 2, 2, 2, Jaylib.BLUE);
    }
}
