

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class BankPage implements Initializable {
    public Button withdraw;
    public Button transfer;
    public Button change;
    public Button deposit;
    public Button inquiry;
    public Button logout;
    public Button payment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void paymentButton(ActionEvent actionEvent) throws IOException {
        Parent transfer = FXMLLoader.load(getClass().getResource("FXML/paymentPage.fxml"));
        Scene bankScene = new Scene(transfer);
        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }


    public void depositButton(ActionEvent actionEvent) throws IOException {
        Parent transfer = FXMLLoader.load(getClass().getResource("FXML/Deposit.fxml"));
        Scene bankScene = new Scene(transfer);
        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }

    public void transferButton(ActionEvent actionEvent) throws IOException {
        Parent transfer = FXMLLoader.load(getClass().getResource("FXML/TransferFundsPage.fxml"));
        Scene bankScene = new Scene(transfer);
        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }

    public void changePin(javafx.event.ActionEvent actionEvent) throws IOException {
//        Parent inquire = FXMLLoader.load(getClass().getResource("FXML/changePin.fxml"));
//        Scene bankScene = new Scene(inquire);
//        Stage bankstage = BankMain.getprimaryStage();
//        bankstage.hide();
//        bankstage.setScene(bankScene);
//        bankstage.show();

        try {
            Desktop.getDesktop().browse(new URL("https://www.rbcroyalbank.com/personal.html").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void log(javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException {
       BankMain.remote.releaseLock(BankMain.getUser().getAccountNo());
       BankMain.getprimaryStage().close();
    }
    public void createCheque(javafx.event.ActionEvent actionEvent) throws IOException {
//        if(BankMain.remote.createCheque(BankMain.getUser())){
            Parent inquire = FXMLLoader.load(getClass().getResource("FXML/CreateCheque.fxml"));
            Scene bankScene = new Scene(inquire);
            Stage bankstage = BankMain.getprimaryStage();
            bankstage.hide();
            bankstage.setScene(bankScene);
            bankstage.show();
//        }
    }

    public void balanceInquire(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {
        Parent inquire = FXMLLoader.load(getClass().getResource("FXML/Balance.fxml"));
        Scene bankScene = new Scene(inquire);

        Stage bankstage = BankMain.getprimaryStage();
        bankstage.hide();
        bankstage.setScene(bankScene);
        bankstage.show();
    }

    public void launchWithdrawalPage(ActionEvent event) throws IOException{
        Parent page = FXMLLoader.load(getClass().getResource("FXML/WithdrawPage.fxml"));

        Scene withdrawScene = new Scene(page);

        Stage bankStage = BankMain.getprimaryStage();

        bankStage.hide();
        bankStage.setScene(withdrawScene);

        bankStage.show();

    }

}
