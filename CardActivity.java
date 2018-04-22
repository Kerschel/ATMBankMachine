import java.io.Serializable;

public class CardActivity implements Serializable{
    private String date;
    private String activity;
    private String amount;

    public CardActivity(String date, String activity, String amount) {
        this.date = date;
        this.activity = activity;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
