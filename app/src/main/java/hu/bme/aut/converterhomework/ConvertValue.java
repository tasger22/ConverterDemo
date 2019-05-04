package hu.bme.aut.converterhomework;

/**
 * Created by Stealth on 2017. 11. 23..
 */

public class ConvertValue {
    private double value;
    private int pos;
    private String prefName;

    public ConvertValue(double value, int pos, String prefName){
        this.value = value;
        this.pos = pos;
        this.prefName = prefName;
    }

    public double getValue() {
        return value;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getPrefName() {
        return prefName;
    }
}
