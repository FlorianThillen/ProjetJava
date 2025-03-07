package BusinessPackage;

import DataAccesPackage.MemberDataAccess;
import DataAccesPackage.SubscriptionDataAccess;
import ExceptionsPackage.ConnectionException;
import ExceptionsPackage.ExistenceException;
import ModelsPackage.MemberModel;
import ModelsPackage.SubscriptionModel;

import java.util.Vector;

public class SubscriptionBusiness {
    private MemberDataAccess memberDataAccess = new MemberDataAccess();
    private SubscriptionDataAccess subscriptionDataAccess = new SubscriptionDataAccess();

    public Vector<SubscriptionModel> getSubscriptions(java.util.Date startDate, java.util.Date endDate) throws ConnectionException, ExistenceException {
        return subscriptionDataAccess.getSubscriptions(DateConverter.utilDateToSqlDate(startDate), DateConverter.utilDateToSqlDate(endDate));
    }
    public Vector<MemberModel> getSubscribersInfo(String ownerNN) throws ConnectionException, ExistenceException {
        Vector<Integer> subscribersCardNb = subscriptionDataAccess.getCards(ownerNN);
        Vector<MemberModel> subscribers = new Vector<>();
        for (Integer cardNb: subscribersCardNb) {
            subscribers.add(memberDataAccess.getMemberByCard(cardNb));
        }
        return subscribers;
    }
    public int getPrice(String ownerNN) throws ConnectionException {
        return subscriptionDataAccess.getPrice(ownerNN);
    }

}
