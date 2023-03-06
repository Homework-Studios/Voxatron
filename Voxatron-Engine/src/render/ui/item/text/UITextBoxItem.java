package render.ui.item.text;

import com.raylib.Raylib;
import math.Vector2;
import render.task.ui.UITextRenderTask;
import render.ui.box.BoxFilter;
import render.ui.item.UIItem;
import util.BoxLayoutUtil;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.GetFontDefault;
import static com.raylib.Raylib.MeasureTextEx;

public class UITextBoxItem extends UIItem {

    public Vector2 a;
    public Vector2 b;
    public BoxFilter filter;
    public String text;

    public UITextBoxItem(Vector2 a, Vector2 b, String text, BoxFilter filter) {
        this.a = a;
        this.b = b;
        this.text = text;
        this.filter = filter;

        addTask(new UITextRenderTask(text, new Vector2(), 1, 0, WHITE));
    }

    @Override
    public void update() {
        // This Libary Method is EXTREMLY BAD and doesnt work as it is supposed to
        Raylib.Vector2 textDimensions = MeasureTextEx(GetFontDefault(), text, ((UITextRenderTask) tasks.get(0)).scale * 20, 2);
        Vector2 dimensions = new Vector2((float) (textDimensions.x() * 0.48), textDimensions.y());

        ((UITextRenderTask) tasks.get(0)).position = BoxLayoutUtil.applyFilter(a, b, filter).subtract(dimensions);
        ((UITextRenderTask) tasks.get(0)).text = text;
    }

    @Override
    public void loadUIValues(Vector2 position, Vector2 size) {
        this.a = this.a.add(position);
        this.b = this.b.add(position);
    }
}
