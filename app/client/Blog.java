/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

import java.sql.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Neil
 */
public class Blog extends ActionForm{
     private Integer id;

    /** nullable persistent field */
    private String author,topic;
    

    /** nullable persistent field */
    private Date firstDraft;
    private Date finalDraft;
    private Date postedOn;

    /** nullable persistent field */
  

    public Blog()
    {
        System.out.println("Blog constructor>>>>>>>>>>>>>>>>>>>>");
    }
      public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Blog(String author,String topic ,Date  firstDraft, Date finalDraft,Date postedOn,Integer id)
    {
        this.author=author;
        this.topic=topic;
        this.firstDraft=firstDraft;
        this.finalDraft=finalDraft;
        this.postedOn=postedOn;
        this.id=id;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getFinalDraft() {
        return finalDraft;
    }

    public void setFinalDraft(Date finalDraft) {
        this.finalDraft = finalDraft;
    }

    public Date getFirstDraft() {
        return firstDraft;
    }

    public void setFirstDraft(Date firstDraft) {
        this.firstDraft = firstDraft;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

   

   
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

   
   
}
