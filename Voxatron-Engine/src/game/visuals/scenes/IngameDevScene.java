package game.visuals.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector2;
import engine.math.Vector3;
import engine.render.Renderer;
import engine.render.scene.Element;
import engine.render.scene.ElementBatch;
import engine.render.scene.InGameScene;
import game.visuals.elements.tdElements.*;
import game.visuals.elements.uiElements.inGame.TowerSelector;

import java.util.ArrayList;
import java.util.List;

public class IngameDevScene extends InGameScene {

    ElementBatch elementBatch;

    @Override
    public void init() {

        clearColor = new Jaylib.Color(255, 240, 161, 255);

        addElement3d(new DropGhostElement() {
            @Override
            public void run() {
                position = getDropPosition();
            }
        });
        addElement3d(new FloorElement(1));
        addElement3d(new PathDrawElement());
        addElement3d(new LightElement());
        addElement(new GameManagerElement());


        elementBatch = new ElementBatch(new Element[]{

                new TowerSelector(Vector2.byScreenPercent(50, 105), Vector2.byScreenPercent(85, 20)) {
                    @Override
                    public void run() {
                        System.out.println("TowerSelector");
                    }
                }
        }, true);

        addElement(elementBatch);
    }

    public Vector3 getDropPosition() {
        Raylib.Ray ray = Jaylib.GetMouseRay(Jaylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.Rectangle floor = new Raylib.Rectangle().x(-150).y(-150).width(300).height(300);
        Raylib.BoundingBox box = new Jaylib.BoundingBox().min(new Raylib.Vector3().x(floor.x()).y(0).z(floor.y())).max(new Raylib.Vector3().x(floor.x() + floor.width()).y(0).z(floor.y() + floor.height()));

        Raylib.RayCollision collision = Jaylib.GetRayCollisionBox(ray, box);

        if (collision.hit()) {
            // add y+1 to position
            collision.point(new Raylib.Vector3().x(collision.point().x()).y(collision.point().y() + 1).z(collision.point().z()));

            return new Vector3(collision.point());
        }

        return new Vector3();
    }

    public boolean canDrop() {
        return getDropPosition().x != 0 && getDropPosition().y != 0 && getDropPosition().z != 0;
    }

    @Override
    public void update() {

        for (Element element : getIterableElements3d()) {
            if (element == null) continue;
            element.update();
        }

        for (Element element : getIterableElements()) {
            if (element == null) continue;
            element.update();
        }
    }

    @Override
    public void render() {

        // Enabled the renderer to render 3d elements, without this, the 3d elements will not be rendered.
        // --------------------------------------------
        Renderer.instance.toggleMode3d();
        List<Element> elements3dCopy = new ArrayList<>(elements3d);
        for (Element element : elements3dCopy) {
            element.render();
        }


        Renderer.instance.toggleMode3d();
        // --------------------------------------------

        for (Element element : elements) {
            element.render();
        }
    }
}
