package com.vetolib.models;

import com.vetolib.sample.Invoic;
import javafx.beans.property.SimpleStringProperty;

public class Invoice {
    private int idinvoice;
    private SimpleStringProperty dateSimpleString;
    private String date;
    private int amountInvoice;
    private int veterinary_nordinal;

    public Invoice(int idinvoice, String date, int amountInvoice, int veterinary_nordinal) {
        this.idinvoice = idinvoice;
        this.date = date;
        this.amountInvoice = amountInvoice;
        this.veterinary_nordinal = veterinary_nordinal;
    }
    public Invoice(String Fdate, int FamountInvoice ){
        this.dateSimpleString=new SimpleStringProperty(Fdate);
        this.amountInvoice= new Integer(FamountInvoice);
    }

    public int getIdinvoice() {
        return idinvoice;
    }

    public void setIdinvoice(int idinvoice) {
        this.idinvoice = idinvoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmountInvoice() {
        return amountInvoice;
    }

    public void setAmountInvoice(int amountInvoice) {
        this.amountInvoice = amountInvoice;
    }

    public int getVeterinary_nordinal() {
        return veterinary_nordinal;
    }

    public void setVeterinary_nordinal(int veterinary_nordinal) {
        this.veterinary_nordinal = veterinary_nordinal;
    }

    @Override
    public String toString() {
        return "Invoice : " + getDate();
    }
}