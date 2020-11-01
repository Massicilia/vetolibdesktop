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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONArray;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            // TextFields.bindAutoCompletion(searchField, veterinaries);
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
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
            invoices = getInvoicesByVeterinary(getNOrdinalFromVeterinaryNameSurname());
            tableView = new TableView<>(invoices);
            tableView.setId("tableview");

            TableColumn<Invoice, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("date"));

            TableColumn<Invoice, String> montantCol = new TableColumn<>("Montant (euros)");
            montantCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("amountInvoice"));

            TableColumn<Invoice, String> isPaidCol = new TableColumn<>("Etat");
            isPaidCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("paid"));

            TableColumn<Invoice, String> idInvoiceCol = new TableColumn<>("Numéro de facture");
            idInvoiceCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("idInvoice"));

            // TableColumn<Invoice, String> invoiceLinkCol = new TableColumn<>("Facture");
            // invoiceLinkCol.setCellValueFactory(new PropertyValueFactory("invoiceLink"));

            tableView.getColumns().setAll(idInvoiceCol, dateCol, montantCol, isPaidCol);
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
    private int getNOrdinalFromVeterinaryNameSurname(){
        String searchText = searchField.getText();
        int nOrdinal = 0;
        for (Veterinary veterinary : veterinaries) {
            String name = veterinary.getName() +
                    " " + veterinary.getSurname();
            if (name.toLowerCase().contains(searchText)) {
                nOrdinal = veterinary.getNordinal();
            }
        }
        return nOrdinal;
    }

    /**
     *  GET LIST VETERINARIES
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private ArrayList<Veterinary> getAllVeterinaries()
            throws IOException, InterruptedException{
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
        System.out.println(response.body());

        //JSON VETERINARIES
        JSONArray veterinariesJson = new JSONArray(response.body());
        for (int i = 0; i < veterinariesJson.length(); i++) {
            Veterinary veterinary =
                    new Veterinary(
                            veterinariesJson.getJSONObject(i).getInt("nordinal"),
                            veterinariesJson.getJSONObject(i).getString("name"),
                            veterinariesJson.getJSONObject(i).getString("surname"),
                            veterinariesJson.getJSONObject(i).getString("adress"),
                            veterinariesJson.getJSONObject(i).getString("email"),
                            veterinariesJson.getJSONObject(i).getInt("phonenum"),
                            veterinariesJson.getJSONObject(i).getInt("clinic_nsiret"));
            veterinaries.add(veterinary);
        }
        return veterinaries;
    }

    /**
     *
     * @param nOrdinal
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ObservableList<Invoice> getInvoicesByVeterinary(int nOrdinal)
            throws IOException, InterruptedException{

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/invoice?veterinary_nordinal="+nOrdinal))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // get response body
        Pattern pattern = Pattern.compile("object\":\"list\",\"data\":(.*),\"has_more");
        Matcher matcher = pattern.matcher(response.body());
        while(matcher.find()) {
            JSONArray invoicesJson = new JSONArray(matcher.group(1));
            for (int i = 0; i < invoicesJson.length(); i++) {
                String invoiceID = invoicesJson.getJSONObject(i).getString("id");
                String isPaid = invoicesJson.getJSONObject(i).getBoolean("paid")?"Payée":"Non payée";
                String invoiceLink = invoicesJson.getJSONObject(i).getString("invoice_pdf");
                int invoiceAmount = invoicesJson.getJSONObject(i).getInt("amount_due")/100;
                int timestamp = invoicesJson.getJSONObject(i).getInt("period_start");
                Date dateInvoice = new Date((long)timestamp*1000);
                LocalDate localDateInvoice = convertToLocalDateViaInstant(dateInvoice);
                String month = localDateInvoice.getMonth().toString();
                Invoice invoice =
                        new Invoice(
                                invoiceID,
                                month + " " + localDateInvoice.getYear(),
                                invoiceAmount,
                                isPaid,
                                invoiceLink);
                invoices.add(invoice);

            }
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

    /**
     *
     * @param monthInEnglish
     * @return
     */
    private String getMonthString(String monthInEnglish){
        switch(monthInEnglish) {
            case "january":
                return "Janvier";
            case "february":
                return "Février";
            case "march":
                return "Mars";
            case "april":
                return "Avril";
            case "may":
                return "Mai";
            case "june":
                return "Juin";
            case "july":
                return "Juillet";
            case "august":
                return "Août";
            case "september":
                return "Septembre";
            case "october":
                return "Octobre";
            case "november":
                return "Novembre";
            case "december":
                return "Décembre";
            default:
                return null;
        }
    }

    /**
     *
     * @param dateToConvert
     * @return
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}