package spiel.gegner;

import engine.math.Vector3;
import engine.render.scene.Element;
import spiel.SpielManager;

public abstract class Gegner extends Element {

    public final int gewicht;
    public int leben;
    public int maximalenLeben;

    public float laufgeschwindigkeit;
    public float positionAufWeg = 0;

    public Vector3 position = new Vector3();
    public boolean istAmLeben = true;

    public Gegner(int leben, float laufgeschwindigkeit, int gewicht) {
        maximalenLeben = leben;
        this.leben = leben;
        this.laufgeschwindigkeit = laufgeschwindigkeit;
        this.gewicht = gewicht;

    }

    public void laufeAufWeg() {
        positionAufWeg += (laufgeschwindigkeit * 0.05f * SpielManager.spielGeschwindigkeit);

        position = SpielManager.instance.wegManager.getLaufen(positionAufWeg);
        if (positionAufWeg >= SpielManager.instance.wegManager.totaleDistanz) {
            SpielManager.instance.removeLeben(leben);
            SpielManager.instance.toeteGegner(this);
        }
    }

    public void schaden(int damage) {
        leben -= damage;
        if (leben <= 0) {
            istAmLeben = false;
            kill();
            SpielManager.instance.addEnergy(maximalenLeben);
            SpielManager.instance.toeteGegner(this);
        }
    }

    public void gameTick() {

    }

    public abstract void kill();

    @Override
    public void update() {
        laufeAufWeg();
    }

    public void spawnEnemy(Gegner gegner) {
        gegner.positionAufWeg = positionAufWeg;
        SpielManager.instance.addGegner(gegner);
    }
}
