package game.tower.towers;

import com.raylib.Raylib;
import game.tower.Tower;

public class SphereCanon extends Tower {

    public SphereCanon() {
        super(Type.SPHERE_CANON);

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawSphere(position, 5, type.getColor());
    }
}