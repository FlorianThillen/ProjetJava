package ViewPackage.Search;

import ControllerPackage.Controller;
import ExceptionsPackage.ConnectionException;
import ModelsPackage.MemberModel;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SearchFamilyPanel extends JPanel {
    private Controller controller;
    public SearchFamilyPanel(Controller controller, Container contentContainer){
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Veuillez choisir le titulaire de l'abonnement :");
        titlePanel.add(titleLabel);

        JPanel comboboxPanel = new JPanel();
        JComboBox<String> comboBox = getComboBox();
        JButton confirmButton = new JButton("Confirmer");
        JButton cancelButton = new JButton("Retour");
        comboboxPanel.add(comboBox);
        comboboxPanel.add(confirmButton);
        JPanel tablePanel = new JPanel();
        add(titlePanel);
        add(comboboxPanel);
        add(tablePanel);
        add(confirmButton);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberBox owner = (MemberBox) comboBox.getSelectedItem();
                tablePanel.removeAll();
                try {
                    tablePanel.add(getSubTable(owner.getNationalNb()));
                    tablePanel.repaint();
                    tablePanel.revalidate();
                } catch (ConnectionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new WelcomePanel());
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });
    }

    private JScrollPane getSubTable(String ownerNN) throws ConnectionException {
        Vector<MemberModel> subscribers = controller.getSubscribersFamily(ownerNN);
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Nom");
        columnNames.add("Prénom");
        columnNames.add("Numéro de carte");
        columnNames.add("Prix");
        String price = String.valueOf(controller.getSubPrice(ownerNN));

        Vector<Vector<String>> subscribersInfo = new Vector<>();
        for (MemberModel subscriber: subscribers) {
            Vector<String> row = new Vector<>();
            row.add(subscriber.getLastName());
            row.add(subscriber.getFirstName());
            row.add(String.valueOf(subscriber.getCard().getCardNumber()));
            row.add(price);
            subscribersInfo.add(row);
        }
        return new JScrollPane(new JTable(subscribersInfo, columnNames));
    }
    private JComboBox getComboBox(){
        Vector<MemberModel> owners = controller.getOwners();
        Vector<MemberBox> ownersString = new Vector<>();
        for (MemberModel owner: owners) {
            ownersString.add(new MemberBox(owner.getLastName(), owner.getFirstName(), owner.getNationalNumber()));
        }
        return new JComboBox<>(ownersString);
    }
    private class MemberBox {
        private String lastName, firstName, nationalNb;
        public MemberBox(String lastName, String firstName, String nationalNb) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.nationalNb = nationalNb;
        }
        public String getNationalNb(){return nationalNb;}
        @Override
        public String toString() {return lastName+" "+firstName+" "+nationalNb;}
    }
}
