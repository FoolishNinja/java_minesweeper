package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JPanel{
    public static int width = 800;
    public static int height = 800;
    private static Engine engine = new Engine();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mine Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Window());
        frame.setSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width+20, height+50));
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                engine.MousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        frame.pack();
        frame.setVisible(true);
        engine.Init();
    }

    public void paintComponent(Graphics g) {
        engine.Draw(g);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
