import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyBird extends JFrame implements ActionListener, KeyListener {

    private Timer timer;
    private int birdY;
    private int birdVelocity;
    private int gravity;
    private int pipeX;
    private int pipeWidth;
    private int pipeHeight;
    private int gap;
    private boolean gameRunning;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        birdY = 300;
        birdVelocity = 0;
        gravity = 2;
        pipeX = 800;
        pipeWidth = 50;
        pipeHeight = 300;
        gap = 200;
        gameRunning = true;

        timer = new Timer(20, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            move();
            checkCollision();
            repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        // Draw bird
        g.setColor(Color.BLUE);
        g.fillRect(100, birdY, 50, 50);

        // Draw pipes
        g.setColor(Color.GREEN);
        g.fillRect(pipeX, 0, pipeWidth, pipeHeight);
        g.fillRect(pipeX, pipeHeight + gap, pipeWidth, getHeight() - pipeHeight - gap);

        if (gameRunning) {
            pipeX -= 5;
            if (pipeX + pipeWidth < 0) {
                pipeX = getWidth();
                pipeHeight = (int) (Math.random() * 300) + 50;
            }

            // Update bird position
            birdY += birdVelocity;
            birdVelocity += gravity;
        }
    }

    public void move() {
        if (birdY > 0 && birdY < getHeight() - 50) {
            birdY += birdVelocity;
            birdVelocity += gravity;
        } else {
            gameRunning = false;
        }
    }

    public void checkCollision() {
        if ((100 + 50 > pipeX && 100 < pipeX + pipeWidth) &&
                (birdY < pipeHeight || birdY + 50 > pipeHeight + gap)) {
            gameRunning = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameRunning) {
            birdVelocity = -20;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !gameRunning) {
            restartGame();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void restartGame() {
        birdY = 300;
        birdVelocity = 0;
        pipeX = 800;
        pipeHeight = 300;
        gameRunning = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlappyBird flappyBird = new FlappyBird();
            flappyBird.setVisible(true);
        });
    }
}
