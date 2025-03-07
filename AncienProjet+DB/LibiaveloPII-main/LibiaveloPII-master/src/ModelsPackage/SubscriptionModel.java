package ModelsPackage;

import java.util.Date;

public class SubscriptionModel {
    private String ownerNN;
    private double price;
    private Date date;
    private boolean hasPayedCaution;
    private boolean isPayed;
    public SubscriptionModel(){ }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }


    public boolean getHasPayedCaution() {
        return hasPayedCaution;
    }

    public boolean getIsPayed() {
        return isPayed;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHasPayedCaution(boolean hasPayedCaution) {
        this.hasPayedCaution = hasPayedCaution;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }
    public String getOwnerNN(){return ownerNN;}
    public void setOwnerNN(String ownerNN){this.ownerNN = ownerNN;}
}
