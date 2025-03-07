package ModelsPackage;

import java.time.LocalDateTime;

public class RentalModel {
    private LocalDateTime startDate;
    private LocalDateTime returnDate;
    private StationModel station;
    private BikeModel bike;
    private CardModel card;
    public RentalModel(LocalDateTime startDate, LocalDateTime returnDate, BikeModel bike, CardModel card) {
        setBike(bike);
        setStartDate(startDate);
        setReturnDate(returnDate);
        setCard(card);
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setStation(StationModel station) {
        this.station = station;
    }


    public void setCard(CardModel card) {
        this.card = card;
    }

    public void setBike(BikeModel bike) {
        this.bike = bike;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public StationModel getStation() {
        return station;
    }

    public CardModel getCard() {
        return card;
    }


    public BikeModel getBike() {
        return bike;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}
