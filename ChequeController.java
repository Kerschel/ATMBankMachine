
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ChequeController implements Initializable{

    public TextField amountpay;
    public TextField payorder;
    public TextField yourname;
    public TextField cash;
    public TextField date;
    public Button save;
    public Label msg;
    public void getData(String name,String cash,String payto){
        Float num = new Float(cash) ;
        int dollars = (int)Math.floor( num ) ;
        int cent = (int)Math.floor( ( num - dollars ) * 100.0f ) ;
        this.yourname.setText(name);
        this.cash.setText("$" + String.valueOf(cash));
        payorder.setText(payto);
        amountpay.setText(capitalizer(EnglishNumberToWords.convert(dollars)+" dollars and " + EnglishNumberToWords.convert(cent) +" cents"));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String requiredDate = df.format(new Date()).toString();
        date.setText(requiredDate);
    }

    public void printCheque(javafx.event.ActionEvent actionEvent){
        save.setVisible(false);
        WritableImage image = displayResource.root.snapshot(new SnapshotParameters(), null);
        File file = new File("cheque.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println(e + "ERROR ");
        }
        msg.setVisible(true);
    }




    private String capitalizer(String word){

        String[] words = word.split(" ");
        StringBuilder sb = new StringBuilder();
        if (words[0].length() > 0) {
            sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
            for (int i = 1; i < words.length; i++) {
                sb.append(" ");
                sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
            }
        }
        return  sb.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msg.setVisible(false);
        getData(BankMain.user.getName(),ChequeForm.amt,ChequeForm.pay);
        try {
            BankMain.remote.addActivity(BankMain.getUser().getName(),BankMain.date,"Cheque",ChequeForm.amt);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
