package game.tower;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.Renderer;
import engine.render.scene.Element;
import game.GameManager;
import game.enemy.Enemy;
import game.tower.towers.aoe.PylonCanon;
import game.tower.towers.aoe.SphereCanon;
import game.tower.towers.energy.EnergyFactory;
import game.tower.towers.singletarget.CubeCanon;
import game.tower.towers.singletarget.PhaserCanon;
import game.tower.towers.singletarget.TronCanon;
import game.visuals.elements.uiElements.inGame.TowerPanel;

public abstract class Tower extends Element {
    public static GameManager gameManager;
    public final Type type;
    private final boolean moveMode = false;
    public Enemy target;
    public int range = 50;
    public Vector3 position = new Vector3();

    public int fireRate = 60;
    public int triedToFire = 0;

    //TODO: make towers upgradeable

    public Tower(Type type) {
        this.type = type;
    }

    public abstract void gameTick();

    public void setPosition(Vector3 dropPosition) {
        position = dropPosition;
    }

    public void drawRange() {
        Raylib.DrawCircle3D(position.toRaylibVector3(), range, new Raylib.Vector3().y(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position.toRaylibVector3(), range, new Raylib.Vector3().x(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position.toRaylibVector3(), range, new Raylib.Vector3().z(1), 90, new Jaylib.Color(255, 255, 255, 50));
    }

    protected boolean IsClicked(Raylib.BoundingBox aabb) {

        if (!Jaylib.IsMouseButtonPressed(Jaylib.MOUSE_BUTTON_LEFT)) {
            return false;
        }

        Raylib.Ray ray = Raylib.GetMouseRay(Raylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.RayCollision collision = Raylib.GetRayCollisionBox(ray, aabb);

        return collision.hit();
    }

    public boolean canFire() {
        return triedToFire >= fireRate;
    }

    public void fire() {
        triedToFire = 0;
    }

    public void tryFire() {
        triedToFire++;
    }

    // IMPORTANT NOTE: The lowest EnergyFactory has to cost less than the lowest Tower to not softlock the game!
    public enum Type {
        CUBE_CANON("Cube Canon", Jaylib.RED, 400),
        PHASER_CANON("Phaser", Jaylib.RED, 1300),
        TRON_CANON("Tron Canon", Jaylib.RED, 500),

        PYLON_CANON("Pylon", Jaylib.PURPLE, 2000),
        SPHERE_CANON("Sphere Canon", Jaylib.PURPLE, 600),

        ENERGY_FACTORY("Energy Factory", Jaylib.YELLOW, 100);

        public static final TowerPanel[] panels = new TowerPanel[]{
                new TowerPanel(CUBE_CANON),
                new TowerPanel(PHASER_CANON),
                new TowerPanel(TRON_CANON),

                new TowerPanel(SPHERE_CANON),
                new TowerPanel(PYLON_CANON),

                new TowerPanel(ENERGY_FACTORY)
        };

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
                case PHASER_CANON:
                    return new PhaserCanon();
                case TRON_CANON:
                    return new TronCanon();

                case SPHERE_CANON:
                    return new SphereCanon();
                case PYLON_CANON:
                    return new PylonCanon();

                case ENERGY_FACTORY:
                    return new EnergyFactory();
                default:
                    return null;
            }
        }
    }
}
