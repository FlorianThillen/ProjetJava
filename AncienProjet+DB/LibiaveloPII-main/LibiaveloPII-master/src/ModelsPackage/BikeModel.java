package ModelsPackage;

public class BikeModel {
    private int serialNumber;
    private StationModel station;
    public BikeModel(int serialNumber, StationModel station){
        setStation(station);
        setSerialNumber(serialNumber);
    }
    public BikeModel(int serialNumber){
        setSerialNumber(serialNumber);
    }
    public int getSerialNumber() {
        return serialNumber;
    }

    public StationModel getStation() {
        return station;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setStation(StationModel station) {
        this.station = station;
    }
}
