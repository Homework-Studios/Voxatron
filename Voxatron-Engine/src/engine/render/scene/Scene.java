package engine.render.scene;

import com.raylib.Jaylib;

import java.util.ArrayList;

public abstract class Scene {

    public Jaylib.Color clearColor = new Jaylib.Color(0, 0, 0, 255);

    public ArrayList<Element> elements;
    public ArrayList<Element> elements3d;

    public Scene() {
        elements = new ArrayList<>();
        elements3d = new ArrayList<>();
        init();
    }

    // Sort the abstracts in the correct order -> Saves time when auto-completing
    public abstract void init();

    public abstract void update();

    public abstract void render();

    public Element[] getIterableElements() {
        return elements.toArray(new Element[0]);
    }

    public Element[] getIterableElements3d() {
        return elements3d.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
        element.parentScene = this;
    }

    public void addElement3d(Element element) {
        elements3d.add(element);
        element.parentScene = this;
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }

    public void removeElement3d(Element element) {
        elements3d.remove(element);
    }

    public void clearElements() {
        elements.clear();
    }

    public void clearElements3d() {
        elements3d.clear();
    }

    public void reload() {
        elements.clear();
        elements3d.clear();
        init();
    }

}
