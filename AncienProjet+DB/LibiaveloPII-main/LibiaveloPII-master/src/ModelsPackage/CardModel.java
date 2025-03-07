package ModelsPackage;

public class CardModel {
    private MemberModel owner;
    private int cardNumber;
    private SubscriptionModel subscription;
    public CardModel(MemberModel owner, int cardNumber, SubscriptionModel subscription){
        setOwner(owner);
        setCardNumber(cardNumber);
        setSubscription(subscription);
    }
    public CardModel(){}
    public CardModel(int cardNumber){
        setCardNumber(cardNumber);
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public MemberModel getOwner() {
        return owner;
    }

    public SubscriptionModel getSubscription() {
        return subscription;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setOwner(MemberModel owner) {
        this.owner = owner;
    }

    public void setSubscription(SubscriptionModel subscription) {
        this.subscription = subscription;
    }
}
