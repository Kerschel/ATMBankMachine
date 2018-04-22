import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PayController implements Initializable {

    public Label price;
    public Label itemname;
    public Button buy;
    public Button back;
    public String accountNo, name, priceitem;

    public void BuyItem(ActionEvent actionEvent) throws InsufficientFundsException, AccountNotFoundException, IOException {
        try{
            BankMain.remote.makeWithdrawal(BankMain.user.getAccountNo(), Double.parseDouble(priceitem));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transaction Successful");
            alert.setContentText("Payment Successful");
            alert.showAndWait();
            Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
            Scene bankScene = new Scene(bankpage);
            Stage bankstage = BankMain.getprimaryStage();
            bankstage.hide();
            bankstage.setScene(bankScene);
            bankstage.show();
        }
        catch (InsufficientFundsException insufficientFundsException){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaction Error");
            alert.setContentText("Insufficient Funds in Account");
            alert.showAndWait();
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

        File myFile = new File("sale.txt");

        try {
            Scanner in = new Scanner(myFile);
            while (in.hasNext()) {
                accountNo = in.next();
                name = in.next();
                priceitem = in.next();
            }
            in.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error with file");
        }
        if(accountNo.equals(BankMain.user.getAccountNo())){
            itemname.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            price.setText("$ " +priceitem);
        }


    }


}