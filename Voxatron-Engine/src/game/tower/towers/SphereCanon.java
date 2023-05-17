package game.tower.towers;

import com.raylib.Jaylib;
import game.tower.Tower;

public abstract class SphereCanon extends Tower {

    public SphereCanon() {
        super("Sphere Canon", Jaylib.BLUE, 10, 10, 10, 10);
    }

}