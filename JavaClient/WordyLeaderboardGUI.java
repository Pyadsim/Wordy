import WordyApp.Wordy;
import WordyApp.WordyPackage.EmptyListException;
import WordyApp.WordyPackage.LeaderboardsMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WordyLeaderboardGUI extends JFrame{
    private JLabel lead;
    private JButton backButton;
    private JPanel lengthiestPanel;
    private JTable table;
    private JLabel leadDesc;
    private JScrollPane scPane;

    public WordyLeaderboardGUI(Wordy wordy, String username) {

        try {

            String ldbList = wordy.leaderboards();
            String[] ldbArr = ldbList.split(",");

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Players");
            model.addColumn("Number of Wins");

            for (int i = 0; i < ldbArr.length; i += 2) {

                String user = ldbArr[i];
                int wins = Integer.parseInt(ldbArr[i + 1]);
                Object[] rowData = {user, wins};
                model.addRow(rowData);

            }// for ends

            table.setModel(model);
            scPane.setViewportView(table);

        } catch (EmptyListException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }// try-catch ends

        setTitle("Highest Win Ranking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(lengthiestPanel);
        setLocationRelativeTo(null);

        backButton.addActionListener(e -> {

            new WordyMenuGUI(wordy, username);
            setVisible(false);

        });

        pack();
        setVisible(true);

    }// const WordyLeaderboardGUI ends

}// class WordyLeaderboardGUI ends
