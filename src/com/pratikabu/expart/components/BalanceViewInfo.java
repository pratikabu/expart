package com.pratikabu.expart.components;

/**
 *
 * @author Pratik
 */
public class BalanceViewInfo {
    private int uid;
    private String uName;

    private int paymentMode;
    private String paymentName;

    private double totalPayed, totalReceived;

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getTotalPayed() {
        return totalPayed;
    }

    public void setTotalPayed(double totalPayed) {
        this.totalPayed = totalPayed;
    }

    public void addPayee(double amt){
        totalPayed += amt;
    }

    public double getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(double totalReceived) {
        this.totalReceived = totalReceived;
    }

    public void addReceiver(double amt){
        totalReceived += amt;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double difference(){
        return totalPayed - totalReceived;
    }
}
