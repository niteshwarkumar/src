/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import java.io.Serializable;

/**
 *
 * @author niteshwar
 */
public class OptionsSelect implements Serializable {
    
    public static String TGTAUD = "TGTAUD";// Strategic Change Target Audiance
    public static String PRSAFF = "PRSAFF";// Strategic Change PROCESS(ES) AFFECTED 
   
    private Integer id;
    private Integer screen;
    private Integer optionId;

    public OptionsSelect() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
    
    

}