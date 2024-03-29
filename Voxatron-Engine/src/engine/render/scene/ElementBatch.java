package engine.render.scene;

public class ElementBatch extends Element {

    public Element[] elements;

    public boolean isVisible;

    public ElementBatch(Element[] elements, boolean isVisible) {
        this.elements = elements;
        this.isVisible = isVisible;

        for (Element element : elements) {
            element.parentScene = this.parentScene;
        }
    }

    @Override
    public void update() {

        if (!isVisible) return;

        // update the elements in the batch
        for (Element element : elements) {
            element.update();
        }
    }

    @Override
    public void render() {

        if (!isVisible) return;

        // render the elements in the batch
        for (Element element : elements) {
            element.render();
        }
    }

    public void setVisibility(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
    }
}
