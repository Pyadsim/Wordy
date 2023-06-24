import WordyApp.Wordy;
import WordyApp.WordyPackage.CriteriaNotMetException;
import WordyApp.WordyPackage.InvalidWordException;
import WordyApp.WordyPackage.WordTooShortException;
import sun.plugin2.message.JavaObjectOpMessage;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordyPlayGUI extends JFrame{
    private JLabel play;
    private JTextField inputWord;
    private JButton submitWordButton;
    private JLabel letters;
    private JPanel showLetters;
    private JButton quitButton;
    private JLabel response;
    private JLabel timerCntr;
    private JLabel timerLabel;
    private JPanel playPanel;
    private JLabel scoredWins;
    private int wins;
    private volatile String enteredString = inputWord.getText();
    private volatile boolean stopThread = false;
    private boolean conditionChanged = false; // Shared condition variable
    private final Object lock = new Object();
    private Timer timer;
    private ExecutorService timerExec;
    private ExecutorService checkExec;
    private Future<?> timerFuture;
    private Future<?> checkFuture;
    private boolean isHost;

    public WordyPlayGUI(Wordy wordy, String username, int lobbyNum, int wins) {

        setTitle("Current user: " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(playPanel);
        setLocationRelativeTo(null);

        letters.setText(wordy.get_letters(lobbyNum));
        isHost = wordy.is_host(username, lobbyNum);
        this.wins = wins;
        wordy.update_round(1, lobbyNum);

        submitWordButton.addActionListener(e -> {
            // setting
            synchronized (lock) {
                enteredString = inputWord.getText();
                updateCondition();
            }// synchronization ends

        });

        quitButton.addActionListener(e -> {

            if (isHost)
                wordy.remove_lobby(lobbyNum);
            else
                wordy.remove_player_on_quit(lobbyNum);

            timer.cancel();
            stopCheckThread();
            setVisible(false);
            new WordyMenuGUI(wordy, username);

        });

        timerExec = Executors.newSingleThreadExecutor();
        timerFuture = timerExec.submit(() -> {
            timer(wordy, username, lobbyNum);// start timer
        });

        checkExec = Executors.newSingleThreadExecutor();
        checkFuture = checkExec.submit(() -> {
            check(wordy, username, lobbyNum);// check whether the submitted word is accepted
        });

        pack();
        setVisible(true);
    }// const WordyPlayGUI ends

    private void check(Wordy wordy, String username, int lobbyID) {

        Thread checkThread = new Thread(() -> {
            String previousString = "";
            synchronized (lock) {
                while (!stopThread) {
                    try {

                        lock.wait();// wait until the condition changes
                        if (!enteredString.equals(previousString)) {
                            wordy.verify_guess(enteredString, username, lobbyID);
                            previousString = enteredString;
                        } else {
                            response.setText("You just submitted this word.");
                        }// if-else ends

                    } catch (InvalidWordException iwe) {
                        response.setText(iwe.msg);
                    } catch (WordTooShortException wtse) {
                        response.setText(wtse.msg);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }// try-catch ends
                }// while ends
            }// synchronization ends
        });
        checkThread.start();

    }// method check ends

    // Method to update the condition and notify the thread
    private void updateCondition() {
        synchronized (lock) {
            conditionChanged = true;
            lock.notifyAll();
        }// synchronization ends
    }// method updateCondition ends

    private void stopCheckThread() {
        stopThread = true;
    }// method stopCheckThread ends

    private void timer(Wordy wordy, String username, int lobbyID) {

        timer = new Timer();

        TimerTask timerTask = new TimerTask() {

            int counter = 10;

            @Override
            public void run() {

                if (counter > 0) {
                    timerCntr.setText(String.valueOf(counter));
                    counter--;
                } else {
                    if (wins != 3) {
                        // start another round
                        wins += wordy.get_score(username, lobbyID);
                        scoredWins.setText(String.valueOf(wins));

                        // reassign letters after each round
                        if (isHost) {
                            wordy.reassign_letters(lobbyID, username);
                            wordy.update_round(0, lobbyID);
                            setVisible(false);
                            new WordyPlayGUI(wordy, username, lobbyID, wins);
                        } else {
                            setVisible(false);
                            new WordyPlayGUI(wordy, username, lobbyID, wins);
                        }// if ends


                    } else {
                        // game ends
                        setVisible(false);
                        stopCheckThread();
                        JOptionPane.showMessageDialog(null, "Game Over.");
                        new WordyMenuGUI(wordy, username);
                    }// if-else ends

                    timerFuture.cancel(true);
                    timerExec.shutdown();
                    checkFuture.cancel(true);
                    checkExec.shutdown();
                    timer.cancel();
                }// if-else ends

            }// method run ends
        };

        // Schedule the timer to run every second (1000 milliseconds)
        timer.schedule(timerTask, 0, 1000);

    }// method timer ends

}// class WordyPlayGUI ends
