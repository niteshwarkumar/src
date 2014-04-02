/*
 * workspace.java
 *
 * Created on October 16, 2004, 11:37 AM
 */

package app.workspace;

import java.beans.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class Workspace extends Object implements Serializable {
    
    private List appointments;
    private String username;
    private String view;
    private Date startDate;
    private String move;
    private List contacts;
    private String[] colors = new String[12];
    private String[] links = new String[6];
    private String[] displayText = new String[6];
    
    private PropertyChangeSupport propertySupport;
    
    public Workspace() {
        this.username = "";
        this.view = "";
        this.move ="";
        this.appointments = null;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String value) {
        this.username = value;
    }
    
    public List getAppointments() {
        return this.appointments;
    }
    
    public void setAppointments(List a) {
        this.appointments = a;
    }
    
    public String getView() {
        return this.view;
    }
    
    public void setView(String v) {
        this.view = v;
    }
    
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date sd) {
        this.startDate = sd;
    }
    public String getMove() {
        return this.move;
    }
    
    public void setMove(String m) {
        this.move = m;
    }
    
    public void setContacts(List c) {
        this.contacts = c;
    }
    
    public List getContacts() {
        return this.contacts;
    }
    
    public String[] getColors() {
        return this.colors;
    }
    
    public void setColors(String[] c) {
        this.colors = c;
    }
    
    public String[] getLinks() {
        return this.links;
    }
    
    public void setLinks(String[] l) {
        this.links = l;
    }
    
    public String[] getDisplayText() {
        return this.displayText;
    }
    
    public void setDisplayText(String[] d) {
        this.displayText = d;
    }
}
