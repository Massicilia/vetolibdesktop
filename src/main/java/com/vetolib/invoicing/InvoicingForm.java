package com.vetolib.invoicing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InvoicingForm extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                InvoicingForm.class.getResource("/fxml/Invoicing.fxml"));
        GridPane page = (GridPane) loader.load();
        Scene scene = new Scene(page, 800, 500);

        primaryStage.setTitle("Vetolib");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
