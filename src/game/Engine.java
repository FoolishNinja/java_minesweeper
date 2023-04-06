package game;

import game.components.Component;
import game.components.Field;
import game.components.ScoreDisplay;
import game.components.TimeDisplay;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Engine {
    private ArrayList<Component> components = new ArrayList<>();
    private Field field = new Field();
    private TimeDisplay timeDisplay = new TimeDisplay();
    private ScoreDisplay scoreDisplay = new ScoreDisplay();
    private boolean running = true;

    public void Init() {
        components.add(timeDisplay);
        components.add(field);
        components.add(scoreDisplay);
    }

    public void Draw(Graphics g) {
        g.clearRect(0,0,Window.width, Window.height);
        for (int i = 0; i < components.size(); i++) {
            components.get(i).Draw(g);
        }
    }

    public void MousePressed(MouseEvent e) {
        if(running) {
            switch (e.getButton()) {
                case 1:
                    if (field.RecursiveOpenTiles(e.getX(), e.getY())) LooseGame();
                    break;
                case 3:
                    if(field.FlagTile(e.getX(), e.getY())) WinGame();
                    break;
            }
            scoreDisplay.SetScore(field.RevealedTiles() * 100);
        }
    }

    private void LooseGame() {
        timeDisplay.StopTimer();
        field.RevealTiles();
        running = false;
        ResetGame();
    }

    private void WinGame() {
        timeDisplay.StopTimer();
        running = false;
        ResetGame();
    }

    private void ResetGame() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            field.Reset();
            timeDisplay.Reset();
            scoreDisplay.Reset();
            running = true;
        });
        t.start();
    }
}
