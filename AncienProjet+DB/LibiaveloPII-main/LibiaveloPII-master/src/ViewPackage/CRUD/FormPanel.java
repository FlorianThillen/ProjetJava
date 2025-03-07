package ViewPackage.CRUD;

import ModelsPackage.MemberModel;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormPanel extends JPanel {
    private JTextField tNationalNb, tLastName, tFirstName, tBirthday, tEmail, tPhone, tStreet, tStreetNb, tLocality, tPostalCode, tHoldeNN, tCard;

    private JCheckBox gotDiscountBox;
    private JRadioButton maleButton, femaleButton, otherButton;

    public FormPanel(MemberModel member){
        this();
        tNationalNb.setText(member.getNationalNumber());
        tNationalNb.setEnabled(false);
        tLastName.setText(member.getLastName());
        tFirstName.setText(member.getFirstName());
        tEmail.setText(member.getEmail());
        tBirthday.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH).format(member.getBirthDayLD()));
        tHoldeNN.setText(member.getHolder());
        tLocality.setText(member.getAddress().getLocality());
        tStreet.setText(member.getAddress().getStreet());
        tStreetNb.setText(String.valueOf(member.getAddress().getNumber()));
        tPostalCode.setText(String.valueOf(member.getAddress().getPostalCode()));
        tPhone.setText(member.getPhoneNumber());
        gotDiscountBox.setSelected(member.getGotDiscount());
        switch (member.getGender()){
            case 'm' :
                maleButton.setSelected(true);
                break;
            case 'f' :
                femaleButton.setSelected(true);
                maleButton.setSelected(false);
                break;
            default:
                otherButton.setSelected(true);
                maleButton.setSelected(false);
        }
    }
    public FormPanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel nationalNbPanel = new JPanel();
        JPanel lastNamePanel = new JPanel();
        JPanel firstNamePanel = new JPanel();
        JPanel birthdayPanel = new JPanel();
        JPanel emailPanel = new JPanel();
        JPanel phoneNbPanel = new JPanel();
        JPanel gotDiscountPanel = new JPanel();
        JPanel genderPanel = new JPanel();
        JPanel streetNbPanel = new JPanel();
        JPanel streetPanel = new JPanel();
        JPanel postalCodePanel = new JPanel();
        JPanel localityPanel = new JPanel();
        JPanel holderPanel = new JPanel();

        nationalNbPanel.add(new JLabel("Numéro national :"));
        lastNamePanel.add(new JLabel("Nom :"));
        firstNamePanel.add(new JLabel("Prénom :"));
        birthdayPanel.add(new JLabel("Date de naissance (jour/mois/année):"));
        emailPanel.add(new JLabel("adresse email :"));
        phoneNbPanel.add(new JLabel("Numéro de téléphone :"));
        gotDiscountPanel.add(new JLabel("A droit à une réduction :"));
        genderPanel.add(new JLabel("Genre :"));
        streetNbPanel.add(new JLabel("Numéro de rue"));
        streetPanel.add(new JLabel("Rue"));
        postalCodePanel.add(new JLabel("Code postal"));
        localityPanel.add(new JLabel("Localité"));
        holderPanel.add(new JLabel("Numéro national Parrain/Marraine"));

        tNationalNb = new JTextField(11);
        tLastName = new JTextField(20);
        tFirstName = new JTextField(20);
        tBirthday = new JTextField("jj-mm-aaaa");
        tEmail = new JTextField(30);
        tPhone = new JTextField(13);
        tStreet = new JTextField(20);
        tStreetNb = new JTextField(6);
        tLocality = new JTextField(20);
        tPostalCode = new JTextField(4);
        gotDiscountBox = new JCheckBox();
        tHoldeNN = new JTextField(11);
        ButtonGroup genderButtons = new ButtonGroup();
        maleButton = new JRadioButton("homme", true);
        femaleButton = new JRadioButton("femme");
        otherButton = new JRadioButton("autre");
        genderButtons.add(maleButton);
        genderButtons.add(femaleButton);
        genderButtons.add(otherButton);


        nationalNbPanel.add(tNationalNb);
        lastNamePanel.add(tLastName);
        firstNamePanel.add(tFirstName);
        birthdayPanel.add(tBirthday);
        emailPanel.add(tEmail);
        phoneNbPanel.add(tPhone);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);
        streetPanel.add(tStreet);
        streetNbPanel.add(tStreetNb);
        postalCodePanel.add(tPostalCode);
        localityPanel.add(tLocality);
        gotDiscountPanel.add(gotDiscountBox);
        holderPanel.add(tHoldeNN);

        this.add(nationalNbPanel);
        this.add(lastNamePanel);
        this.add(firstNamePanel);
        this.add(birthdayPanel);
        this.add(emailPanel);
        this.add(phoneNbPanel);
        this.add(genderPanel);
        this.add(localityPanel);
        this.add(postalCodePanel);
        this.add(streetPanel);
        this.add(streetNbPanel);
        this.add(gotDiscountPanel);
        this.add(holderPanel);
    }
    public String getNationalNb() {return tNationalNb.getText();}
    public String getLastName() {return tLastName.getText();}
    public String getFirstName() {return tFirstName.getText();}
    public String getBirthday() {return tBirthday.getText();}
    public String getMail() {return tEmail.getText();}
    public String getPhoneNb() {return tPhone.getText();}
    public String getHolderNN() {return tHoldeNN.getText();}
    public String getStreet() {return tStreet.getText();}
    public String getStreetNb() {return tStreetNb.getText();}
    public String getLocality() {return tLocality.getText();}
    public String getPostalCode() {return tPostalCode.getText();}
    public boolean hasDiscount() {return gotDiscountBox.isSelected();}
    public char getGender(){
        if(maleButton.isSelected()) return 'm';
        if(femaleButton.isSelected()) return 'f';
        else return 'o';
    }
}
