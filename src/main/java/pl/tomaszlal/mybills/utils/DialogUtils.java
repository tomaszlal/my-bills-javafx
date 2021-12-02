package pl.tomaszlal.mybills.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {

    private static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static Optional<ButtonType> dialogConfirmationDelete(){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle(bundle.getString("confirm.delete"));
        exitAlert.setHeaderText(bundle.getString("query.delete"));
        Optional<ButtonType> buttonType = exitAlert.showAndWait();
        return buttonType;
    }

    public static void dialogError(String headError,String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error"));
        errorAlert.setHeaderText(headError);

        TextArea textArea = new TextArea(error);
        textArea.setEditable(false);
        errorAlert.getDialogPane().setContent(textArea);

//        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}
