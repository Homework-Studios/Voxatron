package spiel.gegner.gegner;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import spiel.gegner.Gegner;

public class Kugel extends Gegner {
    //TODO: make immune to none tank damage
    public Kugel() {
        super(50, 6, 150);
    }

    @Override
    public void render() {
        Raylib.DrawSphere(position.toRaylibVector3(), 3, Jaylib.LIGHTGRAY);
    }

    @Override
    public void kill() {
        for (int i = 0; i < 3; i++) {
            spawnEnemy(new PinkerWuerfel());
        }
    }
}
