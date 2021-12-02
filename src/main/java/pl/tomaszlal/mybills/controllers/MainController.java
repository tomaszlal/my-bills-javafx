package pl.tomaszlal.mybills.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.tomaszlal.mybills.utils.FxmlUtils;

public class MainController {
    public static final String LIST_OF_BILLS_FXML = "/fxml/listOfBills.fxml";

    @FXML
    private BorderPane borderPane;

    @FXML
    private TopMenuController topMenuButtonsController;  // to jest fx:id z pliku MainBorderPane.fxml <fx:include fx:id="topMenuButtons"  tylko dokładamy słowo Controller

    @FXML
    public void initialize(){
        setCenter(LIST_OF_BILLS_FXML);
        topMenuButtonsController.setMainController(this);


    }

    public void setCenter(String pathToFxml){
        borderPane.setCenter(FxmlUtils.fxmlLoader(pathToFxml));

    }


}
