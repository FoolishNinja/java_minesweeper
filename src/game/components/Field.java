package game.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Field extends Component {
    private ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
    private int offsetY = 33;
    private int bombCount = 40;
    private int flaggedBombs = 0;

    public Field() {
        Reset();
    }

    @Override
    public void Draw(Graphics g) {
        for(ArrayList<Tile> list : grid) {
            for(Tile t : list) {
                t.Draw(g, offsetY);
            }
        }
    }

    private HashMap<String,BufferedImage> LoadImages() {
        HashMap<String, BufferedImage> images = new HashMap<>();
        try {
            for(final File file : new File("src/assets").listFiles()) {
                String path = file.getPath();
                BufferedImage image = ImageIO.read(new File(path));
                images.put(file.getName(), image);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    public boolean FlagTile(int x, int y) {
        Tile t = GetTile(x,y);
        switch(t.GetType()) {
            case COVEREDBOMB:
                flaggedBombs++;
                t.SetType(Type.FLAGGEDBOMB);
                if(flaggedBombs == bombCount) return true;
                break;
            case FLAGGEDBOMB:
                flaggedBombs--;
                t.SetType(Type.COVEREDBOMB);
                break;
            case COVERED:
                t.SetType(Type.FLAGGED);
                break;
            case FLAGGED:
                t.SetType(Type.COVERED);
            break;
        }
        return false;
    }

    public boolean RecursiveOpenTiles(int x, int y) {
        Tile t = GetTile(x,y);
        ArrayList<Tile> neighbours = GetNeighbourTiles(t);
        switch(t.GetType()) {
            case COVEREDBOMB:
                t.SetType(Type.BOMB);
                return true;
            case COVERED:
                int bombCount = 0;
                for(Tile tile : neighbours) {
                    if(tile.GetType() == Type.COVEREDBOMB) bombCount++;
                }
                if(bombCount > 0) {
                    t.SetType(Type.NUMBER);
                    t.SetValue(bombCount);
                    return false;
                }
                t.SetType(Type.OPEN);
                ActivateNeighbours(neighbours);
                break;
        }
        return false;
    }

    public void RevealTiles() {
        for(ArrayList<Tile> row : grid) {
            for(Tile t : row) {
                t.Reveal();
            }
        }
    }

    private void ActivateNeighbours(ArrayList<Tile> neighbours) {
        for(Tile t : neighbours) {
            if(t.GetType() == Type.COVERED) {
                RecursiveOpenTiles(t.GetX(), t.GetY());
            }
        }
    }

    private ArrayList<Tile> GetNeighbourTiles(Tile t) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for(ArrayList<Tile> row : grid) {
            for(Tile tile : row) {
                int x = tile.GetX();
                int y = tile.GetY();
                int x2 = t.GetX();
                int y2 = t.GetY();
                if(x >= x2 - 1
                    && x <= x2 + 1
                    && y >= y2 - 1
                    && y <= y2 + 1
                    && (x != x2 || y != y2)
                ) tiles.add(tile);
            }
        }
        return tiles;
    }

    private Tile GetTile(int x, int y) {
        if(x < 21 && y < 21) return grid.get(y).get(x);
        return grid.get(YMouseToCor(y)).get(XMouseToCor(x));
    }

    public int RevealedTiles() {
        int count = 0;
        for(ArrayList<Tile> row : grid) {
            for(Tile tile : row) {
                if(tile.GetType() == Type.OPEN || tile.GetType() == Type.NUMBER) count++;
            }
        }
        return count;
    }

    private int XMouseToCor(int x) {
        return (int)Math.floor(x / 40);
    }

    private int YMouseToCor(int y) {
        return (int)Math.floor((y - offsetY * 2) / 40);
    }

    public void Reset() {
        HashMap<String,BufferedImage> images = LoadImages();
        grid = new ArrayList<>();
        for(int i = 0; i< 20; i++) grid.add(new ArrayList<>());

        ArrayList<Type> fields = new ArrayList<>();
        for(int i = 0; i < 380; i++) {
            if(i < bombCount) fields.add(Type.COVEREDBOMB);
            else fields.add(Type.COVERED);
        }
        Collections.shuffle(fields);

        for(int i = 0; i < 380; i++) {
            int row = (int)Math.floor(i / 20);
            grid.get(row).add(new Tile(fields.get(i),i % 20, row, images));
        }
    }
}
