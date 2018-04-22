import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawController implements Initializable {

    @FXML
    private Button twentyDollar;

    @FXML
    private Button oneHundredDollar;

    @FXML
    private Button twoHundredDollar;

    @FXML
    private Button fiveHundredDollar;

    @FXML
    private Button sixHundredDollar;

    @FXML
    private TextField amountValue;

    String amount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void makeWithdrawal(ActionEvent event){
        String amount = ((Button)event.getSource()).getText();
        double myAmount = Double.parseDouble(amount);
        System.out.println(myAmount);

        try{

            BankMain.remote.makeWithdrawal(BankMain.getUser().getAccountNo(), myAmount);
            BankMain.remote.addActivity(BankMain.getUser().getName(),BankMain.date,"Cash Withdrawal", String.valueOf(myAmount));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setContentText("Withdrawal Successful");

            alert.showAndWait();
            returnToHomePage();
        }catch(InsufficientFundsException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText("Insufficient Funds in Account");

            alert.showAndWait();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void addToAmount(String value){
        String amount = amountValue.getText();

        amount += value;

        amountValue.setText(amount);
    }

    public void inputValue(ActionEvent event){
        String buttonText = ((Button)event.getSource()).getText();

        addToAmount(buttonText);
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

    public void goToOtherAmountPage() throws IOException{
        Parent page = FXMLLoader.load(getClass().getResource("FXML/OtherAmountWithdraw.fxml"));

        Scene withdrawScene = new Scene(page);

        Stage bankStage = BankMain.getprimaryStage();

        bankStage.hide();
        bankStage.setScene(withdrawScene);

        bankStage.show();
    }

    public void withdrawButtonClick(ActionEvent event){
        String amountString = amountValue.getText();

        double withdrawAmount = Double.parseDouble(amountString);

        if(withdrawAmount % 20 != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Value Error");
            alert.setContentText("Please Enter Amount Divisible by $20.00");

            alert.showAndWait();
            amountValue.setText("");
            return;
        }

        this.withdrawCash(withdrawAmount);
    }

    public void withdrawCash(double balance){
        try{

            BankMain.remote.makeWithdrawal(BankMain.getUser().getAccountNo(), balance);
            BankMain.remote.addActivity(BankMain.getUser().getName(),BankMain.date,"Cash Withdrawal", String.valueOf(balance));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setContentText("Withdrawal Successful");

            alert.showAndWait();
            returnToHomePage();
        }catch(InsufficientFundsException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText("Insufficient Funds in Account");

            alert.showAndWait();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
