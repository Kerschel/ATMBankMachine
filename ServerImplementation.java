import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImplementation extends UnicastRemoteObject implements ServerInterface,Serializable {

    public ServerImplementation() throws RemoteException{
    }



    @Override
    public byte[] accessAccount(String name,String pin,String type) throws IOException {
//        BankingServer.updateData();
        BankingServer.lockData();
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        for(int i = 0; i< BankingServer.account.size(); i++){
            System.out.println(BankingServer.account.get(i).getAccountNo());
            if(BankingServer.account.get(i).getName().toLowerCase().equals(name.toLowerCase()) && BankingServer.account.get(i).getPin().equals(pin)){
               if(type.equals("real")) {
                   for (Locks l : BankingServer.locker) {
                       if (l.accountNo.equals(BankingServer.account.get(i).getAccountNo())) {
                           if (l.getLock().equals("1")) {
                               return null;
                           }
                       }
                   }
               }
                Accounts a = BankingServer.account.get(i);

                System.out.println(BankingServer.account.get(i).getName());
                oo.writeObject(a);
                oo.close();
                return bStream.toByteArray();

            }
        }
        return null;
    }





    @Override
    public boolean addAccount(String name,String pin,String accountType,double balance) throws RemoteException, FileNotFoundException {
        BankingServer.account.add(new Accounts(name,pin,accountType,balance));
        BankingServer.saveData();
        return true;
    }

    @Override
    public boolean createCheque(Accounts a) throws RemoteException{
        if(a.getAccountType().equals("Chequeing")){
            return true;
        }
        return false;
    }

    @Override
    public void addLock(String accNo) throws FileNotFoundException,RemoteException {
        int stop =0;
        for(int i = 0; i< BankingServer.locker.size(); i++){
                if (BankingServer.locker.get(i).getAccountNo().equals(accNo)){
                    BankingServer.locker.get(i).setLock("1");
                    BankingServer.saveLock();
                    stop = 1;
                }

                }
                if (stop==0){
            BankingServer.locker.add(new Locks(accNo, "1"));
            BankingServer.saveLock();
        }
    }

    @Override
    public void releaseLock(String accNo) throws FileNotFoundException,RemoteException {
        for(int i = 0; i< BankingServer.locker.size(); i++){
            if (BankingServer.locker.get(i).getAccountNo().equals(accNo)){
                BankingServer.locker.get(i).setLock("0");
                BankingServer.saveLock();
            }

        }

    }


    @Override
    public void addActivity(String name, String date, String activity, String amount) throws RemoteException, IOException {
        for(int i = 0; i< BankingServer.account.size(); i++){
            if (BankingServer.account.get(i).getName().equals(name)){
                BankingServer.account.get(i).addActivities(date,activity,amount);
                break;
            }
        }
    }

    @Override
    public boolean reduceBalance(String name, String pin,double amount) throws RemoteException, IOException {
        for(int i = 0; i< BankingServer.account.size(); i++){
            if(BankingServer.account.get(i).getName().equals(name) && BankingServer.account.get(i).getAccountNo().equals(pin)){
                if( BankingServer.account.get(i).getBalance() >= amount) {
                    BankingServer.account.get(i).setBalance(BankingServer.account.get(i).getBalance() - amount);
                    BankingServer.saveData();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean changePin(String name, String oldpin, String newpin) throws RemoteException, IOException {
        BankingServer.updateData();
        System.out.println(name);
        for(int i = 0; i< BankingServer.account.size(); i++){
            if(BankingServer.account.get(i).getName().equals(name)) {
                BankingServer.account.get(i).setPin(newpin);
                BankingServer.saveData();
                return true;
            }
            }
        return false;
    }

    @Override
    public double makeWithdrawal(String accountNo, double amount) throws RemoteException, IOException, InsufficientFundsException, AccountNotFoundException {
        try{
            BankingServer.updateData();
        }catch (IOException ex){
            throw ex;
        }
        for(int i = 0; i < BankingServer.account.size(); i++){
            if(BankingServer.account.get(i).getAccountNo().equals(accountNo)){
                try{
                    double balance = BankingServer.account.get(i).makeWithdrawal(amount);
                    BankingServer.saveData();
                    return balance;
                }catch (InsufficientFundsException ex){
                    throw ex;
                }
            }
        }
        throw new AccountNotFoundException("Account Not Found");
    }


    @Override
    public ArrayList getActivity(String name) throws RemoteException, IOException {
        for(int i = 0; i< BankingServer.account.size(); i++){
            if (BankingServer.account.get(i).getName().equals(name)){
                return BankingServer.account.get(i).getActivities();
            }
        }
        return null;
    }

    @Override
    public void makeDeposit(String accountNo, double amount) throws RemoteException, IOException, InsufficientFundsException, AccountNotFoundException {
        BankingServer.updateData();

        for(int i = 0; i < BankingServer.account.size(); i++){
            if(BankingServer.account.get(i).getAccountNo().equals(accountNo)){

                    BankingServer.account.get(i).setBalance(BankingServer.account.get(i).getBalance() + amount);
                    BankingServer.saveData();

            }
        }

    }


    @Override
    public double transferFunds(String sourceAccountNo, String destinationAccountNo, double amount) throws IOException, RemoteException, InsufficientFundsException, AccountNotFoundException {

        try{
            BankingServer.updateData();
        }catch (IOException ex){
            throw ex;
        }

        int s = -1;
        int d = -1;

        for(int i = 0; i < BankingServer.account.size(); i++){
            if(BankingServer.account.get(i).getAccountNo().equals(sourceAccountNo)) {
                s = i;
            }
            else if(BankingServer.account.get(i).getAccountNo().equals(destinationAccountNo)) {
                d = i;
            }
        }

        if(s == -1|| d == -1){
            throw new AccountNotFoundException("An account was not found");
        }
        try{
            double balance = BankingServer.account.get(s).makeWithdrawal(amount);
            BankingServer.account.get(d).creditAccount(amount);
            BankingServer.saveData();
            return balance;
        }catch (InsufficientFundsException ex){
            throw ex;
        }
    }
}
