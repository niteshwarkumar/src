/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Niteshwar
 */
public class QMSLibrary implements Serializable {

  private Integer id;
  private String mainTab;
  private String title;
  private String description;
  private String isoreference;
  private String category;
  private String docId;
  private String version;
  private String type;
  private String format;
  private Date releaseDate;
  private String owner;
  private boolean allCheck;
  private boolean pmCheck;
  private boolean engCheck;
  private boolean dtpCheck;
  private boolean vmCheck;
  private boolean smCheck;
  private boolean manCheck;
  private boolean accCheck;
  private boolean hrCheck;
  private boolean itCheck;
  private boolean qmCheck;
  private boolean mqaCheck;
  private String other;
  private String uploadBy;
  private Date uploadDate;
  private String fileName;
  private String fileSaveName;
  private String link;
  private String person1;
  private String person2;
  private String person3;
  private Date date1;
  private Date date2;
  private Date date3;
  private boolean sign1;
  private boolean sign2;
  private boolean sign3;
  private String needed;
  private String reason;
  private boolean affected;
  private String affectedBox;
  private String type1;
  private String type2;
  private String type3;



    public QMSLibrary() {
    }

    public QMSLibrary(String mainTab, String title, String description, String isoreference, String category, String docId, String version, String type, String format, Date releaseDate, String owner, boolean allCheck, boolean pmCheck, boolean engCheck, boolean dtpCheck, boolean vmCheck, boolean smCheck, boolean manCheck, boolean accCheck, boolean hrCheck, boolean itCheck, boolean qmCheck, boolean mqaCheck, String other, String uploadBy, Date uploadDate, String fileName, String fileSaveName, String link, String person1, String person2, String person3, Date date1, Date date2, Date date3, boolean sign1, boolean sign2, boolean sign3, String needed, String reason, boolean affected, String affectedBox, String type1, String type2, String type3) {
        this.mainTab = mainTab;
        this.title = title;
        this.description = description;
        this.isoreference = isoreference;
        this.category = category;
        this.docId = docId;
        this.version = version;
        this.type = type;
        this.format = format;
        this.releaseDate = releaseDate;
        this.owner = owner;
        this.allCheck = allCheck;
        this.pmCheck = pmCheck;
        this.engCheck = engCheck;
        this.dtpCheck = dtpCheck;
        this.vmCheck = vmCheck;
        this.smCheck = smCheck;
        this.manCheck = manCheck;
        this.accCheck = accCheck;
        this.hrCheck = hrCheck;
        this.itCheck = itCheck;
        this.qmCheck = qmCheck;
        this.mqaCheck = mqaCheck;
        this.other = other;
        this.uploadBy = uploadBy;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.link = link;
        this.person1 = person1;
        this.person2 = person2;
        this.person3 = person3;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.sign1 = sign1;
        this.sign2 = sign2;
        this.sign3 = sign3;
        this.needed = needed;
        this.reason = reason;
        this.affected = affected;
        this.affectedBox = affectedBox;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
    }

    
    public boolean isAccCheck() {
        return accCheck;
    }

    public void setAccCheck(boolean accCheck) {
        this.accCheck = accCheck;
    }

    public boolean isAllCheck() {
        return allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public boolean isDtpCheck() {
        return dtpCheck;
    }

    public void setDtpCheck(boolean dtpCheck) {
        this.dtpCheck = dtpCheck;
    }

    public boolean isEngCheck() {
        return engCheck;
    }

    public void setEngCheck(boolean engCheck) {
        this.engCheck = engCheck;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isHrCheck() {
        return hrCheck;
    }

    public void setHrCheck(boolean hrCheck) {
        this.hrCheck = hrCheck;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isItCheck() {
        return itCheck;
    }

    public void setItCheck(boolean itCheck) {
        this.itCheck = itCheck;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isManCheck() {
        return manCheck;
    }

    public void setManCheck(boolean manCheck) {
        this.manCheck = manCheck;
    }

    public boolean isMqaCheck() {
        return mqaCheck;
    }

    public void setMqaCheck(boolean mqaCheck) {
        this.mqaCheck = mqaCheck;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPmCheck() {
        return pmCheck;
    }

    public void setPmCheck(boolean pmCheck) {
        this.pmCheck = pmCheck;
    }

    public boolean isQmCheck() {
        return qmCheck;
    }

    public void setQmCheck(boolean qmCheck) {
        this.qmCheck = qmCheck;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

     public boolean isSmCheck() {
        return smCheck;
    }

    public void setSmCheck(boolean smCheck) {
        this.smCheck = smCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isVmCheck() {
        return vmCheck;
    }

    public void setVmCheck(boolean vmCheck) {
        this.vmCheck = vmCheck;
    }

    public String getMainTab() {
        return mainTab;
    }

    public void setMainTab(String mainTab) {
        this.mainTab = mainTab;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public String getPerson1() {
        return person1;
    }

    public void setPerson1(String person1) {
        this.person1 = person1;
    }

    public String getPerson2() {
        return person2;
    }

    public void setPerson2(String person2) {
        this.person2 = person2;
    }

    public String getPerson3() {
        return person3;
    }

    public void setPerson3(String person3) {
        this.person3 = person3;
    }

    public boolean isSign1() {
        return sign1;
    }

    public void setSign1(boolean sign1) {
        this.sign1 = sign1;
    }

    public boolean isSign2() {
        return sign2;
    }

    public void setSign2(boolean sign2) {
        this.sign2 = sign2;
    }

    public boolean isSign3() {
        return sign3;
    }

    public void setSign3(boolean sign3) {
        this.sign3 = sign3;
    }

    public String getIsoreference() {
        return isoreference;
    }

    public void setIsoreference(String isoreference) {
        this.isoreference = isoreference;
    }

    public String getNeeded() {
        return needed;
    }

    public void setNeeded(String needed) {
        this.needed = needed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isAffected() {
        return affected;
    }

    public void setAffected(boolean affected) {
        this.affected = affected;
    }

    public String getAffectedBox() {
        return affectedBox;
    }

    public void setAffectedBox(String affectedBox) {
        this.affectedBox = affectedBox;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

  

}
