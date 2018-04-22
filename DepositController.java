import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DepositController {
    public TextField amountValue;

    public void inputValue(ActionEvent event) {
        String buttonText = ((Button)event.getSource()).getText();

        addToAmount(buttonText);
    }

    private void addToAmount(String value){
        String amount = amountValue.getText();

        amount += value;

        amountValue.setText(amount);
    }

    public void returnToHomePage(){
        try{
            Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
            Scene bankScene = new Scene(bankpage);
            Stage bankstage = BankMain.getprimaryStage();
            bankstage.hide();
            bankstage.setScene(bankScene);
            bankstage.show();
        }catch (IOException exceptiion){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setContentText("Close application");
        }
    }

    public void returnToHomePageButtonClick(ActionEvent event){
        this.returnToHomePage();
    }

    public void depositCash(ActionEvent event) throws IOException, ClassNotFoundException, InsufficientFundsException, AccountNotFoundException {
            BankMain.remote.makeDeposit(BankMain.getUser().getAccountNo(), Double.parseDouble(amountValue.getText()));
            BankMain.remote.addActivity(BankMain.getUser().getName(),BankMain.date,"Cash Deposit", String.valueOf(amountValue.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setContentText("Deposit Successful");

            alert.showAndWait();
            returnToHomePage();

    }
}
