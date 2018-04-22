
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class BankingServer {
    public static  ArrayList<Accounts> account = new ArrayList<>();
    public static  ArrayList<Locks> locker = new ArrayList<>();
    public static ObservableList<CardActivity> accountUsage = FXCollections.observableArrayList();
    public static void main(String[] args) throws FileNotFoundException, RemoteException, MalformedURLException {

        System.out.println("Bank is online");
        updateData();
        lockData();
        ServerImplementation Servers = new ServerImplementation();
        Naming.rebind("BankServer", Servers);


    }

    public static void updateData() throws FileNotFoundException{
        File myFile = new File("users.txt");
        account = new ArrayList<Accounts>();
        String accountNo, name, pin, accountType, balanceString;
        int numUsers = 0;
        double balance;
        try{
            Scanner in = new Scanner(myFile);
            while (in.hasNext()){
                accountNo = in.next();
                name = in.next();
                pin = in.next();
                accountType = in.next();
                balanceString = in.next();
                balance = Double.parseDouble(balanceString);
                Accounts myAccount = new Accounts(accountNo, name, pin, accountType, balance);
                numUsers++;
                account.add(myAccount);
            }
            System.out.println("Read data for " + numUsers + " users");
            in.close();
        }catch (FileNotFoundException ex){
            System.out.println("Error with file");
            throw ex;
        }
    }

    public static void lockData() throws FileNotFoundException{
        File myFile = new File("lock.txt");
        locker = new ArrayList<Locks>();
        String accountNo, lock;
        int numUsers = 0;
        try{
            Scanner in = new Scanner(myFile);
            while (in.hasNext()){
                accountNo = in.next();
                lock = in.next();
                Locks myAccount = new Locks(accountNo,lock);
                numUsers++;
                locker.add(myAccount);
            }
            System.out.println("Read data for " + numUsers + " users");
            in.close();
        }catch (FileNotFoundException ex){
            System.out.println("Error with file");
            throw ex;
        }
    }

    public static void saveLock() throws FileNotFoundException{
        File myFile = new File("lock.txt");

        PrintWriter printWriter = new PrintWriter(new FileOutputStream(myFile, false));

        for(Locks myAccount: BankingServer.locker){
            printWriter.println(myAccount.getAccountNo() + " " + myAccount.getLock());
        }

        printWriter.close();
    }

    public static void saveData() throws FileNotFoundException{
        File myFile = new File("users.txt");

        PrintWriter printWriter = new PrintWriter(new FileOutputStream(myFile, false));

        for(Accounts myAccount: BankingServer.account){
            printWriter.println(myAccount.getAccountNo() + " " + myAccount.getName() + " " + myAccount.getPin() + " " + myAccount.getAccountType() + " " + myAccount.getBalance());
        }

        printWriter.close();
    }


}