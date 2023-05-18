package render.scene;

public abstract class Element {

    public Scene parentScene;

    public abstract void update();
    public abstract void render();
}
