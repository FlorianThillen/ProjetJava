package ModelsPackage;

public class AddressModel {
    private int postalCode;
    private String street;
    private int number;
    private String locality;

    public AddressModel(String street, String locality, int postalCode, int number){
        setLocality(locality);
        setNumber(number);
        setStreet(street);
        setPostalCode(postalCode);
    }
    public AddressModel(){}

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public int getPostalCode() {
        return postalCode;
    }


    public String getLocality() {
        return locality;
    }

    public String getStreet() {
        return street;
    }
    @Override
    public String toString(){
        return street+" "+number+",\n"+postalCode+" "+locality;
    }
}
