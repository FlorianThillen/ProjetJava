package DataAccesPackage;

import BusinessPackage.DateConverter;
import ExceptionsPackage.ConnectionException;
import ExceptionsPackage.ExistenceException;

import ModelsPackage.SubscriptionModel;
import java.sql.*;
import java.util.Vector;

public class SubscriptionDataAccess {
    public Vector<SubscriptionModel> getSubscriptions(java.sql.Date startDate, java.sql.Date endDate) throws ConnectionException, ExistenceException {
        Vector<SubscriptionModel> subscriptions = new Vector<>();
        Connection conn = SingletonConnection.getInstance();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT m.nationalNumber, s.price, s.date, s.cautionPayed, s.subscriptionPayed, " +
                    "m.lastName, m.firstName, a.streetNumber, a.street, a.postalCode, a.locality " +
                    "FROM Subscription s " +
                    "JOIN Card c ON s.clientNumber = c.clientNumber " +
                    "JOIN Member m ON c.clientNumber = m.clientNumber " +
                    "JOIN Address a ON m.postalCode = a.postalCode AND m.street = a.street AND m.streetNumber = a.streetNumber AND m.locality = a.locality " +
                    "WHERE s.date BETWEEN ? AND ?";

            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);

            rs = stmt.executeQuery();

            while (rs.next()) {
                SubscriptionModel subscription = new SubscriptionModel();

                subscription.setOwnerNN(rs.getString("nationalNumber"));
                subscription.setPrice(rs.getDouble("price"));

                java.sql.Date sqlDate = rs.getDate("date");
                java.util.Date utilDate = DateConverter.sqlDateToUtilDate(sqlDate);
                subscription.setDate(utilDate);

                subscription.setHasPayedCaution(rs.getBoolean("cautionPayed"));
                subscription.setIsPayed(rs.getBoolean("subscriptionPayed"));

                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            throw new ConnectionException("Erreur de connexion à la base de données");
        } catch (Exception e) {
            throw new ExistenceException("Erreur lors de la récupération des abonnements");
        }
        return subscriptions;
    }

    public Vector<String> getOwnersNN() throws ConnectionException, ExistenceException {
        Vector<String> ownersNN = new Vector<>();
        Connection conn = SingletonConnection.getInstance();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT DISTINCT m.nationalNumber " +
                    "FROM Member m " +
                    "JOIN Card c ON m.clientNumber = c.clientNumber " +
                    "JOIN Subscription s ON c.clientNumber = s.clientNumber";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ownersNN.add(rs.getString("nationalNumber"));
            }
        } catch (SQLException e) {
            throw new ConnectionException("Erreur de connexion à la base de données");
        } catch (Exception e) {
            throw new ExistenceException("Erreur lors de la récupération des numéros nationaux");
        }
        return ownersNN;
    }

    public Vector<Integer> getCards(String owner) throws ConnectionException {
        Vector<Integer> cards = new Vector<>();
        Connection conn = SingletonConnection.getInstance();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT c.clientNumber " +
                    "FROM Member m " +
                    "JOIN Card c ON m.clientNumber = c.clientNumber " +
                    "WHERE m.nationalNumber = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, owner);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cards.add(rs.getInt("clientNumber"));
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new ConnectionException("Erreur de connexion à la base de données");
        }
        return cards;
    }

    public int getPrice(String ownerNN) throws ConnectionException {
        int price = 0;
        Connection conn = SingletonConnection.getInstance();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT s.price " +
                    "FROM Subscription s " +
                    "JOIN Card c ON s.clientNumber = c.clientNumber " +
                    "JOIN Member m ON c.clientNumber = m.clientNumber " +
                    "WHERE m.nationalNumber = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ownerNN);
            rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return price;
    }
}
