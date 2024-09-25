public class Event {
    private String type, freq, amount, mm, dd, yy, notes;

    public Event(String type, String freq, String amount, String mm, String dd, String yy, String notes) {
        this.type = type;
        this.freq = freq;
        this.amount = amount;
        this.mm = mm;
        this.dd = dd;
        this.yy = yy;
        this.notes = notes;
        if (type.equals("Withdrawal") || type.equals("Buy")) {
            this.amount = String.valueOf(-getAmountVal());
        }
    }
    public String getType() {
        return type;
    }
    public String getFreq(){
        return freq;
    }
    public String getAmount() {
        return amount;
    }
    public int getAmountVal() {
        return Integer.parseInt(amount);
    }
    public String getDD(){
        return dd;
    }
    public String getMM(){
        return mm;
    }
    public String getYY(){
        return yy;
    }
    public String getNotes(){
        return notes;
    }
    public int dateCompile() {
        return (Integer.parseInt(yy) * 1000) + (Integer.parseInt(mm) * 100) + Integer.parseInt(dd);
    }
}
