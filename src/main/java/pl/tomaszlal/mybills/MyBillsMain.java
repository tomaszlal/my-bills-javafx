package pl.tomaszlal.mybills;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tomaszlal.mybills.database.dbutils.DbManager;
import pl.tomaszlal.mybills.utils.FxmlUtils;

import java.util.Locale;

public class MyBillsMain extends Application {

    public static final String MAIN_BORDER_PANE_FXML = "/fxml/MainBorderPane.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Locale.setDefault(new Locale("en"));
        DbManager.initDatabase();
        Parent root = FxmlUtils.fxmlLoader(MAIN_BORDER_PANE_FXML);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.name"));
        primaryStage.show();

    }
}
