//ClientViewTeamDisplay.java is a datastore that accumulates
//totals and summaries for the Client-->Team tab

package app.client;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import app.resource.*;
import java.util.*;

public class ClientViewTeamDisplay implements Serializable {

    //data members
    private Resource r;
    private int clientProjects;
    private int totalProjects;
    private String totalForClient;
    private ArrayList countedClientProjects;

    /** default constructor */
    public ClientViewTeamDisplay() {
    }

    public ArrayList getCountedClientProjects() {
        return this.countedClientProjects;
    }

    public void setCountedClientProjects(ArrayList ccp) {
        this.countedClientProjects = ccp;
    }
    
    public Resource getResource() {
        return this.r;
    }

    public void setResource(Resource resource) {
        this.r = resource;
    }

   public int getClientProjects() {
        return this.clientProjects;
    }

    public void setClientProjects(int cp) {
        this.clientProjects = cp;
    }
    
    public int getTotalProjects() {
        return this.totalProjects;
    }

    public void setTotalProjects(int tp) {
        this.totalProjects = tp;
    }
    
    public String getTotalForClient() {
        return this.totalForClient;
    }

    public void setTotalForClient(String tfc) {
        this.totalForClient = tfc;
    }
    
}
