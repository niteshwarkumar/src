package app.menu;

import app.user.User;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Todo implements Serializable {

    /** identifier field */
    private Integer todoId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private boolean started;

    /** nullable persistent field */
    private boolean completed;

    /** nullable persistent field */
    private Date dateAdded;

    /** nullable persistent field */
    private Date dateDue;

    /** nullable persistent field */
    private Date dateCompleted;

    /** nullable persistent field */
    private User User;

    /** full constructor */
    public Todo(String description, String priority, boolean started, boolean completed, Date dateAdded, Date dateDue, Date dateCompleted, User User) {
        this.description = description;
        this.priority = priority;
        this.started = started;
        this.completed = completed;
        this.dateAdded = dateAdded;
        this.dateDue = dateDue;
        this.dateCompleted = dateCompleted;
        this.User = User;
    }

    /** default constructor */
    public Todo() {
    }

    public Integer getTodoId() {
        return this.todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateDue() {
        return this.dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateCompleted() {
        return this.dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public User getUser() {
        return this.User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("todoId", getTodoId())
            .toString();
    }

}
