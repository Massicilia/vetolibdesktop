package com.vetolib.subscriptionRequests;

import com.vetolib.menu.VetolibMenuController;
import com.vetolib.models.Subscriptionrequest;
import com.vetolib.models.Veterinary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionRequestsController {

    @FXML
    GridPane subscriptionPane;

    @FXML
    private ListView<String> lv;

    @FXML
    private VetolibMenuController vetolibMenuController;

    private ObservableList<String> veterinaries = FXCollections.observableArrayList();

    private ArrayList<Subscriptionrequest> subscriptionRequests = new ArrayList<Subscriptionrequest>();

    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public SubscriptionRequestsController() {
    }

    @FXML
    private void initialize() {
        vetolibMenuController = new VetolibMenuController();
        vetolibMenuController.setMenuBackground("Subscription");
        try {
            ObservableList<String> list = this.getRequests();
            lv.setItems(list);

            lv.prefHeightProperty().bind(subscriptionPane.heightProperty());
            lv.prefWidthProperty().bind(subscriptionPane.widthProperty());

            lv.setCellFactory(param -> new XCell());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ObservableList<String> getRequests()
            throws IOException, InterruptedException{

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/subscriptionrequest"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());
        JSONArray veterinariesJson = new JSONArray(response.body());
        for (int i = 0; i < veterinariesJson.length(); i++) {
            Subscriptionrequest subscriptionRequest =
                    new Subscriptionrequest(veterinariesJson.getJSONObject(i).getInt("idsubscriptionrequest"),
                            veterinariesJson.getJSONObject(i).getInt("nordinal"),
                            veterinariesJson.getJSONObject(i).getString("name"),
                            veterinariesJson.getJSONObject(i).getString("surname"),
                            veterinariesJson.getJSONObject(i).getString("adress"),
                            veterinariesJson.getJSONObject(i).getString("email"),
                            veterinariesJson.getJSONObject(i).getInt("phonenum"),
                            veterinariesJson.getJSONObject(i).getInt("clinic_nsiret"));
            subscriptionRequests.add(subscriptionRequest);
            veterinaries.add(subscriptionRequest.toString());
        }
        return veterinaries;
    }

    /**
     *
     * @param nOrdinal
     * @throws IOException
     * @throws InterruptedException
     */
    public void createNewVeterinary(int nOrdinal)
            throws IOException, InterruptedException{
        System.out.println(nOrdinal);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(nOrdinal))
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/veterinary/new"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print response body
        System.out.println(response.body());
    }

    /**
     *
     * @param nOrdinal
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteSubscriptionRequest(int nOrdinal)
            throws IOException, InterruptedException{
        System.out.println(nOrdinal);
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/subscriptionrequest/delete?nordinal="+nOrdinal))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // egt status code
        System.out.println(response.statusCode());

        // get response body
        System.out.println(response.body());
    }

    /**
     *
     * @param nOrdinal
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Veterinary getSubscriptionRequest(int nOrdinal)
            throws IOException, InterruptedException{
        System.out.println(nOrdinal);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/subscriptionrequest?nordinal="+nOrdinal))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // egt status code
        System.out.println(response.statusCode());

        // get response body
        System.out.println(response.body());

        //JSON OBJECT
        JSONObject subscriptionRequestObj = new JSONObject(response.body());
        Veterinary veterinary =
                new Veterinary(subscriptionRequestObj.getInt("nordinal"),
                        subscriptionRequestObj.getString("name"),
                        subscriptionRequestObj.getString("surname"),
                        subscriptionRequestObj.getString("adress"),
                        subscriptionRequestObj.getString("email"),
                        subscriptionRequestObj.getInt("phonenum"),
                        subscriptionRequestObj.getInt("clinic_nsiret"),
                        subscriptionRequestObj.getString("username"),
                        subscriptionRequestObj.getString("password") );
        return veterinary;
    }

    /**
     *
     * @param nOrdinal
     * @return
     */
    private static HttpRequest.BodyPublisher buildFormDataFromMap(int nOrdinal) {
        // map veterinary
        Map<Object, Object> nOrdinalMap = new HashMap<>();
        nOrdinalMap.put("nOrdinal", nOrdinal);

        StringBuilder builder = new StringBuilder();
        builder.append(URLEncoder.encode("nOrdinal", StandardCharsets.UTF_8));
        builder.append("=");
        builder.append(URLEncoder.encode(String.valueOf(nOrdinal), StandardCharsets.UTF_8));
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}