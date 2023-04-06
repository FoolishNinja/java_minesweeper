package game.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Tile extends Component {
    private Type type;
    private int x;
    private int y;
    private int value;
    private final int size = 40;
    private boolean reveal = false;
    private HashMap<String,BufferedImage> images;

    public Tile(Type type, int x, int y, HashMap<String, BufferedImage> images) {
        this.images = images;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics g, int offsetY) {
        g.setColor(Color.decode("#BDBDBD"));
        int X = x*size;
        int Y = y*size + offsetY;
        g.fillRect(X,Y,size, size);
        String image = "";
        switch(type) {
            case FLAGGED:
                if(reveal) image = "openTile";
                else image = "flagTile";
                break;
            case FLAGGEDBOMB:
                if(reveal) image = "bombInactiveTile";
                else image = "flagTile";
                break;
            case BOMB:
                image = "mineTile";
                break;
            case COVEREDBOMB:
                if(reveal) image = "mineInactiveTile";
                else image = "coveredTile";
                break;
            case COVERED:
                if(reveal) image = "openTile";
                else image = "coveredTile";
                break;
            case NUMBER:
                image = Integer.toString(value);
                break;
            case OPEN:
                image = "openTile";
                break;
        }
        g.drawImage(GetImage(image), X, Y, null);
    }

    public void SetType(Type t) {
        type = t;
    }

    public Type GetType() {
        return type;
    }

    public int GetX() { return x; }
    public int GetY() { return y; }

    public void SetValue(int val) {
        value = val;
    }

    public void Reveal() {
        reveal = true;
    }

    private BufferedImage GetImage(String name) {
        return images.get(name + ".png");
    }
}
