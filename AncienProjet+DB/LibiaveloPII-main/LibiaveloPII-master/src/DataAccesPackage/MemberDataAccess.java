package DataAccesPackage;

import ModelsPackage.*;
import ExceptionsPackage.*;

import java.sql.*;
import java.util.ArrayList;

public class MemberDataAccess{

    public MemberModel getMember(String nationalNumber) throws ExistenceException, ConnectionException {
        try {
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT m.clientNumber, m.firstName, m.lastName, m.birthDate, m.phoneNumber, m.gender, m.email, " +
                    "m.street, m.streetNumber, m.locality, m.postalCode " +
                    "FROM velodb.member m " +
                    "WHERE m.nationalNumber = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, nationalNumber);

            ResultSet data = preparedStatement.executeQuery();
            if (data.next()) {
                MemberModel memberInformations = new MemberModel();
                memberInformations.setNationalNumber(nationalNumber);
                memberInformations.setLastName(data.getString("lastName"));
                memberInformations.setFirstName(data.getString("firstName"));
                memberInformations.setBirthDay(data.getDate("birthDate"));
                memberInformations.setPhoneNumber(data.getString("phoneNumber"));
                memberInformations.setGender(data.getString("gender").charAt(0));
                memberInformations.setEmail(data.getString("email"));

                AddressModel address = new AddressModel();
                address.setStreet(data.getString("street"));
                address.setNumber(data.getInt("streetNumber"));
                address.setLocality(data.getString("locality"));
                address.setPostalCode(data.getInt("postalCode"));
                memberInformations.setAddress(address);

                CardModel card = new CardModel();
                card.setCardNumber(data.getInt("clientNumber"));
                memberInformations.setCard(card);

                return memberInformations;
            } else {
                throw new ExistenceException("Erreur : aucun résultat ne correspond à votre recherche.");
            }
        } catch (SQLException sqlException) {
            throw new ConnectionException("Erreur de connexion : " + sqlException.getMessage());
        }
    }

    public void addAddress(Integer streetNumber, String street, String locality, Integer postalCode) throws ConnectionException, ExistenceException {
        Connection connection = SingletonConnection.getInstance();
        try {
            String sqlInstruction1 = "INSERT INTO velodb.address (streetNumber, street, locality, postalCode) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlInstruction1);
            statement.setInt(1, streetNumber);
            statement.setString(2, street);
            statement.setString(3, locality);
            statement.setInt(4, postalCode);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new ExistenceException("Erreur lors de l'ajout de l'adresse : " + sqlException.getMessage());
        }
    }

    public void deleteMember(ArrayList<String> nationalNumbers) throws ConnectionException, ExistenceException {
        Connection connection = SingletonConnection.getInstance();
        try {
            connection.setAutoCommit(false);

            for (String nationalNb : nationalNumbers) {
                MemberModel member = getMember(nationalNb);
                if (member == null) {
                    throw new ExistenceException("Member with national number " + nationalNb + " does not exist");
                }

                String deleteRentalQuery = "DELETE FROM velodb.rental WHERE clientNumber = ?";
                try (PreparedStatement deleteRentalStmt = connection.prepareStatement(deleteRentalQuery)) {
                    deleteRentalStmt.setInt(1, member.getCard().getCardNumber());
                    deleteRentalStmt.executeUpdate();
                }

                String deleteSubscriptionQuery = "DELETE FROM velodb.subscription WHERE clientNumber = ?";
                try (PreparedStatement deleteSubscriptionStmt = connection.prepareStatement(deleteSubscriptionQuery)) {
                    deleteSubscriptionStmt.setInt(1, member.getCard().getCardNumber());
                    deleteSubscriptionStmt.executeUpdate();
                }

                String deleteMemberQuery = "DELETE FROM velodb.member WHERE nationalNumber = ?";
                try (PreparedStatement deleteMemberStmt = connection.prepareStatement(deleteMemberQuery)) {
                    deleteMemberStmt.setString(1, nationalNb);
                    deleteMemberStmt.executeUpdate();
                }

                String deleteCardQuery = "DELETE FROM velodb.card WHERE clientNumber = ?";
                try (PreparedStatement deleteCardStmt = connection.prepareStatement(deleteCardQuery)) {
                    deleteCardStmt.setInt(1, member.getCard().getCardNumber());
                    deleteCardStmt.executeUpdate();
                }

                String deleteAddressQuery = "DELETE FROM velodb.address WHERE postalCode = ? AND street = ? AND streetNumber = ? AND locality = ?";
                try (PreparedStatement deleteAddressStmt = connection.prepareStatement(deleteAddressQuery)) {
                    deleteAddressStmt.setInt(1, member.getAddress().getPostalCode());
                    deleteAddressStmt.setString(2, member.getAddress().getStreet());
                    deleteAddressStmt.setInt(3, member.getAddress().getNumber());
                    deleteAddressStmt.setString(4, member.getAddress().getLocality());
                    deleteAddressStmt.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.err.println("Rollback failed: " + rollbackException.getMessage());
            }
            throw new ExistenceException("Erreur lors de la suppression du membre.");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to restore autocommit: " + e.getMessage());
            }
        }
    }

