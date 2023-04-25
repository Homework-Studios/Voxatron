package assets;

import assets.assets.UI.ClickableAsset;
import assets.assets.UI.ImageAsset;
import util.FileUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Asset {
    public static final HashMap<String, Asset> path_assets = new HashMap<>();
    public static final HashMap<String, DefaultMutableTreeNode> path_nodes = new HashMap<>();
    //Master Class related
    public static boolean DevMode = true;
    public static String ASSET_DIR = System.getenv("APPDATA") + "\\Voxatron\\Assets";
    public static JTree tree;

    //Asset related
    String name;
    String path;
    AssetType type;
    File directory;
    File assetFile;
    HashMap<String, AssetValue> valueHashMap;

    public Asset(String name, String path, AssetType type, boolean createAsset) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.directory = new File(ASSET_DIR + "/" + path + "/" + name);
        this.assetFile = new File(directory, type + ".asset");
        this.valueHashMap = new HashMap<>();
        if (createAsset) createAsset();
        path_assets.put(path + "\\" + name, this);

        updateTreeNodes();
    }

    public static void createOrLoadAsset(String name, String path, AssetType type, boolean createAsset) {
        switch (type) {
            case Directory -> {
                new File(ASSET_DIR + "\\" + path + "\\" + name).mkdirs();
                createNodeLevelUp(path + "\\" + name);
            }
            case File -> {
                try {
                    new File(ASSET_DIR + "\\" + path + "\\" + name).createNewFile();
                    createNodeLevelUp(path + "\\" + name);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case ImageAsset -> new ImageAsset(name, path, type, createAsset);
            case ClickableAsset -> new ClickableAsset(name, path, type, createAsset);
        }
    }

    public static String getPathByNode(DefaultMutableTreeNode node) {
        String path = "";
        TreeNode[] paths = node.getPath();
        for (int i = 1; i < paths.length; i++) {
            path += paths[i].toString();
            if (i != paths.length - 1) {
                path += "\\";
            }
        }
        return path;
    }

    public static void init() {
        path_nodes.put("", (DefaultMutableTreeNode) tree.getModel().getRoot());
        File assetDir = new File(ASSET_DIR);
        if (!assetDir.exists()) {
            assetDir.mkdirs();
        }
        for (File subFile : FileUtils.getSubFiles(assetDir)) {
            if (subFile.isDirectory()) continue;
            if (subFile.getName().endsWith(".asset")) {
                loadAsset(subFile);
            }
        }

    }

    public static void loadAsset(File assetFile) {
        if (assetFile.exists()) {
            createOrLoadAsset(assetFile.getParentFile().getName(), assetFile.getParentFile().getParentFile().getPath().replace(ASSET_DIR, ""), AssetType.valueOf(assetFile.getName().replace(".asset", "")), false);
        }
    }

    public static Asset getSelectedAsset() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        return path_assets.get(getPathByNode(node));
    }

    public static void updateTreeNodes() {
        for (File file : FileUtils.getAllFiles(new File(ASSET_DIR))) {
            String path = file.getAbsolutePath().replace(ASSET_DIR, "");
            createNodeLevelUp(path);
        }

        path_assets.values().forEach(asset ->
                asset.updateAssetNodeName(asset.getName(), asset.getAbsolutePath(), asset.getType()));
    }

    public static void removeFileByNode(DefaultMutableTreeNode node) {
        String path = getPathByNode(node);
        File file = new File(ASSET_DIR + "\\" + path);
        FileUtils.deleteFileOrDirectory(file);
    }

    public static void createNodeLevelUp(String path) {
        if (!path.startsWith("\\")) path = "\\" + path;
        String parentPath = path.substring(0, path.lastIndexOf("\\"));
        DefaultMutableTreeNode child = path_nodes.get(path);
        if (child == null) {
            child = new DefaultMutableTreeNode(path.substring(path.lastIndexOf("\\") + 1));
            path_nodes.put(path, child);
        }

        DefaultMutableTreeNode parent = path_nodes.get(parentPath);
        if (parent == null) {
            String parentName = parentPath.substring(parentPath.lastIndexOf("\\") + 1);
            parent = new DefaultMutableTreeNode(parentName);
            path_nodes.put(parentPath, parent);
        }

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.insertNodeInto(child, parent, 0);
        tree.expandPath(tree.getSelectionPath());
    }

    public void updateValues() {
        //Todo: implement
    }

    public void updateAssetNodeName(String name, String path, AssetType type) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode node = path_nodes.get(path);
        if (node == null) {
            return;
        }
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        List<DefaultMutableTreeNode> children = new ArrayList<>();
        model.removeNodeFromParent(node);
        while (node.getChildCount() != 0) {
            children.add((DefaultMutableTreeNode) node.getFirstChild());
            model.removeNodeFromParent((DefaultMutableTreeNode) node.getFirstChild());
        }
        node = new DefaultMutableTreeNode(name + " (" + type + ")");

        path_nodes.put(path, node);
        for (DefaultMutableTreeNode child : children) {
            model.insertNodeInto(child, node, 0);
            System.out.println(child);
        }
        model.insertNodeInto(node, parent, 0);
    }

    public void rename(String test) {
        updateAssetNodeName(test, path, type);
    }

    public abstract void load();

    public void createAsset() {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!assetFile.exists()) {
                assetFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        directory.delete();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.removeNodeFromParent(path_nodes.get(path));
    }

    //region Getter/Setter
    public File getDirectory() {
        return directory;
    }

    public String getAbsolutePath() {
        return path + "\\" + name;
    }

    public File getAssetFile() {
        return assetFile;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public AssetType getType() {
        return type;
    }

    public HashMap<String, AssetValue> getValueHashMap() {
        return valueHashMap;
    }


    public enum AssetType {
        //Utility Asset Types (no actual asset)
        Directory(),
        File(),
        //Assets
        ImageAsset(),
        ClickableAsset(),

    }


    public static class AssetValue {
        String value;

        public AssetValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
//endregion
}
