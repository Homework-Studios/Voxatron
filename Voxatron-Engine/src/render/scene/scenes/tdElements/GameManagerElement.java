package render.scene.scenes.tdElements;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import game.GameManager;
import util.UiUtil;

public class GameManagerElement extends GameManager {

    @Override
    public void uiUpdate() {

    }

    @Override
    public void render() {
        Raylib.DrawText("energy: " + getEnergy() + "\nlives: " + getLives() + "\nround: " + getRound(), (int) UiUtil.getWidthPercent(5), (int) UiUtil.getHeightPercent(5), (int) UiUtil.getHeightPercent(3), Jaylib.WHITE);
    }
}
