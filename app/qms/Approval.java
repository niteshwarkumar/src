/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nishika
 */
public class Approval  implements Serializable{

  private Integer id;
  private Integer docId;
  private Integer userId;
  private boolean status;
  private Date updateDate; 
  private String mainTab;
  private boolean disable;

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Approval() {
  }

  public Approval(Integer id, String docName, Integer docId, String user, Integer userId, boolean status, Date updateDate) {
    this.id = id;
    this.docId = docId;
    this.userId = userId;
    this.status = status;
    this.updateDate = updateDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDocId() {
    return docId;
  }

  public void setDocId(Integer docId) {
    this.docId = docId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getMainTab() {
    return mainTab;
  }

  public void setMainTab(String mainTab) {
    this.mainTab = mainTab;
  }

  public boolean isDisable() {
    return disable;
  }

  public void setDisable(boolean disable) {
    this.disable = disable;
  }

  
}
  