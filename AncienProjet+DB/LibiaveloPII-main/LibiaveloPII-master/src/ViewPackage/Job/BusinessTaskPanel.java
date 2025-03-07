package ViewPackage.Job;

import ControllerPackage.Controller;
import ViewPackage.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import static BusinessPackage.Constants.STATION_NB;

public class BusinessTaskPanel extends JPanel {
    private Controller controller;
    private Vector<JCheckBox> checkBoxes;

    public BusinessTaskPanel(Container contentContainer, Controller controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Sélectionnez les stations dont vous voulez connaître l'affluence");
        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JPanel outputPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));

        JPanel stationPanel = getStationPanel();

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmer");
        JButton cancelButton = new JButton("Retour");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        inputPanel.add(stationPanel);
        inputPanel.add(buttonPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);

        add(titlePanel);
        add(mainPanel);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentContainer.removeAll();
                contentContainer.add(new WelcomePanel());
                contentContainer.revalidate();
                contentContainer.repaint();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputPanel.removeAll();
                outputPanel.add(getOutput());
                outputPanel.revalidate();
                outputPanel.repaint();
            }
        });
    }

    private JPanel getStationPanel() {
        checkBoxes = new Vector<>();
        JPanel stationsPanel = new JPanel();
        stationsPanel.setLayout(new BoxLayout(stationsPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < STATION_NB; i++) {
            JPanel stationPanel = new JPanel();
            stationPanel.add(new JLabel("Station numéro "+(i+1)));
            JCheckBox checkBox = new JCheckBox();
            checkBoxes.add(checkBox);
            stationPanel.add(checkBox);
            stationsPanel.add(stationPanel);
        }
        return stationsPanel;
    }

    private JPanel getOutput() {
        Vector<Integer> selectedStations = new Vector<>();
        for(int i = 0; i < STATION_NB; i++){
            if(checkBoxes.get(i).isSelected())
            selectedStations.add(i+1);
        }
        LinkedHashMap<Integer, Integer> stationsRents =  controller.getStationsRents(selectedStations);
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(new JLabel("Nombre de locations par station"));
        int sum = 0;
        for (Map.Entry<Integer, Integer> stationRents : stationsRents.entrySet() ) {
            JLabel stationLabel = new JLabel("Station numéro "+stationRents.getKey()+" : "+stationRents.getValue()+" vélo(s) loué(s)");
            outputPanel.add(stationLabel);
            sum += stationRents.getValue();
        }
        outputPanel.add(new JLabel("Total : "+sum+" vélo(s) loué(s)"));
        return outputPanel;
    }
}
