/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

/**
 *
 * @author Neil
 */
public class UploadDocument {
    private int id;
    private String company_name;
    private String project_name;
    private String documents;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
    

}
