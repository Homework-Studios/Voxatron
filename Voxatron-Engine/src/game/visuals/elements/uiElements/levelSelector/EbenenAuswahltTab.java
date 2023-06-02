package game.visuals.elements.uiElements.levelSelector;

import com.raylib.Raylib;

public class EbenenAuswahltTab {

    public String name;
    public Raylib.Color farbe;
    public Raylib.Texture vorderbild;
    public boolean verschlossen;

    public int vorderbildY = 0;

    public EbenenAuswahltTab(String name, Raylib.Color farbe, Raylib.Texture vorderbild, boolean verschlossen) {
        this.name = name;
        this.farbe = farbe;
        this.vorderbild = vorderbild;
        this.verschlossen = verschlossen;
    }
}
