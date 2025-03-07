package ViewPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
    public WelcomePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Bienvenue sur l'application LiBia Velo");
        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel();
        JLabel mainLabel = new JLabel("Naviguez dans les menus pour choisir votre action");
        mainPanel.add(mainLabel);

        JButton closeButton = new JButton("Fermer la page");

        add(titlePanel);
        add(mainLabel);
        add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


    }
}
