package engine;

import com.raylib.Raylib;
import engine.assets.Asset;
import engine.scripting.ScriptingManager;
import engine.veobjects.ObjectManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

public class EngineForm extends JFrame {

    public static DefaultMutableTreeNode assetsRoot = new DefaultMutableTreeNode("Assets");
    public static DefaultTreeModel model;
    public static EngineForm instance;
    public static Color background = new Color(43, 45, 48);
    public static Color highlightArea = new Color(101, 101, 101);
    public static Color highlight = new Color(255, 255, 255);
    public static Color importantHighlight = new Color(37, 255, 151, 255);
    public JTree AssetTree;
    public JPanel Game;
    private JPanel MainPanel;
    private JTree WorldObjectsTree;
    private JTabbedPane tabbedPane1;
    private JPanel Inspector;
    private JPanel AssetPanel;
    private JPanel DebuggerPanel;
    private JPanel BottomSpacingPane;
    private JPanel LeftSpacingPane;
    private JPanel TopSpacing;
    private JTextPane Debugger;
    private JPanel ScriptsPanel;
    private JPanel ObjectsPanel;
    private JTree ScriptsTree;
    private JTree ObjectsTree;


    public EngineForm() {
        Asset.tree = AssetTree;
        ScriptingManager.tree = ScriptsTree;
        ObjectManager.tree = ObjectsTree;
        ObjectManager.worldTree = WorldObjectsTree;

        ScriptsTree.setRootVisible(false);

        ((DefaultTreeModel) ScriptsTree.getModel()).setRoot(new DefaultMutableTreeNode("Scripts"));
        ((DefaultTreeModel) ObjectsTree.getModel()).setRoot(new DefaultMutableTreeNode("Objects"));
        ((DefaultTreeModel) WorldObjectsTree.getModel()).setRoot(new DefaultMutableTreeNode("WorldObjects"));

        instance = this;
        setContentPane(MainPanel);
        Raylib.SetConfigFlags(Raylib.FLAG_WINDOW_TOPMOST);
        String resourceDir = "/resources/";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(EngineForm.class.getResource(resourceDir + "icon.png")));
        setIconImage(icon.getImage());

