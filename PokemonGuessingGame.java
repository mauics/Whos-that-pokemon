import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PokemonGuessingGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartScreen startScreen = new StartScreen();
                startScreen.setSize(300, 150);
                startScreen.setVisible(true);
            }
        });
    }
}

class StartScreen extends JFrame {
    private JButton startButton;

    public StartScreen() {
        super("Pokemon Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the start screen
                showGameScreen(); // Open the main game screen
            }
        });

        setLayout(new FlowLayout());
        add(startButton);
    }

    private void showGameScreen() {
        JFrame gameFrame = new JFrame("Pokemon Guessing Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PokemonGuessingGamePanel gamePanel = new PokemonGuessingGamePanel(gameFrame);
        gameFrame.getContentPane().add(gamePanel);
        gameFrame.setSize(720, 480);
        gameFrame.setVisible(true);
        
    }
}

class PokemonGuessingGamePanel extends JPanel {
    private JLabel label;
    private JTextField textField;
    private JButton checkButton;
    private JButton nextButton;
    private JLabel imageLabel;
    private JLabel timerLabel;
    private JLabel scoreLabel;

    private String[] pokemonNames = {"bulbasaur", "charmander", "squirtle", "pikachu", "jigglypuff", "caterpie", "rattata"};
    private String currentPokemon;
    private int score;
    private int timerSeconds;
    private Timer gameTimer;

    public PokemonGuessingGamePanel(JFrame parentFrame) {
        label = new JLabel("Who's that Pokemon?");
        textField = new JTextField(15);
        checkButton = new JButton("Check");
        nextButton = new JButton("Next");
        imageLabel = new JLabel();
        timerLabel = new JLabel("Timer: ");
        scoreLabel = new JLabel("Score: 0");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRandomPokemon();
            }
        });

        setLayout(new FlowLayout());

        add(label);
        add(textField);
        add(checkButton);
        add(nextButton);
        add(imageLabel);
        add(timerLabel);
        add(scoreLabel);

        setRandomPokemon();
        initializeTimer(parentFrame);
    }

    private void initializeTimer(JFrame parentFrame) {
        timerSeconds = 20; // Set the initial timer value (in seconds)
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer(parentFrame);
            }
        });
        gameTimer.start();
    }

    private void updateTimer(JFrame parentFrame) {
        timerSeconds--;

        if (timerSeconds < 0) {
            // Time's up, end the game
            gameTimer.stop();
            JOptionPane.showMessageDialog(parentFrame, "Game Over! Your final score is: " + score);
            resetGame();
        } else {
            updateTimerLabel();
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Timer: " + timerSeconds + "s");
    }

    private void setRandomPokemon() {
        Random random = new Random();
        currentPokemon = pokemonNames[random.nextInt(pokemonNames.length)];
        ImageIcon imageIcon = new ImageIcon(currentPokemon + ".jpg");
        imageLabel.setIcon(imageIcon);
        textField.setText(""); // Clear the text field when moving to the next Pokémon
        timerSeconds = 20; // Reset the timer for the next Pokémon
        updateTimerLabel();
    }

    private void checkAnswer() {
        String userGuess = textField.getText().toLowerCase();

        if (userGuess.equals(currentPokemon)) {
            JOptionPane.showMessageDialog(this, "Correct!");
            score++;
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! It's " + currentPokemon.toUpperCase() + ".");
        }

        updateScoreLabel();
        setRandomPokemon();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void resetGame() {
        score = 0;
        updateScoreLabel();
        initializeTimer((JFrame) getParent());
        setRandomPokemon();
    }
}


// https://pokemondb.net/pokedex/national sprites