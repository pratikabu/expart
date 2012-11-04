/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pratikabu.expart.components;

import com.pratikabu.expart.searchBox.UserData;

/**
 *
 * @author Blue
 */
public class UserAmountDetail {
    private UserData userData;
    private AmountInfo ai;

    public UserAmountDetail(UserData userData, AmountInfo ai) {
        this.userData = userData;
        this.ai = ai;
    }

    public AmountInfo getAI() {
        return ai;
    }

    public void addAI(double amt){
        this.ai.addAmount(amt);
    }

    public UserData getUserData() {
        return userData;
    }
}
