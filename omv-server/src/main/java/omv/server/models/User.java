package omv.server.models;

public class User {
    public String email="";
    public String username="";
    public String password="";

    public void init (String email, String name, String password) {
        this.email = email;
        this.username = name;
        this.password = password;
    }
    public void validate() {
        // TODO
    }
    public String insertQuery() {
        String myquery = "INSERT INTO USER (email, username, password) VALUES ('"+this.email+"', '"+this.username+"', '"+this.password+"')";
        return myquery;
    }
}