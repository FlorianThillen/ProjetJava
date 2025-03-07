package ModelsPackage;

import java.sql.Date;
import java.time.LocalDate;


public class MemberModel {
    private String nationalNumber;
    private String lastName;
    private String firstName;
    private LocalDate birthDay;
    private String email;
    private String phoneNumber;
    private boolean gotDiscount;
    private char gender;
    private MemberModel holder;
    private AddressModel address;
    private CardModel card;

    public MemberModel(String nationalNumber, String lastName, String firstName, LocalDate birthDay, String email, String phoneNumber, boolean gotDiscount, char gender, MemberModel holder, AddressModel address){
        this.nationalNumber = nationalNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gotDiscount = gotDiscount;
        this.gender = gender;
        this.holder = holder;
        this.address = address;
    }
    public MemberModel(){}
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay.toLocalDate();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setGotDiscount(boolean gotDiscount) {
        this.gotDiscount = gotDiscount;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public void setHolder(MemberModel holder) {
        this.holder = holder;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCard(CardModel card) {
        this.card = card;
    }

    public char getGender() {
        return gender;
    }

    public Date getBirthDay() {
        return Date.valueOf(birthDay);
    }
    public LocalDate getBirthDayLD(){
        return birthDay;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHolder() {
        if (holder != null) {
            return holder.getNationalNumber();
        } else {
            return nationalNumber ;
        }
    }

    public AddressModel getAddress() {
        return address;
    }

    public CardModel getCard() {
        return card;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getGotDiscount() {
        return gotDiscount;
    }
    public String getGenderName() {
        switch (gender){
            case 'm' : return "Homme";
            case 'f' : return "Femme";
            default : return "Autre";
        }
    }
}
