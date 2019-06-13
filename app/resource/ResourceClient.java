/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import java.io.Serializable;

/**
 *
 * @author Nishika
 */
public class ResourceClient implements Serializable {

    private Integer id;
    private String client;
    private String level;
    private String primry;
    private Integer primaryCount;
    private String secondry;
    private Integer secondryCount;
    private Integer resourceId;
    private String language;

    public ResourceClient() {
    }

    public ResourceClient(String client, String level, String primry, Integer primaryCount, String secondry, Integer secondryCount, Integer resourceId, String language) {

        this.client = client;
        this.level = level;
        this.primry = primry;
        this.primaryCount = primaryCount;
        this.secondry = secondry;
        this.secondryCount = secondryCount;
        this.resourceId = resourceId;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrimry() {
        return primry;
    }

    public void setPrimry(String primry) {
        this.primry = primry;
    }

    public Integer getPrimaryCount() {
        return primaryCount;
    }

    public void setPrimaryCount(Integer primaryCount) {
        this.primaryCount = primaryCount;
    }

    public String getSecondry() {
        return secondry;
    }

    public void setSecondry(String secondry) {
        this.secondry = secondry;
    }

    public Integer getSecondryCount() {
        return secondryCount;
    }

    public void setSecondryCount(Integer secondryCount) {
        this.secondryCount = secondryCount;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
