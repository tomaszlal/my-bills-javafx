package pl.tomaszlal.mybills.controllers;

import javafx.fxml.FXML;

public class TopMenuController {

    public static final String LIST_OF_BILLS_FXML = "/fxml/listOfBills.fxml";
    public static final String ADD_BILLS_FXML = "/fxml/addBills.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/addCategory.fxml";

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    } // metoda do przekazanie kontroli nad kontrolerem do innego kontrolera

    @FXML
    private MainController mainController;  // przekazanie kontroli nad kontrolerem do innego kontrolera


    @FXML
    public void onActionButtonListOfBils() {
        mainController.setCenter(LIST_OF_BILLS_FXML);
    }

    @FXML
    public void onActionButtonAddBils() {
        mainController.setCenter(ADD_BILLS_FXML);
    }

    @FXML
    public void onActionButtonAddCategory() {
        mainController.setCenter(ADD_CATEGORY_FXML);
    }
}
