package render.scene.scenes.tdElements;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.scene.Element;

public class FloorElement extends Element {

    public FloorElement() {}

    @Override
    public void update() {

    }

    @Override
    public void render() {
        DrawPlane(new Jaylib.Vector3(), new Jaylib.Vector2().x(300).y(300), DARKGRAY);
    }
}
