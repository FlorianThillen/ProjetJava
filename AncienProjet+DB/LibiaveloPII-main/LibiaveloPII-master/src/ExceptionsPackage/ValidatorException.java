package ExceptionsPackage;

public class ValidatorException extends Exception{
    private String errorType;
    private String champs;
    public ValidatorException(String errorType, String champs){
        this.errorType = errorType;
        this.champs = champs;
    }
    public String getErrorType(){return errorType;}
    @Override
    public String toString(){
        return "Erreur dans le remplissage du champ : "+champs+" : "+errorType;
    }
}