        int desiredWidth = 16;
        int desiredHeight = 16;
        if (icon.getIconWidth() > desiredWidth || icon.getIconHeight() > desiredHeight) {
            Image img = icon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setName("Voxatron Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Action Listeners
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                Raylib.MinimizeWindow();
                super.windowIconified(e);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Raylib.MaximizeWindow();
                super.windowDeiconified(e);
            }
        });

        //set sizing
        Game.setPreferredSize(new Dimension((int) (500 * 1.7), 500));
        tabbedPane1.setPreferredSize(new Dimension(200, 500));
        WorldObjectsTree.setPreferredSize(new Dimension(200, 500));
        DebuggerPanel.setPreferredSize(new Dimension(1, 150));

        //set colors because it does not work with editor,
        ImageIcon finalIcon = icon;
        TreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value, boolean selected, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                String stringValue = tree.convertValueToText(value, selected,
                        expanded, leaf, row, hasFocus);
                setBackgroundSelectionColor(background);
                setBackgroundNonSelectionColor(background);
                setTextSelectionColor(highlight);
                setTextNonSelectionColor(highlight);
                setBorderSelectionColor(importantHighlight);

                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

                setIcon(new ImageIcon());
                String[] split = stringValue.split("\\(")[0].split("\\.");


                return this;
            }
        };

        MainPanel.setBackground(background);
        Game.setBackground(highlightArea);
        tabbedPane1.setBackground(background);
        WorldObjectsTree.setBackground(background);
        DebuggerPanel.setBackground(background);
        Debugger.setBackground(background);
        AssetTree.setBackground(background);
        Inspector.setBackground(background);
        ScriptsTree.setBackground(background);
        ObjectsTree.setBackground(background);

        WorldObjectsTree.setForeground(highlight);
        tabbedPane1.setForeground(highlight);
        DebuggerPanel.setForeground(highlight);
        Debugger.setForeground(highlight);
        AssetTree.setForeground(highlight);
        ScriptsTree.setForeground(highlight);
        ObjectsTree.setForeground(highlight);

        //Trees
        WorldObjectsTree.setCellRenderer(treeCellRenderer);
        AssetTree.setCellRenderer(treeCellRenderer);
        ScriptsTree.setCellRenderer(treeCellRenderer);
        ObjectsTree.setCellRenderer(treeCellRenderer);

        WorldObjectsTree.setTransferHandler(new TreeTransferHandler());
        WorldObjectsTree.setDragEnabled(true);
        WorldObjectsTree.setDropMode(DropMode.ON_OR_INSERT);
        WorldObjectsTree.setEditable(true);

        WorldObjectsTree.setTransferHandler(new TreeTransferHandler());
        WorldObjectsTree.setDragEnabled(true);
        WorldObjectsTree.setDropMode(DropMode.ON_OR_INSERT);
        WorldObjectsTree.setEditable(true);


        //Tabs
        for (Component component : tabbedPane1.getComponents()) {
            component.setBackground(background);
        }

        //region Render Changing
        //Part original Native Java code but changed to fit style
        BasicTabbedPaneUI tabbedPaneUI = new BasicTabbedPaneUI() {
            //can be changed if wanted makes the tabs split form each other
            final int tabSizeShift = 0;

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                int width = tabPane.getWidth();
                int height = tabPane.getHeight();
                Insets insets = tabPane.getInsets();
                //Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

                int x = insets.left;
                int y = insets.top;
                int w = width - insets.right - insets.left;
                int h = height - insets.top - insets.bottom;

                switch (tabPlacement) {
                    case LEFT:
                        x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                        w -= (x - insets.left);
                        break;
                    case RIGHT:
                        w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                        break;
                    case BOTTOM:
                        h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                        break;
                    case TOP:
                    default:
                        y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                        h -= (y - insets.top);
                }

                if (tabPane.getTabCount() > 0 && (tabPane.isOpaque())) {
                    // Fill region behind content area
                    Color color = UIManager.getColor("TabbedPane.contentAreaColor");
                    if (color != null) {
                        g.setColor(color);
                    } else if (selectedIndex == -1) {
                        g.setColor(tabPane.getBackground());
                    } else {
                        g.setColor(highlightArea);
                    }
                    g.fillRect(x, y, w, h);
                }

                paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
                paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);

            }

            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y, x + w - 2, y);
            }

            @Override
            protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y, x + 2, y + h - 2);
            }

            @Override
            protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
            }

            @Override
            protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(highlightArea);
                g.drawLine(x + w - 2, y, x + w - 2, y + h);
            }


            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(importantHighlight);
                } else {
                    g.setColor(background);
                }
                int bottom = (int) (h * 0.1);
                g.fillRect(x + tabSizeShift, y + h - bottom, w, bottom);
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement,
                                          int tabIndex,
                                          int x, int y, int w, int h,
                                          boolean isSelected) {
                g.setColor(highlightArea);

                //Only top is changed to fit style
                switch (tabPlacement) {
                    case LEFT:
                        g.drawLine(x, y, x, y + h); // left highlight
                        g.drawLine(x, y + h - 1, x + 1, y + h - 1); // bottom-left highlight
                        g.drawLine(x + 1, y, x + w - 1, y); // top highlight
                        break;
                    case RIGHT:
                        g.drawLine(x + w - 2, y, x + w - 2, y + h - 1); // right highlight
                        break;
                    case BOTTOM:
                        g.drawLine(x, y + h - 2, x + w - 1, y + h - 2); // bottom highlight
                        g.drawLine(x, y, x, y + h - 2); // left highlight
                        break;
                    case TOP:
                    default:
                        g.drawLine(x, y, x + w - 1, y); // top highlight
                        g.drawLine(x, y, x, y + h - 1); // left highlight
                        g.drawLine(x + w - 1, y, x + w - 1, y + h - 1); // right highlight
                }
            }


        };
        BasicTreeUI worldObjectsUI = new BasicTreeUI() {
            @Override
            protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
                g.setColor(highlightArea);
                g.drawLine(x, top, x, bottom);
            }

            @Override
            protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
                g.setColor(highlightArea);
                g.drawLine(left, y, right, y);
            }

            @Override
            protected void paintExpandControl(Graphics g,
                                              Rectangle clipBounds, Insets insets,
                                              Rectangle bounds, TreePath path,
                                              int row, boolean isExpanded,
                                              boolean hasBeenExpanded,
                                              boolean isLeaf) {
                Object value = path.getLastPathComponent();

                // Draw icons if not a leaf and either hasn't been loaded,
                // or the model child count is > 0.
                if (!isLeaf && (!hasBeenExpanded ||
                        treeModel.getChildCount(value) > 0)) {
                    int middleXOfKnob;
                    middleXOfKnob = bounds.x - getRightChildIndent() + 1;
                    int middleYOfKnob = bounds.y + (bounds.height / 2);


                    Icon expandedIcon = getExpandedIcon();
                    if (expandedIcon != null)
                        g.fillRoundRect(middleXOfKnob - 2, middleYOfKnob - 2, 3, 3, 1, 1);

                }
            }
        };
        BasicTreeUI objectsUI = new BasicTreeUI() {
            @Override
            protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
                g.setColor(highlightArea);
                g.drawLine(x, top, x, bottom);
            }

            @Override
            protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
                g.setColor(highlightArea);
                g.drawLine(left, y, right, y);
            }

            @Override
            protected void paintExpandControl(Graphics g,
                                              Rectangle clipBounds, Insets insets,
                                              Rectangle bounds, TreePath path,
                                              int row, boolean isExpanded,
                                              boolean hasBeenExpanded,
                                              boolean isLeaf) {
                Object value = path.getLastPathComponent();

                // Draw icons if not a leaf and either hasn't been loaded,
                // or the model child count is > 0.
                if (!isLeaf && (!hasBeenExpanded ||
                        treeModel.getChildCount(value) > 0)) {
                    int middleXOfKnob;
                    middleXOfKnob = bounds.x - getRightChildIndent() + 1;
                    int middleYOfKnob = bounds.y + (bounds.height / 2);


                    Icon expandedIcon = getExpandedIcon();
                    if (expandedIcon != null)
                        g.fillRoundRect(middleXOfKnob - 2, middleYOfKnob - 2, 3, 3, 1, 1);

                }
            }
        };
        BasicTreeUI scriptsUI = new BasicTreeUI() {
            @Override
            protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
                g.setColor(highlightArea);
                g.drawLine(x, top, x, bottom);
            }

            @Override
            protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
                g.setColor(highlightArea);
                g.drawLine(left, y, right, y);
            }

            @Override
            protected void paintExpandControl(Graphics g,
                                              Rectangle clipBounds, Insets insets,
                                              Rectangle bounds, TreePath path,
                                              int row, boolean isExpanded,
                                              boolean hasBeenExpanded,
                                              boolean isLeaf) {
                Object value = path.getLastPathComponent();

                // Draw icons if not a leaf and either hasn't been loaded,
                // or the model child count is > 0.
                if (!isLeaf && (!hasBeenExpanded ||
                        treeModel.getChildCount(value) > 0)) {
                    int middleXOfKnob;
                    middleXOfKnob = bounds.x - getRightChildIndent() + 1;
                    int middleYOfKnob = bounds.y + (bounds.height / 2);


                    Icon expandedIcon = getExpandedIcon();
                    if (expandedIcon != null)
                        g.fillRoundRect(middleXOfKnob - 2, middleYOfKnob - 2, 3, 3, 1, 1);

                }
            }
        };
        BasicTreeUI assetsUI = new BasicTreeUI() {
            @Override
            protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
                g.setColor(highlightArea);
                g.drawLine(x, top, x, bottom);
            }

            @Override
            protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
                g.setColor(highlightArea);
                g.drawLine(left, y, right, y);
            }

            @Override
            protected void paintExpandControl(Graphics g,
                                              Rectangle clipBounds, Insets insets,
                                              Rectangle bounds, TreePath path,
                                              int row, boolean isExpanded,
                                              boolean hasBeenExpanded,
                                              boolean isLeaf) {
                Object value = path.getLastPathComponent();

                // Draw icons if not a leaf and either hasn't been loaded,
                // or the model child count is > 0.
                if (!isLeaf && (!hasBeenExpanded ||
                        treeModel.getChildCount(value) > 0)) {
                    int middleXOfKnob;
                    middleXOfKnob = bounds.x - getRightChildIndent() + 1;
                    int middleYOfKnob = bounds.y + (bounds.height / 2);


                    Icon expandedIcon = getExpandedIcon();
                    if (expandedIcon != null)
                        g.fillRoundRect(middleXOfKnob - 2, middleYOfKnob - 2, 3, 3, 1, 1);

                }
            }
        };
        WorldObjectsTree.setUI(worldObjectsUI);
        ObjectsTree.setUI(objectsUI);
        ScriptsTree.setUI(scriptsUI);
        AssetTree.setUI(assetsUI);


        tabbedPane1.setUI(tabbedPaneUI);
        DebuggerPanel.setBorder(new LineBorder(highlightArea, 1, true));
        WorldObjectsTree.setBorder(new LineBorder(highlightArea, 1, true));
        //endregion

        //Debugging

        StyledDocument document = Debugger.getStyledDocument();
        try {
            document.insertString(0, "Start of debugging" + System.lineSeparator(), null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (true) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(baos, true);
                PrintStream old = System.out;
                System.setOut(printStream);
                try {
                    System.out.flush();
                    if (baos.toString().length() > 0) {
                        document.insertString(document.getLength(), baos + System.lineSeparator(), null);
                    }
                    System.setOut(old);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });


        //Assets
        model = new DefaultTreeModel(assetsRoot);

        AssetTree.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '':
                        Asset.removeFileByNode((DefaultMutableTreeNode) Objects.requireNonNull(AssetTree.getSelectionPath()).getLastPathComponent());
                        break;
                    case ' ':
                        Objects.requireNonNull(Asset.getSelectedAsset()).rename("Test");
                        break;
                }
                System.out.println("e.getKeyChar() = " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        AssetTree.addMouseListener(new TreePopup());
        AssetTree.setModel(model);
        AssetTree.setCellRenderer(treeCellRenderer);
    }

    private void createUIComponents() {

    }
}
