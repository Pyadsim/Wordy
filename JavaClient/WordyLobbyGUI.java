import WordyApp.Wordy;
import WordyApp.WordyPackage.CriteriaNotMetException;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordyLobbyGUI extends JFrame {

    private int lobbyNum;
    private JPanel mainPanel;
    private JButton cancelButton;
    private JLabel txtCnt;
    private boolean isHost;
    private Future<?> timerFuture;
    private ExecutorService timerExec;
    private Timer timer;

    public WordyLobbyGUI(Wordy wordy, String username) {

        setTitle("Current user: " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        lobbyNum = wordy.create_or_join_lobby(username);
        isHost = wordy.is_host(username, lobbyNum);
        if (!isHost)
            timer_checker(wordy, username);

        cancelButton.addActionListener(e -> {

            new WordyMenuGUI(wordy, username);
            setVisible(false);
            if (isHost)
                wordy.remove_lobby(lobbyNum);
            timer.cancel();
            timerFuture.cancel(true);
            timerExec.shutdown();

        });

        timerExec = Executors.newSingleThreadExecutor();
        timerFuture = timerExec.submit(() -> timer(wordy, username));
        if (isHost)
            wordy.update_round(1, lobbyNum);

        pack();
        setVisible(true);

    }// const WordyLobbyGUI ends

    private void timer(Wordy wordy, String username) {

        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            int counter = 10;

            @Override
            public void run() {

                if (counter > 0) {

                    if (isHost) {
                        txtCnt.setText(String.valueOf(counter));
                    } else {
                        txtCnt.setText("");
                    }// if-else ends

                    counter--;

                } else {
                    try {

                        wordy.update_round(0, lobbyNum);
                        wordy.can_start(lobbyNum);
                        setVisible(false);
                        new WordyPlayGUI(wordy, username, lobbyNum, 0);

                    } catch (CriteriaNotMetException e) {

                        JOptionPane.showMessageDialog(null, e.msg);
                        new WordyMenuGUI(wordy, username);
                        setVisible(false);

                    }// try-catch ends
                    timerFuture.cancel(true);
                    timerExec.shutdown();
                    timer.cancel();

                }// if-else ends

            }// method run ends
        };

        // Schedule the timer to run every second (1000 milliseconds)
        timer.schedule(timerTask, 0, 1000);

    }// method timer ends

    public void timer_checker(Wordy wordy, String username) {

        Timer timerChecker = new Timer();

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                boolean flag = wordy.get_round_stat(lobbyNum);

                if (!flag) {

                    try {

                        wordy.can_start(lobbyNum);
                        setVisible(false);
                        new WordyPlayGUI(wordy, username, lobbyNum, 0);

                    } catch (CriteriaNotMetException e) {

                        JOptionPane.showMessageDialog(null, e.msg);
                        new WordyMenuGUI(wordy, username);
                        setVisible(false);

                    }// try-catch ends
                    timerFuture.cancel(true);
                    timerExec.shutdown();
                    timer.cancel();
                    timerChecker.cancel();

                }// if ends

            }// method run ends
        };

        // Schedule the timer to run every second (100 milliseconds)
        timerChecker.schedule(timerTask, 0, 100);
    }// method timer_checker ends

}// class WordyLobbyGUI ends