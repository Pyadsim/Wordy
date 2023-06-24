import WordyApp.Wordy;
import WordyApp.WordyPackage.CriteriaNotMetException;
import WordyApp.WordyPackage.EmptyListException;
import WordyApp.WordyPackage.LengthiestMap;

import javax.swing.*;

public class WordyMenuGUI extends JFrame{

    private JLabel mainMenu;
    private JButton playButton;
    private JButton leaderboardButton;
    private JButton logoutButton;
    private JPanel menuPanel;
    private JLabel greeting;
    private JButton wordRankingButton;

    public WordyMenuGUI(Wordy wordy, String username) {

        setTitle("Current user: " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(menuPanel);
        setLocationRelativeTo(null);

        greeting.setText("Welcome " + username);

        playButton.addActionListener(e -> {

            setVisible(false);
            new WordyLobbyGUI(wordy, username);

        });

        leaderboardButton.addActionListener(e -> {
            new WordyLeaderboardGUI(wordy, username);
            setVisible(false);
        });

        wordRankingButton.addActionListener(e -> {
             new WordyLengthiestGUI(wordy, username);
             setVisible(false);
        });

        logoutButton.addActionListener(e -> {
            wordy.logout(username);
            setVisible(false);
            System.exit(0);
        });

        pack();
        setVisible(true);

    }// const WordyMenuGUI ends

}// class WordyMenuGUI ends
