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
public class PmFee implements Serializable {
    
        /** identifier field */
    private Integer id;

    /** identifier field */
    private Integer pmfee1;

            /** identifier field */
    private Integer pmfee2;
    
        /** identifier field */
    private Double pmpercent;

    public PmFee() {
    }

    public PmFee(Integer pmfee1, Integer pmfee2, Double pmpercent) {
        this.pmfee1 = pmfee1;
        this.pmfee2 = pmfee2;
        this.pmpercent = pmpercent;
    }

    public Integer getPmfee1() {
        return pmfee1;
    }

    public void setPmfee1(Integer pmfee1) {
        this.pmfee1 = pmfee1;
    }

    public Integer getPmfee2() {
        return pmfee2;
    }

    public void setPmfee2(Integer pmfee2) {
        this.pmfee2 = pmfee2;
   }
  

    public Double getPmpercent() {
        return pmpercent;
    }

    public void setPmpercent(Double pmpercent) {
        this.pmpercent = pmpercent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
  
}
