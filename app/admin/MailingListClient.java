package app.admin;
// Generated Nov 9, 2014 3:32:25 AM by Hibernate Tools 3.2.1.GA



/**
 * MailingListClient generated by hbm2java
 */
public class MailingListClient  implements java.io.Serializable {


     private Integer memberId;
     private Integer mailId;
     private Integer clientId;

    public MailingListClient() {
    }

    public MailingListClient(Integer mailId, Integer clientId) {
       this.mailId = mailId;
       this.clientId = clientId;
    }
   
    public Integer getMemberId() {
        return this.memberId;
    }
    
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getMailId() {
        return this.mailId;
    }
    
    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }
    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }




}


