package ViewPackage.CRUD;

import ControllerPackage.Controller;
import ModelsPackage.MemberModel;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class MemberListPanel extends JPanel {

    public MemberListPanel(Container contentContainer, Controller controller){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Liste des membres"));
        JTable memberTable = getJTable(controller);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        JButton cancel = new JButton("Retour");
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
        add(scrollPane);
        add(cancel);
    }
    private JTable getJTable(Controller controller){
        ArrayList<MemberModel> memberList = controller.getMemberList();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Numéro national");
        columnNames.add("Nom");
        columnNames.add("Prénom");
        columnNames.add("genre");
        columnNames.add("Numéro de téléphone");
        columnNames.add("Réduction");
        columnNames.add("Date de naissance");
        columnNames.add("Adresse");
        columnNames.add("Numéro ational Parrain/Marraine");
        columnNames.add("numéro de carte");

        Vector<Vector<String>> rowData = new Vector<>();
        for (MemberModel member : memberList) {
            Vector<String> row = new Vector<>();
            row.add(member.getNationalNumber());
            row.add(member.getLastName());
            row.add(member.getFirstName());
            row.add(member.getGenderName());
            row.add(member.getPhoneNumber());
            row.add(member.getGotDiscount() ? "oui" : "non");
            row.add(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(member.getBirthDayLD()));
            row.add(member.getAddress().toString());
            row.add(member.getHolder() != null ? member.getHolder() : "");
            row.add(member.getCard() != null ? String.valueOf(member.getCard().getCardNumber()) : "");
            rowData.add(row);
        }
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        return new JTable(model);
    }
}
