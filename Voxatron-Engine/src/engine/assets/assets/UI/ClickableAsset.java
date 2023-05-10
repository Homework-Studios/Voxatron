package engine.assets.assets.UI;

import engine.assets.Asset;

/**
 * settings format:
 * Texture texture;;
 * Vector2 position;;
 * Vector2 size;;
 * BoxFilter filter;;
 * int minWidth;;
 * int minHeight;;
 * boolean toggled;;
 * boolean isSwitch
 */

public class ClickableAsset extends Asset {

    public ClickableAsset(String name, String path, AssetType type, boolean create) {
        super(name, path, type, create);
    }

    @Override
    public void unload() {
        
    }

    @Override
    public void load() {

    }
}
