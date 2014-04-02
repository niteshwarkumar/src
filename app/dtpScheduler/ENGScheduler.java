/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.dtpScheduler;

import app.project.Project;
import app.quote.Quote1;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Neil
 */
public class ENGScheduler implements Serializable {

private int ID_Schedule;

private String requester;
private String ID_Project;
private String ID_Quote;
private String task;
private String timeNeeded;
private Date finishDate;
private String projectOrQuote;
private String hour;
private String timeNeededUnit;


 public ENGScheduler() {
    }

    public String getID_Project() {
        return ID_Project;
    }

    public void setID_Project(String ID_Project) {
        this.ID_Project = ID_Project;
    }

    public String getID_Quote() {
        return ID_Quote;
    }

    public void setID_Quote(String ID_Quote) {
        this.ID_Quote = ID_Quote;
    }

    public int getID_Schedule() {
        return ID_Schedule;
    }

    public void setID_Schedule(int ID_Schedule) {
        this.ID_Schedule = ID_Schedule;
    }

    public String getProjectOrQuote() {
        return projectOrQuote;
    }

    public void setProjectOrQuote(String projectOrQuote) {
        this.projectOrQuote = projectOrQuote;
    }

     public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(String timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTimeNeededUnit() {
        return timeNeededUnit;
    }

    public void setTimeNeededUnit(String timeNeededUnit) {
        this.timeNeededUnit = timeNeededUnit;
    }

  


}
