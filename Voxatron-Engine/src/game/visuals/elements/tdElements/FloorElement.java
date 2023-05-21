package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import engine.render.scene.Element;

import static com.raylib.Jaylib.DARKGRAY;
import static com.raylib.Jaylib.DrawPlane;

public class FloorElement extends Element {

    public FloorElement() {
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        DrawPlane(new Jaylib.Vector3(), new Jaylib.Vector2().x(300).y(300), DARKGRAY);
    }
}
