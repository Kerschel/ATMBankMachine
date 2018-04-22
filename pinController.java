import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class pinController implements Initializable{

    public Button backBtn;
    public TextField confirmpin;
    public TextField newpin;
    public Label msg1;
    public Label msg;

    public void changePin(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if(confirmpin.getText().equals(newpin.getText())) {
            Accounts user = BankMain.getUser();
            BankMain.remote.changePin(user.getName(), user.getAccountNo(), newpin.getText());
            msg.setVisible(true);
            msg1.setVisible(false);
        }
        else{
            msg1.setVisible(true);
            msg.setVisible(false);
        }
    }

    public void back(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {
        Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
        Scene bankScene = new Scene(bankpage);
        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msg1.setVisible(false);
        msg.setVisible(false);

    }
}
