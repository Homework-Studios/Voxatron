package render.scene.scenes.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.scene.Element;

public class CubeElement extends Element {

    public Raylib.Vector3 position;
    public Raylib.Vector3 size;

    public CubeElement(Raylib.Vector3 position, Raylib.Vector3 size) {
        this.position = position;
        this.size = size;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position, size.x(), size.y(), size.z(), Jaylib.RED);
    }
}
