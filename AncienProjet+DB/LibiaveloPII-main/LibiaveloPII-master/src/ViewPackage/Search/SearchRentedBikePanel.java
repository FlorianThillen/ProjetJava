package ViewPackage.Search;

import ControllerPackage.Controller;
import ModelsPackage.RentalModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Vector;

import static BusinessPackage.Constants.STATION_NB;

public class SearchRentedBikePanel extends JPanel {
    private final Controller controller;

    public SearchRentedBikePanel(Controller controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Choisissez la station dont vous voulez voir les locations");
        titlePanel.add(titleLabel);

        JPanel comboboxPanel = new JPanel();
        Vector<Integer> stationsNb = new Vector<>();
        for (int i = 0; i < STATION_NB; i++) {
            stationsNb.add(i + 1);
        }
        JComboBox<Integer> comboBox = new JComboBox<>(stationsNb);
        JButton confirmButton = new JButton("Confirmer");
        comboboxPanel.add(comboBox);
        comboboxPanel.add(confirmButton);

        JPanel rentPanel = new JPanel();

        add(titlePanel);
        add(comboboxPanel);
        add(rentPanel);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stationNb = (Integer) comboBox.getSelectedItem();
                rentPanel.removeAll();
                rentPanel.add(getTable(stationNb));
                rentPanel.repaint();
                rentPanel.revalidate();
            }
        });
    }
    private JScrollPane getTable(int stationNb){
        Vector<RentalModel> rents = controller.getRentsInfo(stationNb);
        Vector<String> columnsNames = new Vector<>();
        columnsNames.add("Numéro de carte");
        columnsNames.add("Numéro de vélo");
        columnsNames.add("Date de début");
        columnsNames.add("Date du retour");

        Vector<Vector<String>> rentsInfo = new Vector<>();
        for (RentalModel rent : rents) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(rent.getCard().getCardNumber()));
            row.add(String.valueOf(rent.getBike().getSerialNumber()));
            row.add(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(rent.getStartDate().toLocalDate()));
            row.add(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(rent.getReturnDate().toLocalDate()));
            rentsInfo.add(row);
        }
        return new JScrollPane(new JTable(rentsInfo, columnsNames));
    }
}
