package ViewPackage.CRUD;

import ControllerPackage.Controller;
import ModelsPackage.MemberModel;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateMemberPanel extends JPanel {
    public UpdateMemberPanel(MemberModel member, Controller controller, Container contentContainer){
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Mise à jour du membre"+member.getFirstName()+" "+member.getLastName());
        titlePanel.add(titleLabel);

        FormPanel formPanel = new FormPanel(member);

        JButton cancel = new JButton("Retour");
        JButton update = new JButton("Mettre à jour");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(update);
        buttonPanel.add(cancel);

        add(titlePanel);
        add(formPanel);
        add(buttonPanel);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new WelcomePanel());
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateMember(
                        member,
                        formPanel.getMail(),
                        formPanel.getPhoneNb(),
                        formPanel.getGender(),
                        formPanel.getHolderNN(),
                        formPanel.getStreet(),
                        formPanel.getStreetNb(),
                        formPanel.getLocality(),
                        formPanel.getPostalCode(),
                        formPanel.getLastName(),
                        formPanel.getFirstName(),
                        formPanel.getBirthday()
                );
            }
        });
    }
}
