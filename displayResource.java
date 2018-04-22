
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class displayResource {
    displayResource(String fxml) throws IOException {
        display(fxml);
    }
    public static Stage window;
    public  static Parent root;
    public static void display(String fxml) throws IOException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("KJ & GR Banking");
        root = FXMLLoader.load(displayResource.class.getResource(fxml));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void close(){
        window.close();
    }

}
