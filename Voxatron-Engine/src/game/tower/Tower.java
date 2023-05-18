package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import game.tower.towers.CubeCanon;
import game.tower.towers.SphereCanon;
import render.scene.Element;

public abstract class Tower extends Element {
    public static GameManager gameManager;
    public final Type type;
    protected Raylib.Vector3 location = new Raylib.Vector3();


    public Tower(Type type) {
        this.type = type;
    }

    public void setLocation(Raylib.Vector3 dropPosition) {
        location = dropPosition;
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
}
