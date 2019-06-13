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
public class ISOStandard implements Serializable {
    
    private Integer id;
    private String iso;
    private String header;
    private String content;
    private Integer headerNo;
    private Integer contentNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHeaderNo() {
        return headerNo;
    }

    public void setHeaderNo(Integer headerNo) {
        this.headerNo = headerNo;
    }

    public Integer getContentNo() {
        return contentNo;
    }

    public void setContentNo(Integer contentNo) {
        this.contentNo = contentNo;
    }

    
}
