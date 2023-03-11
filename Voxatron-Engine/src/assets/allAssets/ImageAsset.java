package assets.allAssets;

import assets.Asset;
import assets.Assets;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class ImageAsset extends Asset {
    public List<Image> images;

    public ImageAsset(String assetName, String assetPath) {
        super(assetName, assetPath, Assets.IMAGE_ASSET);
    }


    public void onLoad() {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().endsWith(".png")) {
                images.add(Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath()));
            }
        }

    }

    @Override
    public void onCreateAsset() {
        setVersion(1);
        setAssetValue("asset_type", assetType.name());
    }
}
