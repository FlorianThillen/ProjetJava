package ViewPackage.CRUD;

import ControllerPackage.Controller;
import ModelsPackage.MemberModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchMemberPanel extends JPanel {
    public SearchMemberPanel(Container contentContainer, Controller controller){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Recherche d'un membre");
        titlePanel.add(titleLabel);

        JPanel contentPanel = new JPanel();
        JLabel contentLabel = new JLabel("Veuillez rechercher le membre que vous voulez modifier ou supprimer en entrant son numéro national.");
        contentPanel.add(contentLabel);

        JPanel nationaNbPanel = new JPanel();
        JLabel nationalNbLabel = new JLabel("Numéro National");
        JTextField tNationalNb = new JTextField(11);
        JButton deleteButton = new JButton("Supprimer");
        JButton updateButton = new JButton("Mettre à jour");
        nationaNbPanel.add(nationalNbLabel);
        nationaNbPanel.add(tNationalNb);
        nationaNbPanel.add(deleteButton);
        nationaNbPanel.add(updateButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        add(titlePanel);
        add(contentPanel);
        add(nationaNbPanel);
        add(buttonPanel);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteMemberNN(tNationalNb.getText());
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberModel member = controller.getMember(tNationalNb.getText());
                if(member != null ){
                    contentContainer.removeAll();
                    contentContainer.add(new UpdateMemberPanel(member, controller, contentContainer));
                    contentContainer.revalidate();
                    contentContainer.repaint();
                }
            }
        });
    }
}
