package com.vetolib.login;

import com.vetolib.subscriptionRequests.SubscriptionRequestsForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.vetolib.utils.ScenesController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginFormController {

    private static HttpURLConnection connexion;

    @FXML
    private GridPane loginPane;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();
        if(emailField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Entrer votre email");
            return;
        }
        if(passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Erreur!",
                    "Entrer votre mot de passe");
            return;
        }

        try {
            int isConnected = login(emailField.getText(), passwordField.getText());
            if(isConnected == 200){
               // AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Identification réussie!",
               //         "Bienvenue " + emailField.getText());
                getMainPage(event);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int login(String email, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // email + password
        Map<Object, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("https://vetolibapi.herokuapp.com/api/v1/administrator/login"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print response body
        System.out.println(response.body());
        System.out.println(response.statusCode());
        return response.statusCode();
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private void getMainPage(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(
                    SubscriptionRequestsForm.class.getResource("SubscriptionRequests.fxml"));
            loginPane = loader.load();
            Scene scene = new Scene(loginPane, 800, 500);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}