package model;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class Subscriber {
    private long id;
    private String name;
    private Tariff tariff;
    private List<Payment> paymentList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public long getTariffID(){
        return getTariff().getId();
    }

    public List<Payment> getPayments() {
        return paymentList;
    }

    public void setPayments(List<Payment> payments) {
        this.paymentList = payments;
        paymentList.stream().forEach(payment -> payment.setSubscriberID(id));
    }

    @Override
    public String toString() {
        return String.format("Subscriber %d name: %s\r\n\t" +
                "tariff: " + tariff + "\r\n\t" +
                "paymentList: " + paymentList,
                id, name
        );
    }
}
