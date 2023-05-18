package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

public class CubeCanon extends Tower {

    public CubeCanon() {
        super(Type.CUBE_CANON);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(location, 5, 5, 5, type.getColor());
    }
}
