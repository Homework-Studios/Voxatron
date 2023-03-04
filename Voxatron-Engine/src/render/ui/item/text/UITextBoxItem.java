package render.ui.item.text;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UiTextRenderTask;
import render.ui.box.BoxFiler;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class UITextBoxItem extends UIItem {

    public Vector2 a;
    public Vector2 b;
    public BoxFiler filer;
    public String text;

    public UITextBoxItem(Vector2 a, Vector2 b, String text, BoxFiler filer) {
        this.a = a;
        this.b = b;
        this.text = text;
        this.filer = filer;

        addRenderable(new UiTextRenderTask(text, new Vector2(), 1, 0, WHITE));
    }

    @Override
    public void update() {
        // This Libary Method is EXTREMLY BAD and doesnt work as it is supposed to
        Raylib.Vector2 textDimensions = MeasureTextEx(GetFontDefault(), text, ((UiTextRenderTask)renderables.get(0)).scale * 20, 2);
        Vector2 dimensions = new Vector2((float) (textDimensions.x() * 0.48), (float)(textDimensions.y()));

        ((UiTextRenderTask)renderables.get(0)).position = BoxLayoutUtil.applyFilter(a, b, filer).subtract(dimensions);
        ((UiTextRenderTask)renderables.get(0)).text = text;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.a = this.a.add(position);
        this.b = this.b.add(position);
    }
}
