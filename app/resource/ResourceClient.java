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
public class ResourceClient implements Serializable{
  
      private Integer id;
      private String client;
      private String level;
      private String primary;
      private String primaryCount;
      private String secondry;
      private String secondryCount;
      private Integer resourceId;

  public ResourceClient() {
  }

  public ResourceClient(Integer id, String client, String level, String primary, String primaryCount, String secondry, String secondryCount, Integer resourceId) {
    this.id = id;
    this.client = client;
    this.level = level;
    this.primary = primary;
    this.primaryCount = primaryCount;
    this.secondry = secondry;
    this.secondryCount = secondryCount;
    this.resourceId = resourceId;
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

  public String getPrimary() {
    return primary;
  }

  public void setPrimary(String primary) {
    this.primary = primary;
  }

  public String getPrimaryCount() {
    return primaryCount;
  }

  public void setPrimaryCount(String primaryCount) {
    this.primaryCount = primaryCount;
  }

  public String getSecondry() {
    return secondry;
  }

  public void setSecondry(String secondry) {
    this.secondry = secondry;
  }

  public String getSecondryCount() {
    return secondryCount;
  }

  public void setSecondryCount(String secondryCount) {
    this.secondryCount = secondryCount;
  }

  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }
      
      
  
}
