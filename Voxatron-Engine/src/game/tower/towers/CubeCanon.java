package game.tower.towers;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.tower.Tower;

public class CubeCanon extends Tower {

    public CubeCanon() {
        super("Cube Canon", Jaylib.RED, 10, 10, 10, 10, new Raylib.Vector3());
    }
}
