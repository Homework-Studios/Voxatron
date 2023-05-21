package render.scene.scenes.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import math.Vector3;
import render.Renderer;
import render.scene.Element;

public abstract class DropGhostElement extends Element implements Runnable {

    public Vector3 position;

    public DropGhostElement() {
        position = new Vector3();
    }

    @Override
    public void update() {
        run();
    }

    @Override
    public void render() {
        // Draw a sphere at the position
        Raylib.DrawSphere(position.toRaylibVector3(), 2, Jaylib.GREEN);

        // Draw a 3d line from 000 to the position
        Raylib.DrawLine3D(new Raylib.Vector3().y(1), position.toRaylibVector3(), Jaylib.GREEN);
    }
}
