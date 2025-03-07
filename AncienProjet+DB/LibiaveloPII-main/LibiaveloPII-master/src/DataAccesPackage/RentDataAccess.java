package DataAccesPackage;

import ExceptionsPackage.*;
import ModelsPackage.BikeModel;
import ModelsPackage.CardModel;
import ModelsPackage.RentalModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;


public class RentDataAccess {
    public int getRentCountForStation(int stationNb) throws ConnectionException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int rentalCount = 0;

        try {
            connection = SingletonConnection.getInstance();

            String query = "SELECT COUNT(*) AS rental_count FROM velodb.Rental WHERE stationNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, stationNb);


            resultSet = statement.executeQuery();


            if (resultSet.next()) {
                rentalCount = resultSet.getInt("rental_count");
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new ConnectionException("Erreur de connexion à la base de données.");
        }

        return rentalCount;
    }
    public Vector<RentalModel> getRentsForAStation(int stationNb) throws ConnectionException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Vector<RentalModel> rentals = new Vector<>();

        try {
            connection = SingletonConnection.getInstance();
            String query = "SELECT r.clientNumber, r.serialNumber, r.startDate, r.returnDate " +
                    "FROM velodb.rental r " +
                    "WHERE r.stationNumber = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1, stationNb);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int clientNumber = resultSet.getInt("clientNumber");
                int serialNumber = resultSet.getInt("serialNumber");
                Date startDate = resultSet.getDate("startDate");
                Date returnDate = resultSet.getDate("returnDate");

                LocalDateTime startDateTime = startDate.toLocalDate().atStartOfDay();
                LocalDateTime returnDateTime = returnDate.toLocalDate().atStartOfDay();

                CardModel card = new CardModel(clientNumber);
                BikeModel bike = new BikeModel(serialNumber);

                RentalModel rental = new RentalModel(startDateTime, returnDateTime, bike, card);
                rentals.add(rental);
            }

        } catch (SQLException e) {
            System.out.println(e);
            throw new ConnectionException("Erreur de connexion à la base de données.");
        }
        return rentals;
    }
}
