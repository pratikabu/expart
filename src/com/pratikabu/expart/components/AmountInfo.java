/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pratikabu.expart.components;

/**
 *
 * @author Pratik
 */
public class AmountInfo {
    private double amount;
    private int paymentMode;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount){
        this.amount+= amount;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }
}
