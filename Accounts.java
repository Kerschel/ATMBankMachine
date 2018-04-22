import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Accounts implements Serializable {
    private String accountNo;
    private String name;
    private String pin;
    private String accountType;
    private double balance;
    private ArrayList<CardActivity> activities = new ArrayList<>();

    public Accounts(String name, String pin,String accountType, double balance) {
        this.pin = pin;
        Random rand = new Random();
        int randomNum = 88763527+ rand.nextInt((91827482 - 88763527) + 1);
        this.accountNo = String.valueOf(randomNum);
        this.name = name;
        this.accountType = accountType;
        this.balance = balance;
        activities =  new ArrayList<>();
    }

    public Accounts(String accountNo, String name, String pin, String accountType, double balance) {
        this.accountNo = accountNo;
        this.name = name;
        this.pin = pin;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public void addActivities(String date, String activity, String amount) {
        this.activities.add(new CardActivity(date,activity,amount));
    }

    public ArrayList<CardActivity> getActivities() {
        return activities;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double makeWithdrawal(double amount) throws InsufficientFundsException{
        System.out.println("Balance: " + this.balance + "\nAmount: " + amount);
        if(amount > this.balance){

            throw new InsufficientFundsException("Insufficient Funds");
        }
        this.balance = this.balance - amount;
        return this.balance;
    }

    public void creditAccount(double amount){
        this.balance += amount;
    }
}
