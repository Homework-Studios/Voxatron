package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.util.UiUtils;
import game.GameManager;

public class SpielManagerElement extends GameManager {

    @Override
    public void uiUpdate() {

    }

    @Override
    public void render() {
        Raylib.DrawText("Energie: " + getEnergie() + "\nLeben: " + getLeben() + "\nRunde: " + getRunde() + " (" + getSchwierigkeit() + ")" + "\n" + beschwwoerGewicht, (int) UiUtils.getWidthPercent(5), (int) UiUtils.getHeightPercent(5), (int) UiUtils.getHeightPercent(3), new Jaylib.Color(46, 46, 46, 255));
    }
}
