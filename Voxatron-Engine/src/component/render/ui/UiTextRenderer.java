package component.render.ui;


import com.raylib.Jaylib;
import com.raylib.Raylib;
import component.render.RenderComponent;

import java.awt.*;

public class UiTextRenderer extends RenderComponent {
    
    private String text;
    private Jaylib.Vector2 position = new Jaylib.Vector2(0, 0);
    private float scale = 1;
    private float rotation = 0;
    public Color color = Color.WHITE;
    
    public UiTextRenderer(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(Raylib raylib) {
        //raylib.text.DrawText(text, (int)position.x, (int)position.y, (int)(20 * scale), Color.WHITE);
    }
}
