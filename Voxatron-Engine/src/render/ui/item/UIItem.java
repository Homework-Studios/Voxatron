package render.ui.item;

import com.raylib.Raylib;
import math.Vector2;
import render.task.RenderTask;
import render.ui.UIScreen;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class UIItem {

    public String uuid;

    public List<RenderTask> tasks = new ArrayList<>();

    public UIScreen screen;

    public boolean hidden = false;

    Vector2 previousPosition = new Vector2(0, 0);

    public UIItem() {
        uuid = UUIDUtil.generateUUID();
    }

    public void addTask(RenderTask task) {
        tasks.add(task);
    }

    public void removeTask(RenderTask task) {
        tasks.remove(task);
    }

    public void loadScreen(UIScreen screen) {
        this.screen = screen;
    }

    public abstract void update();

    public abstract void loadUIValues(Vector2 position, Vector2 size);

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isMouseOver(Vector2 areaPosition, Vector2 areaSize) {
        return isVectorOver(areaPosition, areaSize, new Vector2(Raylib.GetMouseX(), Raylib.GetMouseY()));
    }

    public boolean isVectorOver(Vector2 areaPosition, Vector2 areaSize, Vector2 vector) {
        areaPosition = areaPosition.subtract(areaSize.divide(new Vector2(2, 2)));
        return vector.x > areaPosition.x && vector.x < areaPosition.x + areaSize.x && vector.y > areaPosition.y && vector.y < areaPosition.y + areaSize.y;
    }
}
