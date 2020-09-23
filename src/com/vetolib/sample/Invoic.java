package com.vetolib.sample;

import javafx.beans.property.SimpleStringProperty;

public class Invoic {
    private static SimpleStringProperty month;
    private static int amount;
//constructor for item object

    public Invoic(String Fmonth, int Famount ){
        Invoic.month=new SimpleStringProperty(Fmonth);
        Invoic.amount= new Integer(Famount);
    }

    public static String getMonth() {
        return month.get();
    }

    public static void setMonth(String month) {
        Invoic.month.set(month.replace(" ","_"));
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Invoic.amount = amount;
    }

}