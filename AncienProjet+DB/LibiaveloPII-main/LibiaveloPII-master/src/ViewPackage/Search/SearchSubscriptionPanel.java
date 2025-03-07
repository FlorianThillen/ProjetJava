package ViewPackage.Search;

import BusinessPackage.DateConverter;
import ControllerPackage.Controller;
import ModelsPackage.MemberModel;
import ModelsPackage.SubscriptionModel;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class SearchSubscriptionPanel extends JPanel {
    private Controller controller;
    private JTextField startDateTxt, endDateTxt;
    public SearchSubscriptionPanel(Controller controller, Container contentContainer){
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Entrez les dates entre lesquelles vous voulez connaître les souscriptions.");
        titlePanel.add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JPanel startDatePanel = new JPanel();
        JPanel endDatePanel = new JPanel();
        startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.Y_AXIS));
        endDatePanel.setLayout(new BoxLayout(endDatePanel, BoxLayout.Y_AXIS));
        JLabel startDateLabel = new JLabel("Date de début (jj-mm-aaaa)");
        JLabel endDateLabel = new JLabel("Date de fin (jj-mm-aaaa)");

        startDateTxt = new JTextField("01-01-2010");
        endDateTxt = new JTextField(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(LocalDate.now()));

        startDatePanel.add(startDateLabel);
        endDatePanel.add(endDateLabel);
        startDatePanel.add(startDateTxt);
        endDatePanel.add(endDateTxt);

        inputPanel.add(startDatePanel);
        inputPanel.add(endDatePanel);

        JPanel tablePanel = new JPanel();

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        JButton cancelButton = new JButton("Retour");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane subTable = getSubTable();
                if(subTable != null) {
                    tablePanel.removeAll();
                    tablePanel.add(subTable);
                    contentContainer.revalidate();
                    contentContainer.repaint();
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
        add(titlePanel);
        add(inputPanel);
        add(tablePanel);
        add(buttonPanel);
    }

    private JScrollPane getSubTable(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = formatter.parse(startDateTxt.getText());
            Date endDate = formatter.parse(endDateTxt.getText());
            Vector<SubscriptionModel> subscriptions = controller.getSubscriptions(startDate, endDate);
            if(subscriptions != null){
                Vector<String> columnNames = new Vector<>();
                columnNames.add("Numéro de carte");
                columnNames.add("Date");
                columnNames.add("Nom");
                columnNames.add("Prénom");
                columnNames.add("Adresse");

                Vector<Vector<String>> subscriptionsInfo = new Vector<>();
                for (SubscriptionModel subscription : subscriptions){
                    Vector<String> row = new Vector<>();
                    MemberModel owner = controller.getMember(subscription.getOwnerNN());
                    row.add(String.valueOf(owner.getCard().getCardNumber()));
                    row.add(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(DateConverter.utilDateToLocalDate(subscription.getDate())));
                    row.add(owner.getLastName());
                    row.add(owner.getFirstName());
                    row.add(owner.getAddress().toString());
                    subscriptionsInfo.add(row);
                }
                return new JScrollPane(new JTable(subscriptionsInfo, columnNames));
            }
            JOptionPane.showMessageDialog(null, "Aucun abonnement trouvés entre ces dates", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (ParseException e){
            JOptionPane.showMessageDialog(null, "Les dates doivent suivre le format jj-mm-aaaa", "Mauvaise date", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}














