package model;

public class Payment {
    private Long id;
    private Integer summa;
    private Long subscriberID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSumma() {
        return summa;
    }

    public void setSumma(Integer summa) {
        this.summa = summa;
    }

    public Long getSubscriberID() {
        return subscriberID;
    }

    public void setSubscriberID(Long subscriberID) {
        this.subscriberID = subscriberID;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", summa=" + summa +
                '}';
    }
}
