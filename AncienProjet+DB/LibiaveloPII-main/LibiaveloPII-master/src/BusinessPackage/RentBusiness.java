package BusinessPackage;

import DataAccesPackage.RentDataAccess;
import ExceptionsPackage.ConnectionException;
import ExceptionsPackage.ExistenceException;
import ModelsPackage.RentalModel;

import java.util.*;

public class RentBusiness {
    private RentDataAccess rentDataAccess = new RentDataAccess();
    public Vector<RentalModel> getRentsForAStation(int stationNb) throws ConnectionException, ExistenceException {
        return rentDataAccess.getRentsForAStation(stationNb);
    }
    public LinkedHashMap<Integer, Integer> getStationsRents(Vector<Integer> selectedStations) throws ConnectionException {
        HashMap<Integer, Integer> unsortedStations = new HashMap<>();
        for (int stationNb : selectedStations) {
            unsortedStations.put(stationNb, rentDataAccess.getRentCountForStation(stationNb));
        }
        ArrayList<Map.Entry<Integer, Integer>> stationsList = new ArrayList<>(unsortedStations.entrySet());
        stationsList.sort(Map.Entry.comparingByValue());
        LinkedHashMap<Integer, Integer> sortedStations = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> station : stationsList) {
            sortedStations.put(station.getKey(), station.getValue());
        }
        return sortedStations;
    }
}
