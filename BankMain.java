
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BankMain extends Application implements Initializable {

    public TextField namevalue;
    public Button one;

    public Button two;

    public Button three;

    public Button four;

    public Button five;

    public Button six;

    public Button seven;

    public Button eight;

    public Button nine;

    public Button zero;

    public Button cancel;

    public Button clear;

    public Button enter;

    public TextField pinvalue;

    public Label credentials;
    public static ServerInterface remote;
    public static String ServerURL;
    private static Stage window;
    public  static Parent root;
    public static String date;
    public static ObservableList<String> activities = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        root = FXMLLoader.load(getClass().getResource("FXML/HomeScreen.fxml"));
        window.setTitle("KJ & GR Banking");
        window.setScene(new Scene(root));
        window.show();
    }

    public static Stage getprimaryStage(){
        return window;
    }


//Setting up RPC server url. If no arguments were given it defaults to local host server
    public static void main(String[] args) throws UnknownHostException {
        if(args.length == 0){
            ServerURL = "rmi://" + "localhost" + "/BankServer";
        }
        else
            ServerURL = "rmi:/" + InetAddress.getByName(args[0]) + "/BankServer";

        System.out.println(ServerURL);
        launch(args);
    }


    public void enterPin(String text){
        String pin = pinvalue.getText();
        pin+= text;
        pinvalue.setText(pin);
    }

    public void setPin(javafx.event.ActionEvent event) throws IOException {
        enterPin(((Button)event.getSource()).getText());
    }

    public void clear(javafx.event.ActionEvent event) throws IOException {
        pinvalue.setText("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(new Date()).toString();
        //The RMI  lookup is tried with the arguments the server given by the user. If the server is down it tries the other server.
        try {
            remote= (ServerInterface) Naming.lookup(ServerURL);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Hello");
        if (remote == null){
            ServerURL = "rmi://" + "localhost" + "/BankServer";
            System.out.println(ServerURL);

            try {
                remote= (ServerInterface) Naming.lookup(ServerURL);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


    }
    public static Accounts user = null;
    public static String name,pin;
//  // Login settings. Checks if the user pin and id is correct. If it is the server replies with the account information and allows the user to continue
    public void Login(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {
        if(remote.accessAccount(namevalue.getText(),pinvalue.getText(),"real")!=null) {
            name = namevalue.getText();
            pin = pinvalue.getText();
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(remote.accessAccount(namevalue.getText(), pinvalue.getText(),"real")));
            user = (Accounts) iStream.readObject();
            remote.addLock(user.getAccountNo());
            if (user != null) {
                Parent bankpage = FXMLLoader.load(getClass().getResource("FXML/BankPage.fxml"));
                Scene bankScene = new Scene(bankpage);
                Stage bankstage = BankMain.getprimaryStage();
                bankstage.hide();
                bankstage.setScene(bankScene);
                bankstage.show();
            }
        }
        else {
            credentials.setText("Account Locked");
            credentials.setVisible(true);
//            credentials = null;
        }
    }

    public static Accounts getUser() throws IOException, ClassNotFoundException {
        ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(remote.accessAccount(name, pin,"check")));
        return user = (Accounts) iStream.readObject();
    }
    public void receipt() throws IOException {
        new displayResource("FXML/PrintCheque.fxml");
    }
    public void Register(javafx.event.ActionEvent event) throws IOException {
        new displayResource("FXML/Register.fxml");
        credentials.setText("Registered");
        credentials.setVisible(true);
    }

}
