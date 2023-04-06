package game.components;

import java.awt.*;

public class ScoreDisplay extends Component {
    private int score = 0;

    public void Draw(Graphics g) {
        g.setColor(bgColor);
        int textWidth = Integer.toString(score).length() * 23;
        g.fillRect(game.Window.width - textWidth,0, textWidth,33);
        g.setColor(Color.RED);
        g.setFont(consolas);
        g.drawString(Integer.toString(score), game.Window.width - textWidth,30);
    }

    public void SetScore(int s) {
        score = s;
    }

    public void Reset() {
        score = 0;
    }
}
