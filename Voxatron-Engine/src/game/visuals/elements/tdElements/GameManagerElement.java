package game.visuals.elements.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import engine.util.UiUtils;
import game.GameManager;

public class GameManagerElement extends GameManager {

    @Override
    public void uiUpdate() {

    }

    @Override
    public void render() {
        Raylib.DrawText("energy: " + getEnergy() + "\nlives: " + getLives() + "\nround: " + getRound(), (int) UiUtils.getWidthPercent(5), (int) UiUtils.getHeightPercent(5), (int) UiUtils.getHeightPercent(3), Jaylib.WHITE);
    }
}
