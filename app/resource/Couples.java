/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class Couples  implements Serializable {

      private Integer id;
      private Integer resourceid;
      private String resourcetype;
      private Integer coupleid;
      private String coupletype;
      private String yesno;
      private String resourcename;
      private String couplename;

    public Couples() {
    }

    public Couples(Integer id, Integer resourceid, String resourcetype, Integer coupleid, String coupletype, String yesno, String resourcename, String couplename) {
        this.id = id;
        this.resourceid = resourceid;
        this.resourcetype = resourcetype;
        this.coupleid = coupleid;
        this.coupletype = coupletype;
        this.yesno = yesno;
        this.resourcename = resourcename;
        this.couplename = couplename;
    }

    public Integer getCoupleid() {
        return coupleid;
    }

    public void setCoupleid(Integer coupleid) {
        this.coupleid = coupleid;
    }

    public String getCouplename() {
        return couplename;
    }

    public void setCouplename(String couplename) {
        this.couplename = couplename;
    }

    public String getCoupletype() {
        return coupletype;
    }

    public void setCoupletype(String coupletype) {
        this.coupletype = coupletype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public String getYesno() {
        return yesno;
    }

    public void setYesno(String yesno) {
        this.yesno = yesno;
    }

      


}
