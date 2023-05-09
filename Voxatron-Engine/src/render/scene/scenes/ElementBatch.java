package render.scene.scenes;

import render.scene.Element;

public class ElementBatch extends Element {

    public Element[] elements;

    public boolean isVisible;

    public ElementBatch(Element[] elements, boolean isVisible) {
        this.elements = elements;
        this.isVisible = isVisible;
    }

    @Override
    public void update() {

        if(!isVisible) return;

        // update the elements in the batch
        for (Element element : elements) {
            element.update();
        }
    }

    @Override
    public void render() {

        if(!isVisible) return;

        // render the elements in the batch
        for (Element element : elements) {
            element.render();
        }
    }
}
