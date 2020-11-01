package com.vetolib.subscriptionRequests;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCell extends ListCell<String> {
    HBox hbox = new HBox();
    Label veterinaryLabel = new Label("");
    Pane pane = new Pane();
    Button delButton = new Button("Refuser");
    Button okButton = new Button("Accepter");

    SubscriptionRequestsController subscriptionRequestsController = new SubscriptionRequestsController();

    public XCell() {
        super();
        hbox.getChildren().addAll(veterinaryLabel, pane, delButton, okButton);
        HBox.setHgrow(pane, Priority.ALWAYS);
        //delButton.setOnAction(event -> getListView().getItems().remove(getItem()));
        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    subscriptionRequestsController.deleteSubscriptionRequest(getNOrdinal());
                    getListView().getItems().remove(getItem());
                } catch (IOException | InterruptedException CreationException) {
                    CreationException.printStackTrace();
                }
            }
        });
        //okButton.setOnAction(event -> getListView().getItems().remove(getItem()));
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    subscriptionRequestsController.createNewVeterinary(getNOrdinal());
                    getListView().getItems().remove(getItem());
                } catch (IOException | InterruptedException CreationException) {
                    CreationException.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            veterinaryLabel.setText(item);
            setGraphic(hbox);
        }
    }

    public int getNOrdinal(){
        int nordinal = 0;
        String veterinaryDetails = veterinaryLabel.getText();
        Pattern pattern = Pattern.compile("ordinal : (.*?)Contact", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(veterinaryDetails);
        while (matcher.find()) {
            String nordinalString = matcher.group(1).replaceAll("[\\\r\\\n]+","");
            nordinal = Integer.parseInt(nordinalString);
        }
        return nordinal;
    }


}