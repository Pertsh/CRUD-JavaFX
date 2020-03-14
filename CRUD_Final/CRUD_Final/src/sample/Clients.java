package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class Clients {

    private SimpleStringProperty id;
    private SimpleStringProperty firstname;
    private SimpleStringProperty lastname;
    private Date dateofbirth;
    private SimpleStringProperty email;

    public Clients(String id,String firstname, String lastname, String date, String email) {
        this.id = new SimpleStringProperty(id);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.dateofbirth = Date.valueOf(date);
        this.email = new SimpleStringProperty(email);
    }

    public String getFirstname() { return firstname.get(); }
    public SimpleStringProperty firstnameProperty() { return firstname; }
    public String getLastname() { return lastname.get(); }
    public SimpleStringProperty lastnameProperty() { return lastname; }
    public String getEmail() { return email.get(); }
    public SimpleStringProperty emailProperty() { return email; }
    public String getId() {return id.get(); }
    public SimpleStringProperty idProperty() {return id; }
    public Date getDateofbirth(){ return dateofbirth;}

    public void setFirstname(String firstname) { this.firstname.set(firstname); }
    public void setLastname(String lastname) { this.lastname.set(lastname); }
    public void setEmail(String email) { this.email.set(email); }
    public void setId(String id) { this.id.set(id);}
    public void setDateofbirth(Date dateofbirth){this.dateofbirth=dateofbirth;}

}
