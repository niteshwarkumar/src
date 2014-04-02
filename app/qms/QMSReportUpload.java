/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class QMSReportUpload implements Serializable{

     private Integer id;

     private Integer auditno;

     private String pathname;

     private String path;

    public Integer getAuditno() {
        return auditno;
    }

    public void setAuditno(Integer auditno) {
        this.auditno = auditno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }



}
