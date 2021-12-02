package pl.tomaszlal.mybills.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FxmlUtils {

    public static Pane fxmlLoader(String pathToFxml) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(pathToFxml));
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            // DialogUtils.dialogError(e.getMessage()); //Do obsłużenia przez okno dialogowe
        }
        return null;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.messages");
    }
}
