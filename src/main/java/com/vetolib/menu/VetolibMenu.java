package com.vetolib.menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class VetolibMenu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                VetolibMenu.class.getResource("/fxml/VetolibMenu.fxml"));
        Pane page = (Pane) loader.load();
        Scene scene = new Scene(page);

        stage.setTitle("Vetolib");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
