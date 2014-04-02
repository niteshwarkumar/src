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

    

    public INDeliveryReq() {
    }


    

    public INDeliveryReq(Integer inDeliveryId, String clientReqText, boolean clientReqCheck, String clientReqBy) {
        this.inDeliveryId = inDeliveryId;
        this.clientReqText = clientReqText;
        this.clientReqCheck = clientReqCheck;
        this.clientReqBy = clientReqBy;
    }
}
