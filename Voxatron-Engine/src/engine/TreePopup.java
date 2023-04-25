package engine;

import assets.Asset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static engine.EngineForm.*;

class TreePopup extends MouseAdapter {

    private void onTreeHover(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree tree = (JTree) e.getSource();
        TreePath path = tree.getPathForLocation(x, y);
        if (path == null)
            return;

        DefaultMutableTreeNode rightClickedNode = (DefaultMutableTreeNode) path
                .getLastPathComponent();
        TreePath[] selectionPaths = tree.getSelectionPaths();

        boolean isSelected = false;
        if (selectionPaths != null) {
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.equals(path)) {
                    isSelected = true;
                    break;
                }
            }
        }
        if (!isSelected) {
            tree.setSelectionPath(path);
        }
        JPopupMenu popup = new JPopupMenu();
        JPopupMenu newPopup = new JPopupMenu();
        List<JMenuItem> menuItems = new ArrayList<>();

        final JMenuItem backMenuItem = new JMenuItem("◀");
        backMenuItem.addActionListener(ev -> {
            popup.show(tree, x, y);
        });
        setStandardVisualsToButtons(backMenuItem);
        newPopup.add(backMenuItem);
        final JDialog dialog = new JDialog();
        final JTextField textField = new JTextField();
        textField.setBackground(background);
        textField.setForeground(highlight);
        textField.setBorder(new LineBorder(highlightArea, 1, true));
        dialog.add(textField);
        dialog.setSize(100, 15);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(tree);

        ActionMap actionMap = textField.getActionMap();
        InputMap inputMap = textField.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "enderAction");

        for (Asset.AssetType assetType : Asset.AssetType.values()) {
            final JMenuItem newMenuItem = new JMenuItem(assetType.toString());
            setStandardVisualsToButtons(newMenuItem);
            newMenuItem.addActionListener(ev -> {
                Action enterAction = new EnterAction(dialog, textField, (DefaultTreeModel) tree.getModel(), rightClickedNode, assetType);
                actionMap.put("enterAction", enterAction);
                dialog.setVisible(true);
                dialog.setLocation(MouseInfo.getPointerInfo().getLocation());
            });
            newPopup.add(newMenuItem);
        }

        final JMenuItem createMenuItem = new JMenuItem("New");
        createMenuItem.addActionListener(ev -> {
            newPopup.show(tree, x, y);
        });
        menuItems.add(createMenuItem);


        final JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener(ev -> {
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

            TreeNode[] nodes = rightClickedNode.getPath();
            StringBuilder assetPath = new StringBuilder();
            for (int i = 1; i < nodes.length; i++) {
                TreeNode node = nodes[i];
                assetPath.append(node.toString() + "/");
            }
            Asset.removeFileByNode(rightClickedNode);

            model.removeNodeFromParent(rightClickedNode);
        });
        menuItems.add(deleteMenuItem);

        newPopup.setForeground(highlight);
        newPopup.setBackground(background);
        newPopup.setBorder(new LineBorder(highlightArea, 1, true));
        popup.setForeground(highlight);
        popup.setBackground(background);
        popup.setBorder(new LineBorder(highlightArea, 1, true));
        for (JMenuItem menuItem : menuItems) {
            setStandardVisualsToButtons(menuItem);
            popup.add(menuItem);
        }
        popup.show(tree, x, y);
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            onTreeHover(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            onTreeHover(e);
    }

    private void setStandardVisualsToButtons(JMenuItem item) {
        item.setForeground(highlight);
        item.setBackground(background);
        item.setBorder(new LineBorder(highlightArea, 1, true));
        item.addChangeListener(e1 -> {
            JMenuItem model = (JMenuItem) e1.getSource();
            if (model.isArmed()) {
                item.setBorder(new LineBorder(importantHighlight, 1, true));
            } else {
                item.setBorder(new LineBorder(highlightArea, 1, true));
            }
        });
    }

    private static class EnterAction extends AbstractAction {

        final JDialog target;
        final JTextField textField;
        final DefaultTreeModel model;
        final DefaultMutableTreeNode rightClickedNode;
        final Asset.AssetType assetType;

        public EnterAction(JDialog target, JTextField textField, DefaultTreeModel model, DefaultMutableTreeNode rightClickedNode, Asset.AssetType assetType) {
            this.target = target;
            this.textField = textField;
            this.model = model;
            this.rightClickedNode = rightClickedNode;
            this.assetType = assetType;
        }

        public void actionPerformed(ActionEvent e) {
            target.setVisible(false);
            String text = textField.getText();
            if (text != null && !text.isEmpty()) {
                Asset.createOrLoadAsset(text, Asset.getPathByNode(rightClickedNode), assetType, true);
            }
        }
    }

}
