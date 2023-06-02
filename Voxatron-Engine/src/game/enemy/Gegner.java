package game.enemy;

import engine.math.Vector3;
import engine.render.scene.Element;
import game.GameManager;

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
        positionAufWeg += (laufgeschwindigkeit * 0.05f * GameManager.spielGeschwindigkeit);

        position = GameManager.instance.wegManager.getLaufen(positionAufWeg);
        if (positionAufWeg >= GameManager.instance.wegManager.totaleDistanz) {
            GameManager.instance.removeLeben(leben);
            GameManager.instance.toeteGegner(this);
        }
    }

    public void schaden(int damage) {
        leben -= damage;
        if (leben <= 0) {
            istAmLeben = false;
            kill();
            GameManager.instance.addEnergy(maximalenLeben);
            GameManager.instance.toeteGegner(this);
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
        GameManager.instance.addGegner(gegner);
    }
}
