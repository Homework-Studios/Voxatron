package render.scene;

import java.util.ArrayList;

public abstract class Scene {

    public ArrayList<Element> elements;

    public Scene() {
        elements = new ArrayList<>();
        init();
    }

    // Sort the abstracts in the correct order -> Saves time when auto-completing
    public abstract void init();

    public abstract void update();

    public abstract void render();

    public Element[] getIterableElements() {
        return elements.toArray(new Element[0]);
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }

    public void clearElements() {
        elements.clear();
    }

    public void reload() {
        elements.clear();
        init();
    }

}
