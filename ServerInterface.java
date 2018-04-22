import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
    byte[] accessAccount(String name, String pin, String type) throws IOException;

    boolean addAccount(String name, String pin, String accountType, double balance) throws RemoteException, FileNotFoundException;

    boolean createCheque(Accounts a)throws RemoteException, IOException;

    void addLock(String accNo) throws FileNotFoundException, RemoteException;

    void releaseLock(String accNo) throws FileNotFoundException,RemoteException;

    void addActivity(String name, String date, String activity, String amount) throws RemoteException, IOException;

    boolean reduceBalance(String name,String pin,double amount)throws RemoteException, IOException;

    boolean changePin(String name,String oldpin,String newpin)throws RemoteException, IOException;

    double makeWithdrawal(String accountNo, double amount) throws RemoteException, IOException, InsufficientFundsException, AccountNotFoundException;

    ArrayList getActivity(String name) throws RemoteException, IOException;

    void makeDeposit(String accountNo,double amount)throws RemoteException, IOException, InsufficientFundsException, AccountNotFoundException;

    double transferFunds(String sourceAccountNo, String destinationAccountNo, double amount) throws IOException, RemoteException, InsufficientFundsException, AccountNotFoundException;
}
