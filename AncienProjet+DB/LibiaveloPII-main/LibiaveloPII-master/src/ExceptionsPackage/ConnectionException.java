package ExceptionsPackage;

public class ConnectionException extends Exception {
    private String error;

    public ConnectionException(String e) {
        this.error = e;
    }
    @Override
    public String toString() {
        return "Erreur de connexion : " + this.error;
    }
}
