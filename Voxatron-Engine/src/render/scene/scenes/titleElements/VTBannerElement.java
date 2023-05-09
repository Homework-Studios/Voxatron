package render.scene.scenes.titleElements;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;
import render.scene.Element;
import util.UiUtil;

import static com.raylib.Jaylib.RED;

public class VTBannerElement extends Element {

    Texture banner;

    public VTBannerElement() {
           Image ibanner = LoadImage("Voxatron/Assets/MainMenu/VTBanner/VTBanner.png");
           ImageResize(ibanner, (int) UiUtil.getWidthPercent(50), (int) UiUtil.getHeightPercent(50));
           banner = LoadTextureFromImage(ibanner);
    }


    @Override
    public void update() {

    }

    @Override
    public void render() {
        DrawTexture(banner, (int) UiUtil.getWidthPercent(50) - banner.width() / 2, (int) UiUtil.getHeightPercent(30) - banner.height() / 2, WHITE);
    }
}
