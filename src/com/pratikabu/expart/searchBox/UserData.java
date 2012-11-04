/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pratikabu.expart.searchBox;

/**
 *
 * @author Blue
 */
public class UserData {
    private int uid;
    private String uName;

    public UserData(int uid, String uName) {
        this.uid = uid;
        this.uName = uName;
    }

    public String getUName() {
        return uName;
    }

    public int getUid() {
        return uid;
    }
}
