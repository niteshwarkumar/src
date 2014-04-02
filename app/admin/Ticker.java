/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class Ticker implements Serializable {

    private Integer tickerId;
    private Integer userId;
    private String userEmail;
    private Integer boxNumber;

    public Ticker() {
    }

    public Ticker(Integer tickerId, Integer userId, String userEmail, Integer boxNumber) {
        this.tickerId = tickerId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.boxNumber = boxNumber;
    }

    public Integer getboxNumber() {
        return boxNumber;
    }

    public void setboxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public Integer getTickerId() {
        return tickerId;
    }

    public void setTickerId(Integer tickerId) {
        this.tickerId = tickerId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }
}
