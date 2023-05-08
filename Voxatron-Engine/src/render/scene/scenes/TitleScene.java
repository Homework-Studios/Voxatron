package render.scene.scenes;

import render.scene.Element;
import render.scene.Scene;
import render.scene.scenes.titleElements.VTBannerElement;

public class TitleScene extends Scene {

    public TitleScene(){
        super();

        addElement(new VTBannerElement());
    }

    @Override
    public void update() {
        for (Element element : getIterableElements()) {
            element.update();
        }
    }

    @Override
    public void render() {
        for (Element element : elements) {
            element.render();
        }
    }
}
