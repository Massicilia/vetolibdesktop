package com.vetolib.models;

import javafx.beans.property.SimpleStringProperty;

public class Invoice {
    private String idInvoice;
    private SimpleStringProperty dateSimpleString;
    private String date;
    private int amountInvoice;
    private int veterinary_nOrdinal;
    private String paid;
    private String invoiceLink;

    public Invoice(String idInvoice, String date, int amountInvoice, int veterinary_nOrdinal) {
        this.idInvoice = idInvoice;
        this.date = date;
        this.amountInvoice = amountInvoice;
        this.veterinary_nOrdinal = veterinary_nOrdinal;
    }
    public Invoice(String Fdate, int FamountInvoice ){
        this.dateSimpleString=new SimpleStringProperty(Fdate);
        this.amountInvoice= new Integer(FamountInvoice);
    }
    public Invoice(String idInvoice, String date, int amountInvoice, String paid, String invoiceLink ){
        this.idInvoice = idInvoice;
        this.date= date;
        this.amountInvoice = amountInvoice;
        this.paid = paid;
        this.invoiceLink = invoiceLink;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
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

    public int getVeterinary_nOrdinal() {
        return veterinary_nOrdinal;
    }

    public void setVeterinary_nOrdinal(int veterinary_nOrdinal) {
        this.veterinary_nOrdinal = veterinary_nOrdinal;
    }

    public String getPaid() {
        return paid;
    }

    public String getInvoiceLink() {
        return invoiceLink;
    }

    public void setPaid(String paid) {
        paid = paid;
    }

    public void setInvoiceLink(String invoiceLink) {
        this.invoiceLink = invoiceLink;
    }

    @Override
    public String toString() {
        return "Invoice : " + getDate();
    }
}