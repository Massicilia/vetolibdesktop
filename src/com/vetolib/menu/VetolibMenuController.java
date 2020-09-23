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
    public void subscriptionMenuClickedEvent(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(
                SubscriptionRequestsForm.class.getResource("SubscriptionRequests.fxml"));
        setNewForm(mouseEvent, loader);
    }

    /**
     * click on invoicing menu
     * @param mouseEvent
     */
    public void mouseClickedEventInvoicing(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(
                InvoicingForm.class.getResource("Invoicing.fxml"));
        setNewForm(mouseEvent, loader);
    }

    /**
     * set form
     * @param mouseEvent
     * @param loader
     */
    public void setNewForm(MouseEvent mouseEvent, FXMLLoader loader) {
        try{
            menuPane = loader.load();
            Scene scene = new Scene(menuPane, 800, 500);
            Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
