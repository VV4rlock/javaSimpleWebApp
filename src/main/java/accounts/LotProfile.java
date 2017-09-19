package accounts;


import java.util.Date;

public class LotProfile {
    private long id;
    private String name;
    private String loginOfCreater;
    private String loginOfLastRate;
    private int lastPrice;
    private String description;
    private long dateOfCreation;
    private long timeAlive;

    public LotProfile(long id, String name,String loginOfCreater, String loginOfLastRate,
                      int lastPrice, String description, long dateOfCreation, long timeAlive) {
        this.id=id;
        this.name=name;
        this.loginOfCreater=loginOfCreater;
        this.loginOfLastRate=loginOfLastRate;
        this.lastPrice=lastPrice;
        this.description=description;
        this.dateOfCreation=dateOfCreation;
        this.timeAlive=timeAlive;
    }

    public LotProfile(String name,String loginOfCreater, String loginOfLastRate,
                      int lastPrice, String description, long dateOfCreation, long timeAlive) {
        this.id=-1;
        this.name=name;
        this.loginOfCreater=loginOfCreater;
        this.loginOfLastRate=loginOfLastRate;
        this.lastPrice=lastPrice;
        this.description=description;
        this.dateOfCreation=dateOfCreation;
        this.timeAlive=timeAlive;
    }

    public String getName() {
        return name;
    }

    public String getLoginOfCreater() {
        return loginOfCreater;
    }

    public String getLoginOfLastRate() {
        return loginOfLastRate;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public String getDescription() {
        return description;
    }

    public long getDateOfCreation() {
        return dateOfCreation;
    }

    public long getId() {
        return id;
    }

    public long getTimeAlive() {
        return timeAlive;
    }

    public String getEndTime(){
        return (new Date(timeAlive+dateOfCreation)).toString();
    }

}