    public ArrayList<MemberModel> getMemberList() throws ExistenceException, ConnectionException {
        try {
            ArrayList<MemberModel> members = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT * FROM velodb.member";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);

            ResultSet data = preparedStatement.executeQuery();
            while (data.next()) {
                MemberModel member = new MemberModel();
                member.setNationalNumber(data.getString("nationalNumber"));
                member.setLastName(data.getString("lastName"));
                member.setFirstName(data.getString("firstName"));
                member.setGender(data.getString("gender").charAt(0));
                member.setPhoneNumber(data.getString("phoneNumber"));
                member.setBirthDay(data.getDate("birthDate"));
                member.setEmail(data.getString("email"));

                AddressModel address = new AddressModel();
                address.setPostalCode(data.getInt("postalCode"));
                address.setStreet(data.getString("street"));
                address.setNumber(data.getInt("streetNumber"));
                address.setLocality(data.getString("locality"));
                member.setAddress(address);

                CardModel card = new CardModel();
                card.setCardNumber(data.getInt("clientNumber"));
                member.setCard(card);

                member.setGotDiscount(data.getBoolean("gotDiscount"));

                members.add(member);
            }
            return members;
        } catch (SQLException sqlException) {
            throw new ExistenceException("Erreur : aucun membre enregistré.");
        }
    }
    public void addMember(MemberModel member) throws ConnectionException, ExistenceException {
        Connection connection = null;
        try {
            connection = SingletonConnection.getInstance();
            connection.setAutoCommit(false);

            String sqlCheckAddress = "SELECT COUNT(*) FROM velodb.address WHERE postalCode = ? AND street = ? AND streetNumber = ? AND locality = ?";
            PreparedStatement checkAddressStmt = connection.prepareStatement(sqlCheckAddress);
            checkAddressStmt.setInt(1, member.getAddress().getPostalCode());
            checkAddressStmt.setString(2, member.getAddress().getStreet());
            checkAddressStmt.setInt(3, member.getAddress().getNumber());
            checkAddressStmt.setString(4, member.getAddress().getLocality());
            ResultSet rs = checkAddressStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            checkAddressStmt.close();
            rs.close();

            if (count == 0) {
                String sqlInsertAddress = "INSERT INTO velodb.address (postalCode, street, streetNumber, locality) VALUES (?, ?, ?, ?)";
                PreparedStatement insertAddressStmt = connection.prepareStatement(sqlInsertAddress);
                insertAddressStmt.setInt(1, member.getAddress().getPostalCode());
                insertAddressStmt.setString(2, member.getAddress().getStreet());
                insertAddressStmt.setInt(3, member.getAddress().getNumber());
                insertAddressStmt.setString(4, member.getAddress().getLocality());
                insertAddressStmt.executeUpdate();
                insertAddressStmt.close();
            }


            String sqlInsertCard = "INSERT INTO velodb.card (clientNumber) VALUES (NULL)";
            PreparedStatement insertCardStmt = connection.prepareStatement(sqlInsertCard, Statement.RETURN_GENERATED_KEYS);
            insertCardStmt.executeUpdate();
            rs = insertCardStmt.getGeneratedKeys();
            rs.next();
            int clientNumber = rs.getInt(1);
            insertCardStmt.close();
            rs.close();


            String sqlInsertMember = "INSERT INTO velodb.member (nationalNumber, lastName, firstName, birthDate, email, phoneNumber, gender, locality, postalCode, street, streetNumber, gotDiscount, clientNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertMemberStmt = connection.prepareStatement(sqlInsertMember);

            insertMemberStmt.setString(1, member.getNationalNumber());
            insertMemberStmt.setString(2, member.getLastName());
            insertMemberStmt.setString(3, member.getFirstName());
            insertMemberStmt.setDate(4, member.getBirthDay());
            insertMemberStmt.setString(5, member.getEmail());
            insertMemberStmt.setString(6, member.getPhoneNumber());
            insertMemberStmt.setString(7, String.valueOf(member.getGender()));
            insertMemberStmt.setString(8, member.getAddress().getLocality());
            insertMemberStmt.setInt(9, member.getAddress().getPostalCode());
            insertMemberStmt.setString(10, member.getAddress().getStreet());
            insertMemberStmt.setInt(11, member.getAddress().getNumber());
            insertMemberStmt.setBoolean(12, member.getGotDiscount());
            insertMemberStmt.setInt(13, clientNumber);
            insertMemberStmt.executeUpdate();
            insertMemberStmt.close();

            connection.commit();

        } catch (SQLException sqlException) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            sqlException.printStackTrace();
            throw new ExistenceException("Erreur : ce membre existe déjà ou problème lors de l'insertion");
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAddress(String currentStreet, Integer currentStreetNumber, String currentLocality, Integer currentPostalCode, String newStreet, Integer newStreetNumber, String newLocality, Integer newPostalCode) throws ConnectionException, ExistenceException {
        Connection connection = null;
        try {
            connection = SingletonConnection.getInstance();
            connection.setAutoCommit(false);

            // Vérifiez si l'adresse actuelle existe
            String checkCurrentQuery = "SELECT * FROM velodb.address WHERE street = ? AND streetNumber = ? AND locality = ? AND postalCode = ?";
            PreparedStatement checkCurrentStatement = connection.prepareStatement(checkCurrentQuery);
            checkCurrentStatement.setString(1, currentStreet);
            checkCurrentStatement.setInt(2, currentStreetNumber);
            checkCurrentStatement.setString(3, currentLocality);
            checkCurrentStatement.setInt(4, currentPostalCode);
            ResultSet currentResultSet = checkCurrentStatement.executeQuery();

            if (currentResultSet.next()) {
                // Vérifiez si la nouvelle adresse existe
                String checkNewAddressQuery = "SELECT * FROM velodb.address WHERE street = ? AND streetNumber = ? AND locality = ? AND postalCode = ?";
                PreparedStatement checkNewAddressStatement = connection.prepareStatement(checkNewAddressQuery);
                checkNewAddressStatement.setString(1, newStreet);
                checkNewAddressStatement.setInt(2, newStreetNumber);
                checkNewAddressStatement.setString(3, newLocality);
                checkNewAddressStatement.setInt(4, newPostalCode);
                ResultSet newAddressResultSet = checkNewAddressStatement.executeQuery();

                if (!newAddressResultSet.next()) {
                    // Si la nouvelle adresse n'existe pas, l'insérer
                    String insertNewAddressQuery = "INSERT INTO velodb.address (street, streetNumber, locality, postalCode) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertNewAddressStatement = connection.prepareStatement(insertNewAddressQuery);
                    insertNewAddressStatement.setString(1, newStreet);
                    insertNewAddressStatement.setInt(2, newStreetNumber);
                    insertNewAddressStatement.setString(3, newLocality);
                    insertNewAddressStatement.setInt(4, newPostalCode);
                    insertNewAddressStatement.executeUpdate();
                }

                // Mettre à jour les membres avec la nouvelle adresse
                String updateMemberQuery = "UPDATE velodb.member SET street = ?, streetNumber = ?, postalCode = ?, locality = ? WHERE street = ? AND streetNumber = ? AND postalCode = ? AND locality = ?";
                PreparedStatement updateMemberStatement = connection.prepareStatement(updateMemberQuery);
                updateMemberStatement.setString(1, newStreet);
                updateMemberStatement.setInt(2, newStreetNumber);
                updateMemberStatement.setInt(3, newPostalCode);
                updateMemberStatement.setString(4, newLocality);
                updateMemberStatement.setString(5, currentStreet);
                updateMemberStatement.setInt(6, currentStreetNumber);
                updateMemberStatement.setInt(7, currentPostalCode);
                updateMemberStatement.setString(8, currentLocality);
                updateMemberStatement.executeUpdate();


                connection.commit();
            } else {
                throw new ExistenceException("Erreur : adresse actuelle non trouvée");
            }
        } catch (SQLException sqlException) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    System.out.println("Erreur lors du rollback : " + e.getMessage());
                }
            }
            System.out.println("Erreur : " + sqlException.getMessage());
            throw new ExistenceException("Erreur : mauvaises entrées rentrées");
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println("Erreur lors du réglage de l'auto-commit : " + e.getMessage());
                }
            }
        }
    }
    public void updateMember(MemberModel member) throws ConnectionException, ExistenceException {
        try {
            Connection connection = SingletonConnection.getInstance();
            MemberModel memberAddressCheck = getMember(member.getNationalNumber());

            if (memberAddressCheck == null) {
                throw new ExistenceException("Membre introuvable pour la mise à jour.");
            }

            AddressModel currentAddress = memberAddressCheck.getAddress();
            AddressModel newAddress = member.getAddress();

            // Vérifiez si l'adresse a changé
            if (!currentAddress.equals(newAddress)) {
                // Appelez la méthode updateAddress pour mettre à jour l'adresse
                updateAddress(
                        currentAddress.getStreet(),
                        currentAddress.getNumber(),
                        currentAddress.getLocality(),
                        currentAddress.getPostalCode(),
                        newAddress.getStreet(),
                        newAddress.getNumber(),
                        newAddress.getLocality(),
                        newAddress.getPostalCode()
                );
            }
            // Mise à jour des autres informations du membre
            String sqlInstruction = "UPDATE velodb.member SET lastName = ?, firstName = ?, birthDate = ?, email = ?, phoneNumber = ?, " +
                    "gender = ?, locality = ?, postalCode = ?, street = ?, streetNumber = ?, gotDiscount = ?, clientNumber = ? " +
                    "WHERE nationalNumber = ?";

            PreparedStatement statement = connection.prepareStatement(sqlInstruction);
            statement.setString(1, member.getLastName());
            statement.setString(2, member.getFirstName());
            statement.setDate(3, member.getBirthDay());
            statement.setString(4, member.getEmail());
            statement.setString(5, member.getPhoneNumber());
            statement.setString(6, Character.toString(member.getGender()));
            statement.setString(7, newAddress.getLocality());
            statement.setInt(8, newAddress.getPostalCode());
            statement.setString(9, newAddress.getStreet());
            statement.setInt(10, newAddress.getNumber());
            statement.setBoolean(11, member.getGotDiscount());
            statement.setInt(12, member.getCard().getCardNumber());
            statement.setString(13, member.getNationalNumber());

            statement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Erreur : " + sqlException.getMessage());
            throw new ExistenceException("Erreur : mauvaises valeurs entrées.");
        }
    }

    public MemberModel getMemberByCard(int cardNb) throws ExistenceException, ConnectionException {
        try {
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT m.nationalNumber, m.firstName, m.lastName, m.birthDate, m.phoneNumber, m.gender, m.email, " +
                    "m.street, m.streetNumber, m.locality, m.postalCode, m.gotDiscount " +
                    "FROM velodb.member m " +
                    "WHERE m.clientNumber = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, cardNb);

            ResultSet data = preparedStatement.executeQuery();
            if (data.next()) {
                MemberModel memberInformations = new MemberModel();
                memberInformations.setNationalNumber(data.getString("nationalNumber"));
                memberInformations.setLastName(data.getString("lastName"));
                memberInformations.setFirstName(data.getString("firstName"));
                memberInformations.setBirthDay(data.getDate("birthDate"));
                memberInformations.setPhoneNumber(data.getString("phoneNumber"));
                memberInformations.setGender(data.getString("gender").charAt(0));
                memberInformations.setEmail(data.getString("email"));
                memberInformations.setGotDiscount(data.getBoolean("gotDiscount"));

                AddressModel address = new AddressModel();
                address.setStreet(data.getString("street"));
                address.setNumber(data.getInt("streetNumber"));
                address.setLocality(data.getString("locality"));
                address.setPostalCode(data.getInt("postalCode"));
                memberInformations.setAddress(address);

                CardModel card = new CardModel();
                card.setCardNumber(cardNb);
                memberInformations.setCard(card);

                return memberInformations;
            } else {
                throw new ExistenceException("Erreur : aucun résultat ne correspond à votre recherche.");
            }
        } catch (SQLException sqlException) {
            throw new ConnectionException("Erreur de connexion : " + sqlException.getMessage());
        }
    }
}
