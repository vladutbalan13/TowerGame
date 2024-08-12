import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import static java.lang.Math.abs;


public class TowerGame extends JPanel implements ActionListener, KeyListener {
    private int blockX = 200; // initial block position on x
    private int blockY = 50;  // initial block position on y
    private int blockWidth;
    private int blockHeight;
    private int blockSpeed = 10;
    private boolean falling = false;
    private final Image background; //background
    private boolean gameOver = false;
    private final JButton retryButton;
    private final JLabel scoreLabel;
    private int score = 0;
    private final Image blockImage; //box texture




    private final ArrayList <Rectangle> blocks = new ArrayList<>();
    //private Image background
    public TowerGame(int blockSize) {
        this.blockWidth = blockSize;
        this.blockHeight = blockSize;
        background = new ImageIcon("/Users/vladbalan/Java/TowerGame/background.jpeg").getImage();
        Timer timer = new Timer(30, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        blockImage = new ImageIcon("/Users/vladbalan/Java/TowerGame/crate.png").getImage();
        //score implementation
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(20, 20, 150, 30);
        add(scoreLabel);

        //retry button
        retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Arial", Font.BOLD, 20));
        retryButton.setVisible(false); // initially hidden
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                resetGame();
            }

        });
        // add button to panel
        setLayout(null);
        retryButton.setBounds(350, 300, 100, 50); // Positioning the button
        add(retryButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            add(scoreLabel); //add score tracker
        }
        //if game over
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over!", getWidth() / 2 - 150, getHeight() / 2);
            retryButton.setVisible(true);
            return; // stop drawing if game over
        }

        // draw box texture
        if (blockImage != null) {
            g.drawImage(blockImage, blockX, blockY, blockWidth, blockHeight, this);
        }

        if (blockImage != null) {
            for (Rectangle rect : blocks) {
                g.drawImage(blockImage, rect.x, rect.y, rect.width, rect.height, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver) {
            return;
        }
        if (falling) {
            blockY += abs(blockSpeed);
            if (blockY + blockHeight > getHeight() || isCollision()) {
                falling = false;
                if (isCollision()) {
                    // align the block on top of the last block
                    blockY = blocks.getLast().y - blockHeight;
                    blocks.add(new Rectangle(blockX, blockY, blockWidth, blockHeight));
                    score += 20; //increase score
                } else if (blocks.isEmpty() && blockY + blockHeight >= getHeight()) {
                    // if no blocks, the block has to hit the ground
                    blocks.add(new Rectangle(blockX, blockY, blockWidth, blockHeight));
                    score += 20; //increase score
                } else if (!blocks.isEmpty()) {
                    // check if the block misses the top block in the stack
                    if (blockX + blockWidth < blocks.getLast().x || blockX > blocks.getLast().x + blocks.getLast().width) {
                        gameOver = true; // game over if the block misses the stack
                    } else {
                        // align the block on top of the last block
                        blockY = blocks.getLast().y - blockHeight;
                        blocks.add(new Rectangle(blockX, blockY, blockWidth, blockHeight));
                        score += 20; //increase the score
                    }
                } else {
                    gameOver = true;
                }
                scoreLabel.setText("Score: " + score);
                blocks.add(new Rectangle(blockX, blockY, blockWidth, blockHeight));
                blockY = 50; // reset
                blockX = (int) (Math.random() * (getWidth() - blockWidth)); // random position on x
            }
        } else {
            blockX += blockSpeed;
            if (blockX > getWidth() - blockWidth || blockX < 0) {
                blockSpeed = -blockSpeed;
            }
        }
        repaint();
    }

    private boolean isCollision() {
        Rectangle currentBlock = new Rectangle(blockX, blockY, blockWidth, blockHeight);
        for (Rectangle _ : blocks) {
            if (currentBlock.intersects(blocks.getLast())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            falling = true;
        }

    }

    private void resetGame() {
        blocks.clear(); // clear blocks
        blockX = 200;   // reset block position
        blockY = 50;
        blockSpeed = 10;
        falling = false;
        gameOver = false;
        score = 0;
        scoreLabel.setText("Score: " + score); // to update it to 0
        retryButton.setVisible(false);
        repaint(); // redraw
    }
    @Override
    public void keyReleased(KeyEvent e) {}

}
