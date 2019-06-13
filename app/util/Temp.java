/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import java.io.Serializable;

/**
 *
 * @author abhisheksingh
 */
public class Temp implements Serializable{
    
     private Integer id;
    
    private String content;
    private String comment;

    public Temp() {
    }

    public Temp(String content, String comment) {
        this.content = content;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
}
