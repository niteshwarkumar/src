/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tools;


import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class Conversion implements Serializable {

   /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String source;

    /** nullable persistent field */
    private String target;

    /** nullable persistent field */
    private Float conversion;

    /*Default Constructor*/
    public Conversion(){
    }

    public Conversion(String source, String target, Float conversion) {
        this.source = source;
        this.target = target;
        this.conversion = conversion;
    }

    public Float getConversion() {
        return conversion;
    }

    public void setConversion(Float conversion) {
        this.conversion = conversion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    

}
