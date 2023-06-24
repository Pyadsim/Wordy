import WordyApp.Wordy;
import WordyApp.WordyPackage.*;

import javax.swing.*;
import java.awt.*;

public class WordyLoginGUI extends JFrame {
    private JPanel loginPanel;
    private JPasswordField passwordField;
    private JButton enterButton;
    private JTextField usernameField;

    public WordyLoginGUI(Wordy wordyImp) {
        setTitle("Wordy Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(loginPanel);
        setLocationRelativeTo(null);
        enterButton.setEnabled(true);

        enterButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and Password must not be empty!");
                } else {
                    setVisible(false);
                    wordyImp.login(username, password);
                    new WordyMenuGUI(wordyImp, username);
                    JOptionPane.showMessageDialog(null, "Successfully logged in.");
                }// if-else ends

            } catch (WrongPasswordOrUsernameException wpoue) {
                JOptionPane.showMessageDialog(null, wpoue.msg);
                new WordyLoginGUI(wordyImp);
            } catch (AccountDoesNotExistException adnee) {
                JOptionPane.showMessageDialog(null, adnee.msg);
                new WordyLoginGUI(wordyImp);
            } catch (AccountIsLoggedInException ailie) {
                JOptionPane.showMessageDialog(null, ailie.msg);
                new WordyLoginGUI(wordyImp);
            }// try-catch ends
        });

        pack();
        setVisible(true);

    }// const WordyLoginGUI ends

}// class WordyLoginGUI ends

