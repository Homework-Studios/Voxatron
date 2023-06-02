package spiel.turm.tuerme.einzelziel;

import com.raylib.Raylib;
import spiel.turm.Turm;

/**
 * concept:
 * <p>
 * no idea
 * <p>
 * maybe a tower that switches between different modes
 */
public class TronCanon extends Turm {
    public TronCanon() {
        super(Type.TRON_CANON);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawCube(position.toRaylibVector3(), 5, 5, 5, typ.getFarbe());
    }

    @Override
    public void spielSchlag() {

    }
}
