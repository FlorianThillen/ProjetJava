package BusinessPackage;

import DataAccesPackage.*;
import ExceptionsPackage.*;
import ModelsPackage.*;

import java.util.ArrayList;
import java.util.Vector;

public class MemberBusiness {
    private MemberDataAccess memberDataAccess = new MemberDataAccess();
    private SubscriptionDataAccess subscriptionDataAccess = new SubscriptionDataAccess();
    public MemberBusiness() {};
    public ArrayList<MemberModel> getMemberList() throws ConnectionException, ExistenceException {
        return memberDataAccess.getMemberList();
    }
    public MemberModel getMember(String nationalNb) throws ConnectionException, ExistenceException {
        return memberDataAccess.getMember(nationalNb);
    }
    public void deleteMember(ArrayList<String> nationalNumber) throws ConnectionException, ExistenceException {
        memberDataAccess.deleteMember(nationalNumber);
    }
    public void addMember(MemberModel newMember) throws ValidatorException, ConnectionException, ExistenceException {
        memberDataAccess.addMember(newMember);
    }
    public void updateMember(MemberModel updatedMember) throws DateException, ExistenceException, ConnectionException {
        memberDataAccess.updateMember(updatedMember);
    }
    public Vector<MemberModel> getOwners() throws ExistenceException, ConnectionException {
        Vector<MemberModel> owners = new Vector<>();
        Vector<String> ownersNN = subscriptionDataAccess.getOwnersNN();
        for (String ownerNN: ownersNN ) {
            owners.add(getMember(ownerNN));
        }
        return owners;
    }
}
