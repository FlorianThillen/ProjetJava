package ViewPackage.CRUD;

import ControllerPackage.Controller;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMemberPanel extends JPanel {
    public AddMemberPanel(Container contentContainer, Controller controller){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Ajouter un Membre"));
        FormPanel formPanel = new FormPanel();
        JPanel buttonPanel = new JPanel();
        JButton confirm = new JButton("Confirmer");
        JButton cancel = new JButton("Retour");
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(confirm);
        buttonPanel.add(cancel);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addMember(
                    formPanel.getNationalNb(),
                    formPanel.getLastName(),
                    formPanel.getFirstName(),
                    formPanel.getBirthday(),
                    formPanel.getMail(),
                    formPanel.getPhoneNb(),
                    formPanel.hasDiscount(),
                    formPanel.getGender(),
                    formPanel.getHolderNN(),
                    formPanel.getStreet(),
                    formPanel.getStreetNb(),
                    formPanel.getLocality(),
                    formPanel.getPostalCode()
                );
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new WelcomePanel());
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        add(titlePanel);
        add(formPanel);
        add(buttonPanel);
    }


}
