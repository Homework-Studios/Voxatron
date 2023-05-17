package render.scene.scenes;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.tower.towers.CubeCanon;
import game.tower.towers.SphereCanon;
import math.Vector2;
import render.Renderer;
import render.scene.Element;
import render.scene.Scene;
import render.scene.SceneManager;
import render.scene.scenes.tdElements.CubeElement;
import render.scene.scenes.tdElements.DropGhostElement;
import render.scene.scenes.tdElements.FloorElement;
import render.scene.scenes.uiElements.ButtonElement;
import render.scene.scenes.uiElements.TextElement;
import render.scene.scenes.uiElements.levelButtons.TowerPanel;
import render.scene.scenes.uiElements.levelButtons.TowerSelector;

import static util.ColorPalette.ColorPalettes.STANDARD_BUTTON;

public class IngameDevScene extends Scene {

    ElementBatch elementBatch;

    @Override
    public void init() {
        addElement3d(new DropGhostElement() {
            @Override
            public void run() {
                this.position = getDropPosition();
            }
        });
        addElement3d(new FloorElement());

        TowerPanel[] tpl = new TowerPanel[]{
                new TowerPanel(new CubeCanon()) {
                    @Override
                    public void run() {
                        if(canDrop()){
                            addElement3d(new CubeElement(getDropPosition(), new Jaylib.Vector3().x(5).y(5).z(5)));
                        }
                    }
                },
                new TowerPanel(new CubeCanon()) {
                    @Override
                    public void run() {
                        System.out.println("CubeCanon");
                    }
                },
                new TowerPanel(new CubeCanon()) {
                    @Override
                    public void run() {
                        System.out.println("CubeCanon");
                    }
                },
                new TowerPanel(new SphereCanon()) {
                    @Override
                    public void run() {
                        System.out.println("SphereCanon");
                    }
                }
        };

        elementBatch = new ElementBatch(new Element[]{

                new ButtonElement(
                        Vector2.byScreenPercent(94, 5),
                        Vector2.byScreenPercent(10, 7),
                        "Close",
                        40f,
                        STANDARD_BUTTON
                ) {
                    @Override
                    public void run() {
                        SceneManager.instance.setActiveScene(LevelSelectorScene.class);
                    }
                },
                new TowerSelector(Vector2.byScreenPercent(50, 105), Vector2.byScreenPercent(85, 20), tpl) {
                    @Override
                    public void run() {
                        System.out.println("TowerSelector");
                    }
                }
        }, true);

        addElement(elementBatch);
    }

    public Raylib.Vector3 getDropPosition() {
        Raylib.Ray ray = Jaylib.GetMouseRay(Jaylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.Rectangle floor = new Raylib.Rectangle().x(-150).y(-150).width(300).height(300);
        Raylib.BoundingBox box = new Jaylib.BoundingBox().min(new Raylib.Vector3().x(floor.x()).y(0).z(floor.y())).max(new Raylib.Vector3().x(floor.x() + floor.width()).y(0).z(floor.y() + floor.height()));

        Raylib.RayCollision collision = Jaylib.GetRayCollisionBox(ray, box);

        if (collision.hit()) {
            // add y+1 to position
            collision.point(new Raylib.Vector3().x(collision.point().x()).y(collision.point().y() + 1).z(collision.point().z()));

            return collision.point();
        }

        return new Raylib.Vector3();
    }

    public boolean canDrop() {
        return getDropPosition().x() != 0 && getDropPosition().y() != 0 && getDropPosition().z() != 0;
    }

    @Override
    public void update() {
        for (Element element : getIterableElements3d()) {
            element.update();
        }

        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {

        // Enabled the renderer to render 3d elements, without this, the 3d elements will not be rendered.
        // --------------------------------------------
        Renderer.instance.toggleMode3d();

        for (Element element : elements3d) {
            element.render();
        }

        Renderer.instance.toggleMode3d();
        // --------------------------------------------

        for (Element element : elements) {
            element.render();
        }
    }
}
