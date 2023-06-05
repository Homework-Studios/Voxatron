package spiel;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.math.Vector3;
import engine.render.scene.Element;
import engine.render.scene.InGameScene;
import engine.render.scene.SceneManager;
import spiel.gegner.Gegner;
import spiel.gegner.gegner.*;
import spiel.turm.EnergieVerbraucher;
import spiel.turm.Turm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public abstract class SpielManager extends Element {

    public static SpielManager instance;
    public static float spielGeschwindigkeit = 1;
    public final List<Turm> energieFabriken = new ArrayList<>();
    public final List<Gegner> gegner = new ArrayList<>();
    private final List<Gegner> gegnerListe = new ArrayList<>();
    private final List<Gegner> beschwoerbareGegner = new ArrayList<>();
    public WegManager wegManager;
    public int gegnerGetoetet = 0;
    public int beschwwoerGewicht;
    private int runde = 0;
    private boolean rundeBeendet = true;
    private boolean spielSollteEnden = false;
    private int energie = 500;
    private int leben = 100;
    private List<Turm> tuerme = new ArrayList<>();
    private int ueberbleibendesGewicht;

    public SpielManager() {
        instance = this;
        Turm.spielManager = this;
        wegManager = new WegManager();

        gegnerListe.addAll(List.of(new Gegner[]{new RoterWuerfel(), new BlauerWuerfel(), new GruenerFuerfel(), new GelberWuerfel(), new PinkerWuerfel(), new Kugel()}));
        beschwoerbareGegner.add(new RoterWuerfel());
    }

    public static SpielManager getInstance() {
        return instance;
    }

    public void plaziereTurm(Turm.Type typ) {
        InGameScene szene;
        if (!(SceneManager.instance.getActiveScene() instanceof InGameScene)) return;

        szene = ((InGameScene) SceneManager.instance.getActiveScene());
        boolean kaufen;
        if (!kannFallen(szene.getFallPosition())) return;
        if (typ == Turm.Type.ENERGIE_FABRIK) {
            kaufen = kaufen((int) (typ.getKosten() * energieFabriken.size() * energieFabriken.size() * Math.PI * 0.1f));
        } else {
            kaufen = kaufen(typ.getKosten());
        }
        if (kaufen) {
            Turm turm = typ.erstelleTurm();
            assert turm != null;
            turm.setPosition(szene.getFallPosition());
            szene.addElement3d(turm);

            if (turm.typ == Turm.Type.ENERGIE_FABRIK) {
                addEnergyFactory(turm);
            } else {
                addTower(turm);
            }
        }
    }

    private boolean kannFallen(Vector3 location) {
        return location.x != 0 && location.y != 0 && location.z != 0;
    }

    /**
     * Difficulty Scaling Function
     * <p>
     * <30:  0,83x
     * <p>
     * >30:  (20-x)^2*0,05+20
     */
    public int getSchwierigkeit() {
        return (runde < 30 ? (int) (runde * 0.83f) : (int) ((20 - runde) * (20 - runde) * 0.05f + 20)) + 1;
    }

    public void setSpielSollteEnden(boolean spielSollteEnden) {
        this.spielSollteEnden = spielSollteEnden;
    }

    public void setRundeBeendet(boolean rundeBeendet) {
        this.rundeBeendet = rundeBeendet;
    }

    public List<Turm> getTowers() {
        return tuerme;
    }

    public void setTowers(List<Turm> turms) {
        this.tuerme = turms;
    }

    public void addTower(Turm turm) {
        tuerme.add(turm);
    }

    public void addEnergyFactory(Turm turm) {
        energieFabriken.add(turm);
    }

    public int getRunde() {
        return runde;
    }

    public int getEnergie() {
        return energie;
    }

    public int getLeben() {
        return leben;
    }

    public void removeLeben(int lives) {
        this.leben -= lives;
    }

    public void naechsteRunde() {
        runde++;
        rundeBeendet = false;
        beschwoerbareGegner.clear();
        ueberbleibendesGewicht = getSchwierigkeit() + 3;
        beschwwoerGewicht = 0;
        for (Gegner gegner : gegnerListe) {
            if (gegner.gewicht <= getSchwierigkeit()) {
                beschwoerbareGegner.add(gegner);
            }
        }
//        Collections.reverse(spawnableEnemies);
    }

    public void addEnergy(int energy) {
        this.energie += energy;
    }

    public void entferneEnergie(int energy) {
        this.energie -= energy;
    }

    public boolean kaufen(int cost) {
        if (energie >= cost) {
            entferneEnergie(cost);
            return true;
        }
        return false;
    }

    public void start() {
        runde = 1;
        rundeBeendet = false;

        //lasse einen Spiel Schlag 20* pro Sekunde laufen
        Timer timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                if (spielSollteEnden || Raylib.WindowShouldClose()) timer.cancel();
                spielSchlag();
            }
        }, 0, (long) (50 / spielGeschwindigkeit));
    }

    @Override
    public void update() {
        uiUpdate();
        if (Raylib.IsKeyPressed(Jaylib.KEY_T)) {
            naechsteRunde();
        }
    }

    public void gegnerLogikAktualisierung() {
        beschwwoerGewicht += Math.sqrt(runde * 3);
        if (beschwwoerGewicht <= 50) return;
        Gegner gegnerZumBeschwoeren = null;
        for (Gegner gegner : beschwoerbareGegner) {
            if (gegner.gewicht <= ueberbleibendesGewicht) {
                if (gegnerZumBeschwoeren == null || gegner.gewicht > gegnerZumBeschwoeren.gewicht) {
                    gegnerZumBeschwoeren = gegner;
                }
            }
        }
        if (gegnerZumBeschwoeren != null) {
            addGegner(getNeuenGegner(gegnerZumBeschwoeren));
            beschwwoerGewicht -= gegnerZumBeschwoeren.gewicht * 20;
            ueberbleibendesGewicht -= gegnerZumBeschwoeren.gewicht;
        }
        if (ueberbleibendesGewicht == 0 && gegner.size() == 0) naechsteRunde();
    }

    public Gegner getNeuenGegner(Gegner gegner) {
        if (gegner.getClass().equals(RoterWuerfel.class)) {
            return new RoterWuerfel();
        } else if (gegner.getClass().equals(BlauerWuerfel.class)) {
            return new BlauerWuerfel();
        } else if (gegner.getClass().equals(GruenerFuerfel.class)) {
            return new GruenerFuerfel();
        } else if (gegner.getClass().equals(GelberWuerfel.class)) {
            return new GelberWuerfel();
        } else if (gegner.getClass().equals(PinkerWuerfel.class)) {
            return new PinkerWuerfel();
        } else if (gegner.getClass().equals(Kugel.class)) {
            return new Kugel();
        }
        return null;
    }

    public void addGegner(Gegner gegner) {
        parentScene.addElement3d(gegner);
        this.gegner.add(gegner);
    }

    public Gegner[] getGegnerInReichweiteEinerPosition(Vector3 position, float range) {
        List<Gegner> gegnerInReichweite = new ArrayList<>();
        for (Gegner gegner : getGegner()) {
            float distanz = position.distance(gegner.position);
            if (distanz < range) {
                gegnerInReichweite.add(gegner);
            }
        }
        return gegnerInReichweite.toArray(new Gegner[0]);
    }

    public Gegner getNaechstenGegen(Vector3 position, float range) {
        Gegner naechster = null;
        float naechsterDistanz = Float.MAX_VALUE;
        for (Gegner gegner : getGegner()) {
            float distanz = position.distance(gegner.position);
            if (distanz < naechsterDistanz) {
                naechster = gegner;
                naechsterDistanz = distanz;
            }
        }

        if (naechsterDistanz > range) return null;

        return naechster;
    }

    public Gegner getWeitestenGegnerInReichweite(Vector3 position, float range) {
        Gegner weitesten = null;
        float weitestenDistanzAufWeg = 0;
        for (Gegner gegner : getGegner()) {
            float distance = gegner.positionAufWeg;
            if (distance > weitestenDistanzAufWeg && gegner.position.distance(position) <= range) {
                weitesten = gegner;
                weitestenDistanzAufWeg = distance;
            }
        }
        return weitesten;
    }

    public void toeteGegner(Gegner gegner) {
        this.gegner.remove(gegner);
        parentScene.removeElement3d(gegner);
        gegnerGetoetet++;
    }

    public EnergieVerbraucher[] getNaechstenEnergieVerbraucher(Vector3 position, float range) {
        List<EnergieVerbraucher> naechsten = new ArrayList<>();

        // loop over all the energy consumers
        for (Turm turm : tuerme) {
            if (turm instanceof EnergieVerbraucher) {

                float distanz = position.distance(turm.position);

                if (distanz < range) {
                    naechsten.add((EnergieVerbraucher) turm);
                }
            }
        }

        for (int i = 0; i < naechsten.size(); i++) {
            if (i >= 5) naechsten.remove(i);
        }

        return naechsten.toArray(new EnergieVerbraucher[0]);
    }

    /**
     * runs every 50ms / gameSpeed (20 times per second * gameSpeed)
     */
    public void spielSchlag() {
        if (spielSollteEnden) {
            System.out.println("Game Over");
        }
        gegnerLogikAktualisierung();
        if (rundeBeendet) {
            naechsteRunde();
            System.out.println("Round " + runde + " started");
        }
        // hands over the Game Ticks
        tuerme.forEach(Turm::spielSchlag);
        energieFabriken.forEach(Turm::spielSchlag);
        gegner.forEach(Gegner::gameTick);
    }

    public abstract void uiUpdate();

    public List<Gegner> getGegner() {
        return new ArrayList<>(gegner);
    }

}
