package com.vetolib.models;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Veterinary {
    private int nordinal;
    private String name;
    private String surname;
    private String adress;
    private String email;
    private int phonenum;
    private int clinic_nsiret;
    private String username;
    private String password;

    public Veterinary(int nordinal, String name, String surname, String adress, String email, int phonenum, int clinic_nsiret, String username, String password) {
        this.nordinal = nordinal;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.email = email;
        this.phonenum = phonenum;
        this.clinic_nsiret = clinic_nsiret;
        this.username = username;
        this.password = password;
    }

    public Veterinary(int nordinal, String name, String surname, String adress, String email, int phonenum, int clinic_nsiret) {
        this.nordinal = nordinal;
        this.name = name;
        this.surname = surname;
        this.adress = adress;
        this.email = email;
        this.phonenum = phonenum;
        this.clinic_nsiret = clinic_nsiret;
    }

    public int getNordinal() {
        return nordinal;
    }

    public void setNordinal(int nordinal) {
        this.nordinal = nordinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(int phonenum) {
        this.phonenum = phonenum;
    }

    public int getClinic_nsiret() {
        return clinic_nsiret;
    }

    public void setClinic_nsiret(int clinic_nsiret) {
        this.clinic_nsiret = clinic_nsiret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Veterinary{" +
                "nordinal=" + nordinal +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", adress='" + adress + '\'' +
                ", email='" + email + '\'' +
                ", phonenum=" + phonenum +
                ", clinic_nsiret=" + clinic_nsiret +
                '}';
    }
}
