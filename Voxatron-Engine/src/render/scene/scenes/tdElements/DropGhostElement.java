package render.scene.scenes.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import render.Renderer;
import render.scene.Element;

public class DropGhostElement extends Element {

    public Raylib.Vector3 position;

    public DropGhostElement() {
        position = new Raylib.Vector3();
    }

    @Override
    public void update() {
        Raylib.Ray ray = Jaylib.GetMouseRay(Jaylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.Rectangle floor = new Raylib.Rectangle().x(-150).y(-150).width(300).height(300);
        Raylib.BoundingBox box = new Jaylib.BoundingBox().min(new Raylib.Vector3().x(floor.x()).y(0).z(floor.y())).max(new Raylib.Vector3().x(floor.x() + floor.width()).y(0).z(floor.y() + floor.height()));

        Raylib.RayCollision collision = Jaylib.GetRayCollisionBox(ray, box);

        if (collision.hit()) {
            position = collision.point();

            // add y+1 to position
            position.y(position.y() + 2);
        }
    }

    @Override
    public void render() {
        // Draw a sphere at the position
        Raylib.DrawSphere(position, 2, Jaylib.GREEN);

        // Draw a 3d line from 000 to the position
        Raylib.DrawLine3D(new Raylib.Vector3().y(1), position, Jaylib.GREEN);
    }
}
