package model;

public class Tariff {
    private int id;
    private String descr;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", descr='" + descr + '\'' +
                '}';
    }
}
