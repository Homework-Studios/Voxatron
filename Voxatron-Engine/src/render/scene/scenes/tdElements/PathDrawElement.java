package render.scene.scenes.tdElements;

import game.GameManager;
import render.scene.Element;

public class PathDrawElement extends Element {

    @Override
    public void update() {

    }

    @Override
    public void render() {
        GameManager.instance.pathManager.debugDraw();
    }
}
