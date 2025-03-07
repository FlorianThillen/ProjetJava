package ExceptionsPackage;

public class DateException extends Exception{
    private String wrongDate;
    public DateException(String wrongDate, String message){
        super(message);
        setWrongDate(wrongDate);
    }

    public void setWrongDate(String wrongDate) {
        this.wrongDate = wrongDate;
    }
}
