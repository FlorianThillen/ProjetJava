package ModelsPackage;

public class StationModel {
    private int stationNumber;
    private String name;
    private int nbBikes;
    private AddressModel address;
    public StationModel(int stationNumber, String name, int nbBikes, AddressModel address){
        setName(name);
        setStationNumber(stationNumber);
        setNbBikes(nbBikes);
        setAddress(address);
    }
    public StationModel(int stationNumber){
        setStationNumber(stationNumber);
    }
    public StationModel(){}

    public AddressModel getAddress() {
        return address;
    }

    public int getNbBikes() {
        return nbBikes;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public String getName() {
        return name;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNbBikes(int nbBikes) {
        this.nbBikes = nbBikes;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }
}
