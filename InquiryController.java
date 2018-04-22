import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.smartcardio.Card;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class InquiryController implements Initializable{

    public Label name;
    public Label date;
    public Label account;
    public Label balance;

    public TableColumn<CardActivity,String> activity;
    public TableColumn<CardActivity,String> date2;
    public TableColumn<CardActivity, String> amount;
    public TableView<CardActivity> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Accounts a = null;
        try {
            a = BankMain.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        name.setText(a.getName());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String Date= df.format(new Date()).toString();
        date.setText("Date: "+Date);
        balance.setText("Balance: " + String.valueOf(Math.round(a.getBalance())));
        account.setText(a.getAccountType() + " Account");
        ObservableList<CardActivity> active = FXCollections.observableArrayList();

        try {
            System.out.println(BankMain.user.getName());
            active = FXCollections.observableArrayList(BankMain.remote.getActivity(BankMain.user.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        active.add(new CardActivity("10/10/2014","Withdraw","1050.00"));
        table.setItems(active);
        activity.setCellValueFactory(new PropertyValueFactory<>("activity"));
        date2.setCellValueFactory(new PropertyValueFactory<>("date"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

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
