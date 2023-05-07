package engine.veobjects;

import engine.assets.Asset;
import engine.scripting.Script;

import java.util.ArrayList;
import java.util.List;

public class VoxatronBaseObject extends VoxatronEngineObject {
    public List<Asset> assetList = new ArrayList<>();
    public List<Script> scriptList = new ArrayList<>();
    public String name = "";

}
