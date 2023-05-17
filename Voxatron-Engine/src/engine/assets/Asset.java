package engine.assets;

import engine.DevelopmentConstants;
import engine.assets.basic.ImageAsset;
import engine.assets.basic.SoundAsset;
import util.FileUtils;
import util.Text;
import util.TreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static engine.EngineForm.highlightArea;

public abstract class Asset {
    public static final HashMap<String, Asset> path_assets = new HashMap<>();
    public static final HashMap<String, DefaultMutableTreeNode> path_nodes = new HashMap<>();
    //Master Class related
    public static String ASSET_DIR = System.getenv("APPDATA") + "\\Voxatron\\Assets";
    public static JTree tree;

    //Asset related
    String name;
    String path;
    AssetType type;
    File directory;
    File assetFile;

    public Asset(String name, String path, AssetType type, boolean createAsset) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.directory = new File(ASSET_DIR + "/" + path + "/" + name);
        this.assetFile = new File(directory, type + ".asset");
        if (createAsset) createAsset();
        path_assets.put(path + "\\" + name, this);
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            createNodeLevelUp(path + "\\" + name);
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                createNodeLevelUp(path + "\\" + name + "\\" + file.getName());
            }
            updateNaming();
        }
        load();
    }

    public static void clearAssets() {
        path_assets.values().forEach(Asset::unload);
    }

    public static void createOrLoadAsset(String name, String path, AssetType type, boolean createAsset) {
        switch (type) {
            case Directory:
                new File(ASSET_DIR + "\\" + path + "\\" + name).mkdirs();
                createNodeLevelUp(path + "\\" + name);
                break;
            case File:
                try {
                    new File(ASSET_DIR + "\\" + path + "\\" + name).createNewFile();
                    createNodeLevelUp(path + "\\" + name);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            case ImageAsset:
                new ImageAsset(name, path, type, createAsset);
                break;
            case SoundAsset:
                new SoundAsset(name, path, type, createAsset);
                break;
            default:
        }
    }

    /**
     * Gets the path of a tree node.
     *
     * @param node the tree node
     * @return the path of the tree node
     */
    public static String getPathByNode(DefaultMutableTreeNode node) {
        String path = "";
        TreeNode[] paths = node.getPath();
        for (int i = 1; i < paths.length; i++) {
            path += paths[i].toString();
            if (i != paths.length - 1) {
                path += "\\";
            }
        }
        path = path.replaceAll("<.*?>", "");
        String[] split = path.split(" ");
        return split[0];
    }

    /**
     * Initializes the asset system.
     */
    public static void init() {
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            path_nodes.put("", (DefaultMutableTreeNode) tree.getModel().getRoot());
        }
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
        updateTreeNodes();
        if (DevelopmentConstants.DEVELOPMENT_MODE) {
            tree.expandPath(new TreePath(path_nodes.get("").getPath()));
        }
    }

    /**
     * Loads an asset from a file.
     *
     * @param assetFile the file containing the asset
     */
    public static void loadAsset(File assetFile) {
        if (assetFile.exists()) {
            createOrLoadAsset(assetFile.getParentFile().getName(), assetFile.getParentFile().getParentFile().getPath().replace(ASSET_DIR, ""), AssetType.valueOf(assetFile.getName().replace(".asset", "")), false);
        }
    }

    /**
     * Gets the currently selected asset.
     *
     * @return the currently selected asset
     */
    public static Asset getSelectedAsset() {
        TreePath selectionPath = tree.getSelectionPath();
        if (selectionPath != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
            if (node != null) {
                return path_assets.get(getPathByNode(node));
            }
        }
        return null;
    }

    /**
     * Updates the tree nodes to match the assets.
     */
    public static void updateTreeNodes() {
        for (File file : FileUtils.getAllFiles(new File(ASSET_DIR))) {
            String path = file.getAbsolutePath().replace(ASSET_DIR, "");
            createNodeLevelUp(path);
        }
    }

    /**
     * Removes a file from the asset system.
     *
     * @param node the tree node representing the file
     */
    public static void removeFileByNode(DefaultMutableTreeNode node) {
        String path = getPathByNode(node);
        path = path.replaceAll("<.*?>", "");
        String[] split = path.split(" ");
        File file = new File(ASSET_DIR + "\\" + split[0]);
        FileUtils.deleteFileOrDirectory(file);
        ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
    }

    /**
     * Creates a tree node for a path.
     *
     * @param path the path to create a node for
     */
    public static void createNodeLevelUp(String path) {
        if (!DevelopmentConstants.DEVELOPMENT_MODE) return;
        TreeUtils.genTreeNodesLevelUp(path, path_nodes, tree);
    }

    /**
     * Updates the name of the tree node representing the asset.
     *
     * @param name the new name of the asset
     * @param path the path of the asset
     * @param type the type of the asset
     */
    public static void updateAssetNodeName(String name, String path, AssetType type) {
        if (!path.startsWith("\\")) path = "\\" + path;
        DefaultMutableTreeNode node = path_nodes.get(path);
        node.setUserObject(new Text().writeText(name + " ").setItalic(true).setSize(10).setColor(highlightArea).writeText("(" + type + ")"));
    }

    /**
     * Unloads the asset.
     */
    public abstract void unload();

    /**
     * Updates the naming of the asset.
     */
    public void updateNaming() {
        updateAssetNodeName(getName(), getAbsolutePath(), getType());
    }

    /**
     * Renames the asset.
     *
     * @param test the new name of the asset
     */
    public void rename(String test) {
        updateAssetNodeName(test, path, type);
    }

    /**
     * Loads the asset.
     */
    public abstract void load();

    /**
     * Deletes the asset.
     */
    public void delete() {
        FileUtils.deleteFileOrDirectory(directory);
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.removeNodeFromParent(path_nodes.get(path));
    }

    /**
     * Returns the path of the asset.
     *
     * @return the path of the asset
     */
    @Override
    public String toString() {
        return path;
    }

    /**
     * Creates the asset directory and file if they do not exist.
     * If the directory or file already exists, this method does nothing.
     *
     * @throws RuntimeException if there is an IOException while creating the directory or file
     */
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

    /**
     * Gets the directory where the asset is stored.
     *
     * @return the directory where the asset is stored
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Gets the absolute path of the asset.
     *
     * @return the absolute path of the asset
     */
    public String getAbsolutePath() {
        return path + "\\" + name;
    }

    //region Getter/Setter

    /**
     * Gets the file where the asset is stored.
     *
     * @return the file where the asset is stored
     */
    public File getAssetFile() {
        return assetFile;
    }

    /**
     * Gets the name of the asset.
     *
     * @return the name of the asset
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the path of the asset.
     *
     * @return the path of the asset
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the type of the asset.
     *
     * @return the type of the asset
     */
    public AssetType getType() {
        return type;
    }

    /**
     * The types of assets.
     */
    public enum AssetType {
        //Utility Asset Types (no actual asset)
        Directory,
        File,
        //Assets
        ImageAsset,
        SoundAsset,

    }

    /**
     * A value associated with an asset.
     */
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
