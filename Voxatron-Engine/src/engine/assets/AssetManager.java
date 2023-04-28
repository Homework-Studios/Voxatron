package engine.assets;

public class AssetManager<Type> {

    public Type getAsset(String path) {
        return (Type) Asset.path_assets.get("\\" + path);
    }
}
