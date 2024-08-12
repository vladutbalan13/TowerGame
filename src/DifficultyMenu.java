import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyMenu extends JFrame implements ActionListener {
    private final JButton easyButton;
    private final JButton mediumButton;
    private final JButton hardButton;

    public DifficultyMenu() {
        setTitle("Select Difficulty");
        setSize(300, 200);
        setLayout(new GridLayout(3, 1));

        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);

        add(easyButton);
        add(mediumButton);
        add(hardButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            startGame(50);  // larger block size
        } else if (e.getSource() == mediumButton) {
            startGame(30);  // medium block size
        } else if (e.getSource() == hardButton) {
            startGame(15);  // smaller block size
        }
    }

    private void startGame(int blockSize) {
        // Start the game with the selected block size
        TowerGame game = new TowerGame(blockSize);
        JFrame frame = new JFrame("Tower Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(game);
        frame.setVisible(true);
        this.dispose(); // Close the difficulty menu
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DifficultyMenu menu = new DifficultyMenu();
            menu.setVisible(true);
        });
    }
}
