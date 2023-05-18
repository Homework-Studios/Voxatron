package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import game.tower.towers.CubeCanon;
import game.tower.towers.SphereCanon;

public abstract class Tower {
    public static GameManager gameManager;
    private final Type type;
    private final int cost;
    protected Raylib.Vector3 location;


    public Tower(Type type, int cost) {
        this.cost = cost;
        this.type = type;
    }

    public enum Type {
        CUBE_CANON("Cube Canon", Jaylib.RED), SPHERE_CANON("Sphere Canon", Jaylib.BLUE);

        private final String name;
        private final Raylib.Color color;

        Type(String name, Raylib.Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
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
