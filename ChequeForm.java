import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChequeForm {
    public TextArea payto;
    public TextField amount;

    public static String pay;
    public static String amt;
    public void makeCheque(javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        pay = payto.getText();
        amt = amount.getText();
        Accounts user = BankMain.getUser();
        if(BankMain.remote.reduceBalance(user.getName(),user.getAccountNo(),Double.parseDouble(amt))) {
            new displayResource("FXML/PrintCheque.fxml");
            Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
            Scene bankScene = new Scene(bankpage);
            Stage bankstage = BankMain.getprimaryStage();
            bankstage.hide();
            bankstage.setScene(bankScene);
            bankstage.show();
        }
        else{
            new displayResource("FXML/errorMessage.fxml");
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
}
