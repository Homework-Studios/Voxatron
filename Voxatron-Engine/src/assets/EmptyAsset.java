package assets;

public class EmptyAsset extends Asset {
    public EmptyAsset(String name, String dir) {
        super(name, dir);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void afterCreate() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void afterLoad() {

    }

    @Override
    public void onUnload() {

    }

    @Override
    public void afterUnload() {

    }
}
