package render.scene.scenes.titleElements;

import com.raylib.Raylib;
import render.scene.Element;
import util.UiUtil;

import static com.raylib.Jaylib.RED;

public class VTBannerElement extends Element {


    @Override
    public void update() {

    }

    @Override
    public void render() {
        Raylib.DrawLine((int)UiUtil.getWidthPercent(50), 0, (int)UiUtil.getWidthPercent(50), (int)UiUtil.getHeightPercent(100), RED);
        // counterpart
        Raylib.DrawLine(0, (int)UiUtil.getHeightPercent(50), (int)UiUtil.getWidthPercent(100), (int)UiUtil.getHeightPercent(50), RED);
    }
}
