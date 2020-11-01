package com.vetolib.menu;

import com.vetolib.invoicing.InvoicingForm;
import com.vetolib.subscriptionRequests.SubscriptionRequestsForm;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class VetolibMenuController {

    @FXML
    public ImageView icon;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Pane menuPane;
    @FXML
    private Menu invoicingMenu;
    @FXML
    private Menu subscriptionsMenu;

    public VetolibMenuController() {
    }

    @FXML
    private void initialize() {
        menuBar.prefWidthProperty().bind(menuPane.widthProperty());
    }

    /**
     * click on subscription menu
     * @param mouseEvent
     */
    public void subscriptionMenuClickedEvent(MouseEvent mouseEvent) throws IOException {
        menuPane = FXMLLoader.load(SubscriptionRequestsForm.class.getResource("/fxml/SubscriptionRequests.fxml"));
        setNewForm(mouseEvent, menuPane);
    }

    /**
     * click on invoicing menu
     * @param mouseEvent
     */
    public void mouseClickedEventInvoicing(MouseEvent mouseEvent) throws IOException {
        menuPane = FXMLLoader.load(InvoicingForm.class.getResource("/fxml/Invoicing.fxml"));
        setNewForm(mouseEvent, menuPane);
    }

    /**
     *
     * @param mouseEvent
     * @param menupane
     */
    public void setNewForm(MouseEvent mouseEvent, Pane menupane) {
        Scene scene = new Scene(menuPane, 800, 500);
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    /**
     * changing backgroundcolor of menus on click event
     * @param formType
     */
    public void setMenuBackground(String formType){
        System.out.println(formType);
        if(formType.equals("subscription")){
            invoicingMenu.setStyle("-fx-background-color: white");
            subscriptionsMenu.setStyle("-fx-background-color: #1095BE");
        }
        if(formType.equals("invoicing")){
            invoicingMenu.setStyle("-fx-background-color: #1095BE");
            subscriptionsMenu.setStyle("-fx-background-color: white");
        }
    }
}
