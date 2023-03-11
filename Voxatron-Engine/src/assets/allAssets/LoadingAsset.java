package assets.allAssets;

import assets.Asset;
import assets.Assets;

public class LoadingAsset extends Asset {
    public LoadingAsset(String assetName, String assetPath) {
        super(assetName, assetPath, Assets.NULL);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onCreateAsset() {

    }
}
