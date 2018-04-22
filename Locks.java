public class Locks {
    public String accountNo;
    public String lock;

    public Locks(String accountNo,String lock){
        this.accountNo=accountNo;
        this.lock = lock;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }
}
