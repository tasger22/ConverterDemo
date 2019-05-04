package hu.bme.aut.converterhomework.currencyelements;

/**
 * Created by Stealth on 2017. 11. 28..
 */

public class CurrencyConvertValue {
    private int pos;
    private double toUsd;
    private double toHuf;
    private double toGbp;
    private double toEur;
    private String name;

    public CurrencyConvertValue(String name,double toUsd, double toEur, double toGbp, double toHuf, int pos){
        this.name = name;
        this.toEur = toEur;
        this.toUsd = toUsd;
        this.toHuf = toHuf;
        this.toGbp = toGbp;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public double getToEur() {
        return toEur;
    }

    public double getToGbp() {
        return toGbp;
    }

    public double getToHuf() {
        return toHuf;
    }

    public double getToUsd() {
        return toUsd;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
