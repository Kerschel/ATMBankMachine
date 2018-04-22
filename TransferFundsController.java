import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransferFundsController implements Initializable {

    @FXML
    private TextField destinationAccountNumber;

    @FXML
    private TextField amountToBeTransfered;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goBack(ActionEvent event) throws IOException {
        Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
        Scene bankScene = new Scene(bankpage);
        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }

    public void transferButtonClick(ActionEvent event){
        String name = this.destinationAccountNumber.getText();

        String amountString = this.amountToBeTransfered.getText();

        double amount = Double.parseDouble(amountString);

        System.out.println("Name: " + name);
        System.out.println("Amount: " + amountString);

        try{
            BankMain.remote.transferFunds(BankMain.getUser().getAccountNo(), name, amount);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setContentText("Transfer Successful");
            alert.showAndWait();
        }catch (InsufficientFundsException insufficientFundsException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText("Insufficient Funds in Account");
            alert.showAndWait();
        }catch (AccountNotFoundException accountNotFoundException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText("Could not find destination account");
            alert.showAndWait();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
