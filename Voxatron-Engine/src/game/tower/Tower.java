package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import game.enemy.Enemy;
import game.tower.towers.CubeCanon;
import game.tower.towers.SphereCanon;
import render.Renderer;
import render.scene.Element;

public abstract class Tower extends Element {
    public static GameManager gameManager;
    public final Type type;
    protected Raylib.Vector3 position = new Raylib.Vector3();
    private boolean moveMode = false;

    public Enemy target;

    public int range = 50;

    public Tower(Type type) {
        this.type = type;
    }

    public void setPosition(Raylib.Vector3 dropPosition) {
        position = dropPosition;
    }

    public enum Type {
        CUBE_CANON("Cube Canon", Jaylib.RED, 100), SPHERE_CANON("Sphere Canon", Jaylib.BLUE, 600);

        private final String name;
        private final Raylib.Color color;
        private final int cost;

        Type(String name, Raylib.Color color, int cost) {
            this.name = name;
            this.color = color;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public int getCost() {
            return cost;
        }

        public Raylib.Color getColor() {
            return color;
        }

        public Tower createTower() {
            switch (this) {
                case CUBE_CANON:
                    return new CubeCanon();
                case SPHERE_CANON:
                    return new SphereCanon();
                default:
                    return null;
            }
        }
    }

    public void drawRange() {
        Raylib.DrawCircle3D(position, range, new Raylib.Vector3().y(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position, range, new Raylib.Vector3().x(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position, range, new Raylib.Vector3().z(1), 90, new Jaylib.Color(255, 255, 255, 50));
    }

    protected boolean IsClicked(Raylib.BoundingBox aabb) {

        if(!Jaylib.IsMouseButtonPressed(Jaylib.MOUSE_BUTTON_LEFT)) {
            return false;
        }

        Raylib.Ray ray = Raylib.GetMouseRay(Raylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.RayCollision collision = Raylib.GetRayCollisionBox(ray, aabb);

        return collision.hit();
    }
}
