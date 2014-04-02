/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.inteqa;

/**
 *
 * @author Niteshwar
 */
public class INSourceFile {
        private Integer id;
        private Integer quantity;
        private String extension;
        private String notes;
        private Integer projectId;

    public INSourceFile(Integer id, Integer quantity, String extension, String notes, Integer projectId) {
        this.id = id;
        this.quantity = quantity;
        this.extension = extension;
        this.notes = notes;
        this.projectId = projectId;
    }

    public INSourceFile() {
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
