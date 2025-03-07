package ValidatorPackage;

import ControllerPackage.Controller;
import ExceptionsPackage.ValidatorException;
import ModelsPackage.MemberModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validator {
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$";
    private static  final String POSTALCODE_REGEX = "^[0-9]{4}$";
    private static final String STREET_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$";
    private static final String STREETNB_REGEX = "^[1-9][0-9]*$";
    public Validator(){}
    public static void controlNationalNb(String n) throws ValidatorException {
        if(n.isEmpty()) throw new ValidatorException("le champ est vide", "Numéro national");
        if(n.length() != 11) throw new ValidatorException("champ invalide", "Numéro national");
    }
    public static void controlDate(String input) throws ValidatorException {
        try{
            LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if(date.isAfter(LocalDate.now())) throw new ValidatorException("Trop tôt", "Date");
        } catch (DateTimeParseException exception) {
            throw new ValidatorException("Mauvais format", "Date");
        }
    }
    public static void controlName(String text, int min, int max, String champs) throws ValidatorException {
        if(text.length() < min || text.length() > max) throw new ValidatorException("L'entrée doit faire entre "+min+" et "+max+" caractères", champs);
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(text);
        if(!matcher.matches())throw new ValidatorException("Champs invalide", champs);
    }
    public static void controlEmail(String email) throws ValidatorException {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) throw new ValidatorException("Adresse mail invalide", "email");
    }
    public static void controlPhone(String phoneNb) throws ValidatorException {
        if(!phoneNb.isEmpty() && (phoneNb.charAt(0) != '0' || phoneNb.length() == 10 )) throw new ValidatorException("Numéro invalide", "Téléphone");
    }
    public static MemberModel controlHolder(String nationalNb, Controller controller) throws ValidatorException{
        try {
            controlNationalNb(nationalNb);
            return controller.getMember(nationalNb);
        } catch (ValidatorException e){
            if(e.getErrorType().equals("champ invalide")) throw new ValidatorException("champ invalide", "Parrain/Marraine");
        }
        return null;
    }
    public static void controlAddress(String street, String streetNb, String locality, String postalCode) throws ValidatorException {
        Pattern pattern = Pattern.compile(STREETNB_REGEX);
        if(!pattern.matcher(streetNb).matches()) throw new ValidatorException("Numéro invalide", "Numéro de rue");
        pattern = Pattern.compile(STREET_REGEX);
        if(!pattern.matcher(street).matches()) throw new ValidatorException("Rue invalide", "Rue");
        if(!pattern.matcher(locality).matches()) throw new ValidatorException("Localité invalide", "Localité");
        pattern = Pattern.compile(POSTALCODE_REGEX);
        if(!pattern.matcher(postalCode).matches() || Integer.parseInt(postalCode) < 1000) throw new ValidatorException("code postal invalide", "Code postal");
    }
    public static void controlDates(Date startDate, Date endDate) throws ValidatorException{
        if(startDate.after(endDate)) throw new ValidatorException("La date de début doit être antérieur à celle de fin", "Dates");
    }

}
