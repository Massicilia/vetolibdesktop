package com.vetolib.sample;
/*
import com.vetolib.invoicing.InvoicingController;
import com.vetolib.models.Invoice;
import com.vetolib.models.Veterinary;
import com.vetolib.sample.Invoic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


/**
 * Created by user on 02/06/2016.
 */
/*public class Main extends Application {
    TableView<Invoice> table;
    ObservableList<Invoice> itemList = FXCollections.observableArrayList();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome");
        primaryStage.setResizable(false);

        TableColumn<Invoice, String> monthColumn = new TableColumn<>("Month");
        monthColumn.setMinWidth(200);
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Invoice, String> amountColumn = new TableColumn<>("Amount");
        monthColumn.setMinWidth(200);
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("amountInvoice"));

        table = new TableView<>();
        table.getColumns().addAll(monthColumn, amountColumn);

        TextField query = new TextField();
        query.setPromptText("Search");


        Button searchButton = new Button("Search");

        searchButton.setOnAction(e -> {
            try {
                itemList = search();
                table.getItems().addAll(itemList);
            } catch (Exception ex) {
                System.out.println("Please select a an option from the box error");
            }

        });


        HBox searchBox = new HBox();
        searchBox.getChildren().addAll(query, searchButton);
        searchBox.setPadding(new Insets(10, 10, 10, 10));
        searchBox.setSpacing(10);

        VBox layout = new VBox();
        layout.getChildren().addAll(searchBox, table);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static ObservableList<Invoice> search(){

        String month=null;
        int amount=0;
        ObservableList<Invoice> items = FXCollections.observableArrayList();
        items.clear();
        try{
            InvoicingController invoicing = new InvoicingController();
            items = invoicing.getInvoicesByVeterinary(22277);

            for(Invoice i: items) {
                System.out.println(i.toString());
            }
            return items;
        }
        catch(IOException | InterruptedException e){
            System.out.print("error: "+e.getMessage());
        }
        return items;
    }

    public ObservableList<Invoice> getInvoicesByVeterinary(int nordinal) throws IOException, InterruptedException{
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/invoice/all?veterinary_nordinal="+nordinal))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // egt status code
        System.out.println(response.statusCode());

        // get response body
        System.out.println(response.body());

        //JSON Invoices
        JSONArray invoicesJson = new JSONArray(response.body());
        for (int i = 0; i < invoicesJson.length(); i++) {
            Invoice invoice = new Invoice(invoicesJson.getJSONObject(i).getInt("idinvoice"),invoicesJson.getJSONObject(i).getString("date"), invoicesJson.getJSONObject(i).getInt("amountinvoice"), invoicesJson.getJSONObject(i).getInt("veterinary_nordinal"));
            invoices.add(invoice);
        }
        return invoices;
    }

}*/
