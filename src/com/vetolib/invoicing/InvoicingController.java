package com.vetolib.invoicing;

import com.vetolib.menu.VetolibMenuController;
import com.vetolib.models.Invoice;
import com.vetolib.models.Veterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InvoicingController {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox VBox;
    @FXML
    private TableView<Invoice> tableView;

    @FXML
    private VetolibMenuController vetolibMenuController = new VetolibMenuController();

    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    private ArrayList<Veterinary> veterinaries = new ArrayList<Veterinary>();

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public InvoicingController() {
        try {
            veterinaries = getAllVeterinaries();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        //vetolibMenuController = new VetolibMenuController();
        //vetolibMenuController.setMenuBackground("invoicing");

        searchButton.setOnAction(event -> {
            if(!invoices.isEmpty()){
                VBox.getChildren().remove(tableView);
            }
            if(searchField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vetolib");
                alert.setContentText("Veuillez entrer le nom du vétérinaire");

                alert.showAndWait();
            }
            else{
                search();
            }
        });
    }

    /**
     * search for invoices and set up the table
     */
    private void search() {
        // get Invoices with veterinary nordinal
        try {
            invoices = getInvoicesByVeterinary(getNordinalFromVeterinaryNameSurname());
            tableView = new TableView<>(invoices);
            tableView.setId("tableview");
            TableColumn<Invoice, String> moisCol = new TableColumn<>("Mois");
            moisCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("date"));
            TableColumn<Invoice, String> montantCol = new TableColumn<>("Montant (euros)");
            montantCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("amountInvoice"));

            tableView.getColumns().setAll(moisCol, montantCol);
            tableView.setPrefWidth(450);
            tableView.setPrefHeight(300);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            tableView.setItems(invoices);
            VBox.getChildren().add(tableView);
        }catch ( IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * GET NORDINAL FROM SEARCHTEXT VETERINARY NAME AND SURNAME
     * @return
     */
    private int getNordinalFromVeterinaryNameSurname(){
        String searchText = searchField.getText();
        int nordinal = 0;
        for(int i=0; i<veterinaries.size();i++){
            String name = veterinaries.get(i).getSurname() + " " + veterinaries.get(i).getName();
            if(name.toLowerCase().contains(searchText)){
                nordinal = veterinaries.get(i).getNordinal();
            }
        }
        return nordinal;
    }

    /**
     *  GET LIST VETERINARIES
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private ArrayList<Veterinary> getAllVeterinaries() throws IOException, InterruptedException{
        ArrayList<Veterinary> veterinaries = new ArrayList<Veterinary>();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/veterinary/all"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // egt status code
        //System.out.println(response.statusCode());

        // get response body
        //System.out.println(response.body());

        //JSON VETERINARIES
        JSONArray veterinariesJson = new JSONArray(response.body());
        for (int i = 0; i < veterinariesJson.length(); i++) {
            Veterinary veterinary = new Veterinary(veterinariesJson.getJSONObject(i).getInt("nordinal"),veterinariesJson.getJSONObject(i).getString("name"), veterinariesJson.getJSONObject(i).getString("surname"), veterinariesJson.getJSONObject(i).getString("adress"), veterinariesJson.getJSONObject(i).getString("email"), veterinariesJson.getJSONObject(i).getInt("phonenum"), veterinariesJson.getJSONObject(i).getInt("clinic_nsiret"));
            veterinaries.add(veterinary);
        }
        return veterinaries;
    }

    /**
     *
     * @param nordinal
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ObservableList<Invoice> getInvoicesByVeterinary(int nordinal) throws IOException, InterruptedException{

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/invoice/all?veterinary_nordinal="+nordinal))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // egt status code
        //System.out.println(response.statusCode());

        // get response body
        //System.out.println(response.body());

        //JSON Invoices
        JSONArray invoicesJson = new JSONArray(response.body());
        for (int i = 0; i < invoicesJson.length(); i++) {
            String date  = getDateFormat(invoicesJson.getJSONObject(i).getString("date"));
            Invoice invoice = new Invoice(invoicesJson.getJSONObject(i).getInt("idinvoice"),date, invoicesJson.getJSONObject(i).getInt("amountinvoice"), invoicesJson.getJSONObject(i).getInt("veterinary_nordinal"));
            invoices.add(invoice);
        }
        return invoices;
    }

    /**
     *
     * @param date
     * @return
     */
    private String getDateFormat(String date){
        String invoiceDate = null;
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date mydate = format.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(mydate);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            invoiceDate = getMonth(month)+" "+year;
        }catch (ParseException e){
            e.getMessage();
        }

        return invoiceDate;
    }

    /**
     *
     * @param monthInt
     * @return
     */
    private String getMonth(int monthInt){
        switch(monthInt) {
            case 0:
                return "Janvier";
            case 1:
                return "Février";
            case 2:
                return "Mars";
            case 3:
                return "Avril";
            case 4:
                return "Mai";
            case 5:
                return "Juin";
            case 6:
                return "Juillet";
            case 7:
                return "Août";
            case 8:
                return "Septembre";
            case 9:
                return "Octobre";
            case 10:
                return "Novembre";
            case 11:
                return "Décembre";
            default:
                return null;
        }
    }
}