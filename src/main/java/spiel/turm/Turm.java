package spiel.turm;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.Renderer;
import engine.render.scene.Element;
import spiel.SpielManager;
import spiel.gegner.Gegner;
import spiel.turm.tuerme.aoe.KugelKanone;
import spiel.turm.tuerme.aoe.PylonenKanone;
import spiel.turm.tuerme.einzelziel.LaserKanone;
import spiel.turm.tuerme.einzelziel.TronCanon;
import spiel.turm.tuerme.einzelziel.WuerfelKanone;
import spiel.turm.tuerme.energie.EnergieFabrik;
import spiel.visuellen.elemente.biElemente.inGame.TurmPanel;

public abstract class Turm extends Element {
    public static SpielManager spielManager;
    public final Type typ;
    public Gegner ziel;
    public int reichweite = 50;
    public Vector3 position = new Vector3();

    public int feuerrate = 60;
    public int hatVersuchtZuFeuern = 0;

    public Turm(Type typ) {
        this.typ = typ;
    }

    // Funktion zur Implementierung des Spiel-Schlags
    public abstract void spielSchlag();

    // Funktion zur Setzung der Position des Turms
    public void setPosition(Vector3 fallPosition) {
        position = fallPosition;
    }

    // Funktion zur Darstellung des Bereichs des Turms
    public void drawRange() {
        Raylib.DrawCircle3D(position.toRaylibVector3(), reichweite, new Raylib.Vector3().y(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position.toRaylibVector3(), reichweite, new Raylib.Vector3().x(1), 90, new Jaylib.Color(255, 255, 255, 50));
        Raylib.DrawCircle3D(position.toRaylibVector3(), reichweite, new Raylib.Vector3().z(1), 90, new Jaylib.Color(255, 255, 255, 50));
    }

    protected boolean IsClicked(Raylib.BoundingBox aabb) {

        if (!Jaylib.IsMouseButtonPressed(Jaylib.MOUSE_BUTTON_LEFT)) {
            return false;
        }

        Raylib.Ray ray = Raylib.GetMouseRay(Raylib.GetMousePosition(), Renderer.camera.getCamera());

        Raylib.RayCollision kollision = Raylib.GetRayCollisionBox(ray, aabb);

        return kollision.hit();
    }

    // Funktion zur Überprüfung, ob der Turm feuern kann
    public boolean kannFeuern() {
        return hatVersuchtZuFeuern >= feuerrate;
    }

    // Funktion zum Feuern des Turms
    public void feuern() {
        hatVersuchtZuFeuern = 0;
    }

    // Funktion zum Versuch des Feuerns des Turms
    public void versucheZuFeuern() {
        hatVersuchtZuFeuern++;
    }

    public enum Type {
        WUERFEL_KANONE("Kanone", Jaylib.RED, 400),
        LASER("Laser", Jaylib.RED, 1300),
        TRON_CANON("Tron Canon", Jaylib.RED, 500),

        PYLON("Pylon", Jaylib.PURPLE, 700),
        SPHERE_CANON("Sphere Canon", Jaylib.PURPLE, 600),

        ENERGIE_FABRIK("Fabrik", Jaylib.YELLOW, 100);

        public static final TurmPanel[] panels = new TurmPanel[]{
                new TurmPanel(WUERFEL_KANONE),
                new TurmPanel(LASER),
//                new TowerPanel(TRON_CANON),

//                new TowerPanel(SPHERE_CANON),
                new TurmPanel(PYLON),

                new TurmPanel(ENERGIE_FABRIK)
        };

        private final String name;
        private final Raylib.Color farbe;
        private final int kosten;

        Type(String name, Raylib.Color farbe, int kosten) {
            this.name = name;
            this.farbe = farbe;
            this.kosten = kosten;
        }

        // Funktion zur Rückgabe des Namens des Turms
        public String getName() {
            return name;
        }

        // Funktion zur Rückgabe der Kosten des Turms
        public int getKosten() {
            return kosten;
        }

        // Funktion zur Rückgabe der Farbe des Turms
        public Raylib.Color getFarbe() {
            return farbe;
        }

        // Funktion zur Erstellung des Turms
        public Turm erstelleTurm() {
            switch (this) {
                case WUERFEL_KANONE:
                    return new WuerfelKanone();
                case LASER:
                    return new LaserKanone();
                case TRON_CANON:
                    return new TronCanon();

                case SPHERE_CANON:
                    return new KugelKanone();
                case PYLON:
                    return new PylonenKanone();

                case ENERGIE_FABRIK:
                    return new EnergieFabrik();
                default:
                    return null;
            }
        }
    }
}
