/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

/**
 *
 * @author Niteshwar
 */
public class INDeliveryReq {

    /** nullable persistent field */
    private Integer id;
    /** nullable persistent field */
    private Integer inDeliveryId;
    private String clientReqText;
    private boolean clientReqCheck;
    private String clientReqBy;
    private String fromPorQ;
    private String type;
    private String notes;
    private String instructionsFor;

    public String getClientReqBy() {
        return clientReqBy;
    }

    public void setClientReqBy(String clientReqBy) {
        this.clientReqBy = clientReqBy;
    }

    public boolean isClientReqCheck() {
        return clientReqCheck;
    }

    public void setClientReqCheck(boolean clientReqCheck) {
        this.clientReqCheck = clientReqCheck;
    }

    public String getClientReqText() {
        return clientReqText;
    }

    public void setClientReqText(String clientReqText) {
        this.clientReqText = clientReqText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInDeliveryId() {
        return inDeliveryId;
    }

    public void setInDeliveryId(Integer inDeliveryId) {
        this.inDeliveryId = inDeliveryId;
    }

    public String getFromPorQ() {
        return fromPorQ;
    }

    public void setFromPorQ(String fromPorQ) {
        this.fromPorQ = fromPorQ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInstructionsFor() {
        return instructionsFor;
    }

    public void setInstructionsFor(String instructionsFor) {
        this.instructionsFor = instructionsFor;
    }

    
    

    public INDeliveryReq() {
    }


    

   
}
