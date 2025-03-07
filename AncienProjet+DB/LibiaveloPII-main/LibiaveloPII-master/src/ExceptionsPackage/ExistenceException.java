package ExceptionsPackage;

public class ExistenceException extends Exception {
    private String type;
    public ExistenceException(String type){this.type = type;}
    @Override
    public String toString(){
        return "La donnée de type "+type+" n'existe pas dans la DB";
    }
}
