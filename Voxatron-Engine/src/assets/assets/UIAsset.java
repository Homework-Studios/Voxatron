package assets.assets;

import assets.Asset;
import jdk.jshell.spi.ExecutionControl;

public class UIAsset extends Asset {
    public UIAsset(String assetName, String assetPath) throws ExecutionControl.NotImplementedException {
        super(assetName, assetPath);
        throw new ExecutionControl.NotImplementedException("UIAsset is not implemented yet");
//        TODO: implement
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
