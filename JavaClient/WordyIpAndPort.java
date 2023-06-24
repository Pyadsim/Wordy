import WordyApp.Wordy;
import WordyApp.WordyHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.util.Properties;

public class WordyIpAndPort extends JFrame{
    private JTextField txtIP;
    private JPanel mainPanel;
    private JTextField txtPort;
    private JButton joinBtn;
    static Wordy wordyImp;

    public static void main(String[] args) {
        new WordyIpAndPort();
    }// method main ends

    public WordyIpAndPort() {

        setTitle("Setting up I P address and port");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);

        joinBtn.addActionListener(e -> {
            try{

                Properties props = new Properties();
                props.put("org.omg.CORBA.ORBInitialHost", txtIP.getText());
                props.put("org.omg.CORBA.ORBInitialPort", txtPort.getText());

                ORB orb = ORB.init((String[]) null, props);
                org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
                NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
                String name = "Wordy";
                wordyImp = WordyHelper.narrow(ncRef.resolve_str(name));

                JOptionPane.showMessageDialog(null, "Successfully Connected.");
                new WordyLoginGUI(wordyImp);

            } catch (Exception ex){
                setVisible(false);
                JOptionPane.showMessageDialog(null, "Something went wrong. Try again later.");
                new WordyIpAndPort();
            }// try-catch ends

            setVisible(false);
        });

        pack();
        setVisible(true);
    }// const WordyIpAndPort ends

}// class WordyIpAndPort ends
