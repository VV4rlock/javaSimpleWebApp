package dbLots.dataSets;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class SignatureDataSet implements Serializable { // Serializable Important to Hibernate!
    //private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true, updatable = false)
    private String name;

    @Column(name = "loginOfCreater", updatable = false)
    private String loginOfCreater;

    @Column(name = "loginOfLastRate")
    private String loginOfLastRate;

    @Column(name = "lastPrice")
    private int lastPrice;

    @Column(name = "description", updatable = false)
    private String description;

    @Column(name = "dataOfCreation", updatable = false)
    private long dateOfCreation;

    @Column(name = "timeAlive", updatable = false)
    private long timeAlive;

    //Important to Hibernate!
    @SuppressWarnings("UnusedDeclaration")
    public SignatureDataSet() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public SignatureDataSet(long id, String name, String loginOfCreater, String loginOfLastRate,
                            int lastPrice, String description, long dateOfCreation, long timeAlive) {
        this.setId(id);
        this.setName(name);
        this.setLoginOfCreater(loginOfCreater);
        this.setLoginOfLastRate(loginOfLastRate);
        this.setLastPrice(lastPrice);
        this.setDescription(description);
        this.setDateOfCreation(dateOfCreation);
        this.setTimeAlive(timeAlive);
    }

    public SignatureDataSet(String name, String loginOfCreater, String loginOfLastRate,
                            int lastPrice, String description, long dateOfCreation, long timeAlive) {
        this.setId(-1);
        this.setName(name);
        this.setLoginOfCreater(loginOfCreater);
        this.setLoginOfLastRate(loginOfLastRate);
        this.setLastPrice(lastPrice);
        this.setDescription(description);
        this.setDateOfCreation(dateOfCreation);
        this.setTimeAlive(timeAlive);
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLoginOfCreater() {
        return loginOfCreater;
    }
    public void setLoginOfCreater(String name) {
        this.loginOfCreater = name;
    }

    public String getLoginOfLastRate() {
        return loginOfLastRate;
    }
    public void setLoginOfLastRate(String name) {
        this.loginOfLastRate = name;
    }

    public int getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(int price) {
        this.lastPrice = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateOfCreation() {
        return dateOfCreation;
    }
    public void setDateOfCreation(long dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getTimeAlive() {
        return timeAlive;
    }
    public void setTimeAlive(long timeAlive) {
        this.timeAlive = timeAlive;
    }


    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}