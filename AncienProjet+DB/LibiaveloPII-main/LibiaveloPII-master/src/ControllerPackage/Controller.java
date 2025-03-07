package ControllerPackage;

import BusinessPackage.*;
import DataAccesPackage.MemberDataAccess;
import ExceptionsPackage.*;
import ModelsPackage.*;
import ValidatorPackage.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {

    private MemberDataAccess memberDataAccess = new MemberDataAccess();
    private MemberBusiness memberBusiness = new MemberBusiness();
    private SubscriptionBusiness subscriptionBusiness = new SubscriptionBusiness();
    private RentBusiness rentBusiness = new RentBusiness();
    public Controller() {
    }

    public MemberModel getMember(String nationalNb) {
        try {
            return memberBusiness.getMember(nationalNb);
        } catch (ExistenceException existenceException) {
            JOptionPane.showMessageDialog(null, "Le numéro national ne correspond à aucun membre", "Erreur: Membre introuvable", JOptionPane.ERROR_MESSAGE);
        } catch (ConnectionException connectionException) {
            JOptionPane.showMessageDialog(null, "Membre introuvable", "Erreur de connection", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


    public void addMember(
            String nationalNumber,
            String lastName,
            String firstName,
            String birthDay,
            String email,
            String phoneNumber,
            boolean gotDiscount,
            char gender,
            String holderNN,
            String street,
            String streetNb,
            String locality,
            String postalCode
    ) {
        try {
            Validator.controlNationalNb(nationalNumber);
            Validator.controlDate(birthDay);
            Validator.controlName(lastName, 1, 50, "Nom");
            Validator.controlName(firstName, 1, 50, "Prénom");
            Validator.controlEmail(email);
            Validator.controlPhone(phoneNumber);
            MemberModel holder = Validator.controlHolder(holderNN, this);
            Validator.controlAddress(street, streetNb, locality, postalCode);
            LocalDate date = LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            AddressModel address = new AddressModel(street, locality, Integer.parseInt(postalCode), Integer.parseInt(streetNb));
            MemberModel newMember = new MemberModel(nationalNumber, lastName, firstName, date, email, phoneNumber, gotDiscount, gender, holder, address);
            memberBusiness.addMember(newMember);
            JOptionPane.showMessageDialog(null, "Membre ajouté avec succès", "Ajout d'un membre", JOptionPane.INFORMATION_MESSAGE);
        } catch (ValidatorException e) {
            JOptionPane.showMessageDialog(null, e, "Erreur formulaire invalide", JOptionPane.ERROR_MESSAGE);
        } catch (ConnectionException e) {
            JOptionPane.showMessageDialog(null, "Impossible d'ajouter le membre", "Erreur ajout du membre", JOptionPane.ERROR_MESSAGE);
        } catch (ExistenceException e) {
            JOptionPane.showMessageDialog(null, "Le membre existe déjà", "Erreur ajout du membre", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<MemberModel> getMemberList() {
        try {
            return memberBusiness.getMemberList();
        } catch (ConnectionException | ExistenceException e) {
            JOptionPane.showMessageDialog(null, "Liste des membres introuvable", "Erreur liste des membres", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public void deleteMemberNN(String nationalNb){
        try {
            Validator.controlNationalNb(nationalNb);
            ArrayList<String> arrayNN = new ArrayList<String>();
            arrayNN.add(nationalNb);
            memberBusiness.deleteMember(arrayNN);
            JOptionPane.showMessageDialog(null, "Membre supprimé avec succès");
        } catch (ValidatorException validatorException) {
            JOptionPane.showMessageDialog(null, validatorException, "Champ invalide", JOptionPane.ERROR_MESSAGE);
        } catch (ExistenceException existenceException) {
            System.out.println(existenceException);
            JOptionPane.showMessageDialog(null, "Le numéro national ne correspond à aucun membre", "Erreur: Membre introuvable", JOptionPane.ERROR_MESSAGE);
        } catch (ConnectionException connectionException) {
            JOptionPane.showMessageDialog(null, "Membre introuvable", "Erreur de connection", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateMember(
            MemberModel member,
            String email,
            String phoneNumber,
            char gender,
            String holderNN,
            String street,
            String streetNb,
            String locality,
            String postalCode,
            String lastName,
            String firstName,
            String birthday
    ) {
        try {
            Validator.controlEmail(email);
            Validator.controlPhone(phoneNumber);
            Validator.controlDate(birthday);
            Validator.controlName(lastName, 1, 30, "nom");
            Validator.controlName(firstName, 1, 30, "prénom");
            MemberModel holder = Validator.controlHolder(holderNN, this);
            Validator.controlAddress(street, streetNb, locality, postalCode);
            AddressModel address = new AddressModel(street, locality, Integer.parseInt(postalCode), Integer.parseInt(streetNb));
            member.setHolder(holder);
            member.setEmail(email);
            member.setPhoneNumber(phoneNumber);
            member.setAddress(address);
            member.setGender(gender);
            member.setLastName(lastName);
            member.setFirstName(firstName);
            member.setBirthDay(DateConverter.localDateToSqlDate(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            memberBusiness.updateMember(member);
            JOptionPane.showMessageDialog(null, "Membre mise à jour avec succès");
        } catch (ValidatorException validatorException) {
            JOptionPane.showMessageDialog(null, validatorException, "Champ invalide", JOptionPane.ERROR_MESSAGE);
        } catch (ConnectionException connectionException) {
            JOptionPane.showMessageDialog(null, "Membre introuvable", "Erreur de connection", JOptionPane.ERROR_MESSAGE);
        } catch (ExistenceException | DateException e){
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du membre", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }
    public Vector<RentalModel> getStationRents(Integer stationNb) {
        try {
            return rentBusiness.getRentsForAStation(stationNb);
        } catch (ConnectionException | ExistenceException e) {
            JOptionPane.showMessageDialog(null, "Impossible d'importer les locations", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Vector<SubscriptionModel> getSubscriptions(Date startDate, Date endDate) {
        try {
            Validator.controlDates(startDate, endDate);
            return subscriptionBusiness.getSubscriptions(startDate, endDate);
        } catch (ConnectionException | ExistenceException e) {
            JOptionPane.showMessageDialog(null, "Abonnements introuvables", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (ValidatorException e) {
            JOptionPane.showMessageDialog(null, e, "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Vector<MemberModel> getOwners() {
        try {
            return memberBusiness.getOwners();
        } catch (ExistenceException | ConnectionException e) {
            JOptionPane.showMessageDialog(null, "Erreur chargement de la page", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Vector<MemberModel> getSubscribersFamily(String ownerNN) {
        try {
            return subscriptionBusiness.getSubscribersInfo(ownerNN);
        } catch (ConnectionException e){
            JOptionPane.showMessageDialog(null, "Erreur chargements des abonnements", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (ExistenceException e) {
            throw new RuntimeException(e);
        }
    }
    public int getSubPrice(String ownerNN) throws ConnectionException {
            return subscriptionBusiness.getPrice(ownerNN);
    }
    public LinkedHashMap<Integer, Integer> getStationsRents(Vector<Integer> selectedStations){
        try{
            return rentBusiness.getStationsRents(selectedStations);
        } catch (ConnectionException e){
            JOptionPane.showMessageDialog(null, "Erreur chargement des données");
            return null;
        }
    }
    public Vector<RentalModel> getRentsInfo(int stationNb){
        try{
            return rentBusiness.getRentsForAStation(stationNb);
        } catch(ConnectionException | ExistenceException e){
            JOptionPane.showMessageDialog(null, "Impossible d'importer les locations", "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
