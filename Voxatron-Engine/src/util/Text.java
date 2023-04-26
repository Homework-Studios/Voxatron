package util;

import java.awt.*;


public class Text {
    private final StringBuilder sb = new StringBuilder();
    private String color = "white";
    private int size = 12;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private boolean obfuscated = false;


    public Text writeText(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            sb.append("<span style=\"");
            sb.append("color:" + color + ";");
            sb.append("font-size:" + size + "pt;");
            if (bold) sb.append("font-weight:bold;");
            if (italic) sb.append("font-style:italic;");
            if (underline) sb.append("text-decoration:underline;");
            if (obfuscated) sb.append("text-shadow:0 0 2px black;");
            sb.append("\">" + line + "</span>");
        }
        return this;
    }

    @Override
    public String toString() {
        return "<html>" + sb + "</html>";
    }

    public String getColor() {
        return color;
    }

    public Text setColor(String color) {
        this.color = color;
        return this;
    }

    public Text setColor(Color color) {
        this.color = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        return this;
    }

    public int getSize() {
        return size;
    }

    public Text setSize(int size) {
        this.size = size;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public Text setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    public Text setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isUnderline() {
        return underline;
    }

    public Text setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public Text setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

}

