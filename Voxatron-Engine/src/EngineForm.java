import javax.swing.*;
import java.awt.*;

public class EngineForm extends JFrame {

    private JPanel MainPanel;
    private JPanel MultiUse;
    private JPanel Game;
    private JPanel Objects;
    private JPanel AssetsPanel;
    private JToolBar ToolBar;
    private JTree tree1;

    public EngineForm() {
        setContentPane(MainPanel);
        getRootPane().setDefaultButton(null);

        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setSize(r.width, r.height);
        setName("Voxatron Engine");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
