package edu.uncc.evaluation01;

import java.io.Serializable;

public class Bill implements Serializable {

    int billAmount;
    int tipPercent;

    public Bill() {
    }

    public Bill(int billAmount, int tipPercent) {
        this.billAmount = billAmount;
        this.tipPercent = tipPercent;

    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

    public int getTipPercent() {
        return tipPercent;
    }

    public void setTipPercent(int tipPercent) {
        this.tipPercent = tipPercent;
    }


}
