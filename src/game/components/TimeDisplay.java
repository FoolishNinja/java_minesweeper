package game.components;

import java.awt.*;

public class TimeDisplay extends Component {
    private int time = 0;
    private Thread t;

    public TimeDisplay() {
        StartTimer();
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0,0,Integer.toString(time).length() * 23,33);
        g.setColor(Color.RED);
        g.setFont(consolas);
        g.drawString(Integer.toString(time), 0,30);
    }

    private void StartTimer() {
        t = new Thread(() -> {
            boolean isRunning = true;
            while(!Thread.currentThread().isInterrupted() && isRunning) {
                try {
                    time++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    isRunning = false;
                }
            }
        });
        t.start();
    }

    public void StopTimer() {
        t.interrupt();
    }

    public void Reset() {
        time = 0;
        StartTimer();
    }
}
