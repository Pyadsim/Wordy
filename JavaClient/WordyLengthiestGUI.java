import WordyApp.Wordy;
import WordyApp.WordyPackage.EmptyListException;
import WordyApp.WordyPackage.LeaderboardsMap;
import WordyApp.WordyPackage.LengthiestMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WordyLengthiestGUI extends JFrame{
    private JPanel lengthiestPanel;
    private JLabel length;
    private JButton backBtn;
    private JTable table;
    private JLabel leadDesc;
    private JScrollPane scPane;

    public WordyLengthiestGUI(Wordy wordy, String username) {

        try {

            String ldbList = wordy.lengthiest();
            String[] ldbArr = ldbList.split(",");

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Players");
            model.addColumn("Word");

            for (int i = 0; i < ldbArr.length; i += 2) {

                String user = ldbArr[i];
                String word = ldbArr[i + 1];
                Object[] rowData = {user, word};
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

        backBtn.addActionListener(e -> {

            new WordyMenuGUI(wordy, username);
            setVisible(false);

        });

        pack();
        setVisible(true);

    }// const WordyLengthiestGUI ends
}// class WordyLengthiestGUI
