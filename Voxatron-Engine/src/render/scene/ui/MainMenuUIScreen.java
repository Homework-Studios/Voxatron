package render.scene.ui;

import com.raylib.Raylib;
import debug.DebugDraw;
import math.Vector2;
import render.scene.SceneManager;
import render.scene.SceneType;
import render.task.ui.UIRoundBoxRenderTask;
import render.ui.UIScreen;
import render.ui.box.BoxFilter;
import render.ui.item.image.UIImageItem;
import render.ui.item.input.UIButtonItem;
import render.ui.item.input.UISliderItem;
import render.ui.item.input.UISwitchItem;
import render.ui.item.view.UIListViewItem;
import render.ui.item.view.UIListViewItemItem;
import window.Window;

import static com.raylib.Jaylib.*;

public class MainMenuUIScreen extends UIScreen {

    public MainMenuUIScreen() {
        super(new Vector2(), new Vector2(GetScreenWidth(), GetScreenHeight()));

        String path = System.getProperty("user.dir") + "\\Voxatron-Engine\\src\\resources\\";

        Raylib.Texture texture = LoadTexture(path + "VTBanner.png");
        texture = texture.width(1300);
        texture = texture.height(600);

        // load the Play.png image
        Raylib.Texture play = LoadTexture(path + "Play.png");

        // load the Credits.png image
        Raylib.Texture credits = LoadTexture(path + "Credits.png");

        // load the Leave.png image
        Raylib.Texture leave = LoadTexture(path + "Leave.png");

        // load the Music.png image
        Raylib.Texture music = LoadTexture(path + "Music.png");

        // load the Sounds.png image
        Raylib.Texture sound = LoadTexture(path + "Sound.png");
        Raylib.Texture soundOff = LoadTexture(path + "SoundOff.png");

        // load the Settings.png image
        Raylib.Texture settings = LoadTexture(path + "Settings.png");

        addItem(new UIImageItem(texture, new Vector2(0, -300), BoxFilter.CENTER));

        addItem(new UIButtonItem(play, new Vector2(0, 20), new Vector2(950, 100), BoxFilter.CENTER, 900, 95, () -> {
            SceneManager.instance.setCurrentScene(SceneType.LEVEL_SELECTOR);
        }));

        addItem(new UIButtonItem(credits, new Vector2(0, 140), new Vector2(950, 100), BoxFilter.CENTER, 900, 95, () -> {
            DebugDraw.instance.print("Clicked Credits");
        }));

        addItem(new UIButtonItem(leave, new Vector2(0, 260), new Vector2(950, 100), BoxFilter.CENTER, 900, 95, () -> {
            Window.instance.stop();
        }));

        addItem(new UISwitchItem(music, new Vector2(-425, 380), new Vector2(100, 100), BoxFilter.CENTER, 95, 95, true) {
            @Override
            public void run() {

            }
        });

        addItem(new UISwitchItem(sound, new Vector2(-305, 380), new Vector2(100, 100), BoxFilter.CENTER, 95, 95, true) {
            @Override
            public void run() {
                if (toggled) this.texture = sound;
                else this.texture = soundOff;
            }
        });

        addItem(new UISliderItem(new Vector2(60, 380), new Vector2(590, 100), BoxFilter.CENTER, 0, () -> {
            DebugDraw.instance.print("slider");
        }));

        UIListViewItem configList = new UIListViewItem(new Vector2(0, 0), new Vector2(1000, 1000), BoxFilter.CENTER);
        configList.hidden = true;

        UIListViewItemItem item = new UIListViewItemItem(new Vector2(20, 20), new Vector2(1200, 100)) {
            @Override
            public void init() {
                UIRoundBoxRenderTask task = new UIRoundBoxRenderTask(new Vector2(0, 0), new Vector2(600, 100), 0.2f, 10, WHITE);
                addTask(task);
                task.lines = true;
            }
        };

        for (int i = 0; i < 15; i++) {
            configList.addListItem(item);
        }

        addItem(configList);
        addItem(new UIButtonItem(settings, new Vector2(425, 380), new Vector2(100, 100), BoxFilter.CENTER, 95, 95, () -> {
            configList.hidden = !configList.hidden;
        }));
    }
}
