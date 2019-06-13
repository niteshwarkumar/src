/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;

/**
 *
 * @author niteshwar
 */
public class IsoDoc  implements Serializable {
    
    private Integer id;
    private Integer isoStandard;
    private Integer docId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsoStandard() {
        return isoStandard;
    }

    public void setIsoStandard(Integer isoStandard) {
        this.isoStandard = isoStandard;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }
    
    
    
}
