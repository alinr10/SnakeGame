import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int snakeBody = 6;
    int appleX;
    int appleY;
    Random random;
    int eatingApple = 0;
    Timer timer;
    char location = 'R';
    Boolean running = true;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.addKeyListener(this);
        this.setFocusable(true);
        setBackground(Color.BLACK);
        random = new Random();
        timer = new Timer(DELAY, this);
        positionTheSnake();
        newApple();
        timer.start();
    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        g.setColor(Color.GREEN);
        g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
        // if(snakeBody<1) return;
        for (int i = 1; i < snakeBody; i++) {
            g.setColor(new Color(45, 180, 0));
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            System.out.println(x[i] + " " + y[i]);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + eatingApple, (SCREEN_WIDTH / 2) - 50, 20);
            if (!running) {
                g.setColor(Color.BLACK); // Örneğin siyah bir arka plan rengi kullanalım
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Score: " + eatingApple, (SCREEN_WIDTH / 2) - 50, 20);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("Game Over", (SCREEN_WIDTH / 2) - 105, SCREEN_HEIGHT / 2);
                timer.stop();
            }

        }

    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public void positionTheSnake() {
        int headX = SCREEN_WIDTH / 2;
        int headY = SCREEN_HEIGHT / 2;
        x[0] = headX;
        y[0] = headY;
        for (int i = 1; i < snakeBody; i++) {
            headX -= 25;
            x[i] = headX;
            y[i] = headY;
        }

    }

    public void move() {
        for (int i = snakeBody; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        if (location == 'U') {
            y[0] -= UNIT_SIZE;

        } else if (location == 'D') {
            y[0] += UNIT_SIZE;

        } else if (location == 'L') {
            x[0] -= UNIT_SIZE;

        } else if (location == 'R') {
            x[0] += UNIT_SIZE;

        }

    }

    public void checkCollision() {
        for (int i = snakeBody; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }

        }
        if (x[0] <= -UNIT_SIZE || x[0] >= SCREEN_WIDTH + UNIT_SIZE || y[0] <= -UNIT_SIZE || y[0] >= SCREEN_HEIGHT + UNIT_SIZE) {
            running = false;
        }
        if (x[0] == appleX && y[0] == appleY) {
            newApple();
            eatingApple++;
            snakeBody++;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        checkCollision();
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (location != 'D') {
                location = 'U';
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (location != 'U') {
                location = 'D';
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (location != 'R') {
                location = 'L';
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (location != 'L') {
                location = 'R';
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
