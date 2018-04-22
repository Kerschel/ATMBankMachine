import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public class RegisterController {
    public TextField name;
    public TextField address;
    public PasswordField pinhide;
    public MenuButton accountType;
    public Button submits;
    public ToggleButton pwshow;
    public TextField pinshow;
    public TextField openbalance;
    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (pwshow.isSelected()) {
            pinshow.setText(pinhide.getText());
            pinshow.setVisible(true);
            pinhide.setVisible(false);
            return;
        }
        pinhide.setText(pinshow.getText());
        pinhide.setVisible(true);
        pinshow.setVisible(false);
    }

    public void submitForm(javafx.event.ActionEvent actionEvent) throws RemoteException, FileNotFoundException {
        if(pinhide.getText().length() > pinshow.getText().length()){
            pinshow.setText(pinhide.getText());
        }
        pinhide.setText(pinshow.getText());
        BankMain.remote.addAccount(name.getText(),pinhide.getText(),accountType.getText(), Double.parseDouble(openbalance.getText()));
        displayResource.close();
    }

    public void savings(javafx.event.ActionEvent actionEvent) throws RemoteException {
        accountType.setText("Savings");
    }

    public void checkings(javafx.event.ActionEvent actionEvent) throws RemoteException {
        accountType.setText("Checkings");
    }

}
