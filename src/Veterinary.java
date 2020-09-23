import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Veterinary {
    private SimpleIntegerProperty nordinal;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty adress;
    private SimpleStringProperty clinic;
    private SimpleStringProperty email;
    private SimpleStringProperty phonenum;

    public Veterinary(Integer nordinal, String name, String surname, String adress, String clinic, String email, String phonenum) {
        this.nordinal = new SimpleIntegerProperty(nordinal);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.adress = new SimpleStringProperty(adress);
        this.clinic = new SimpleStringProperty(clinic);
        this.email = new SimpleStringProperty(email);
        this.phonenum = new SimpleStringProperty(phonenum);
    }

    // getters, setters

    public int getNordinal() {
        return nordinal.get();
    }

    public SimpleIntegerProperty nordinalProperty() {
        return nordinal;
    }

    public void setNordinal(int nordinal) {
        this.nordinal.set(nordinal);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getClinic() {
        return clinic.get();
    }

    public SimpleStringProperty clinicProperty() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic.set(clinic);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhonenum() {
        return phonenum.get();
    }

    public SimpleStringProperty phonenumProperty() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum.set(phonenum);
    }

    @Override
    public String toString() {
        return "Veterinary{" +
                "nordinal=" + nordinal +
                ", name=" + name +
                ", surname=" + surname +
                ", adress=" + adress +
                ", clinic=" + clinic +
                ", email=" + email +
                ", phonenum=" + phonenum +
                '}';
    }
}
