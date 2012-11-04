package com.pratikabu.expart.components;

/**
 *
 * @author Pratik
 */
public class TotalAmountInfo {
    private int paymentMode;
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

    public double difference(){
        return totalPayed - totalReceived;
    }
}
