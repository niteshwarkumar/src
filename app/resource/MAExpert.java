/*
 * MAExpert.java
 *
 * Created on June 17, 2009, 2:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.resource;

import java.io.Serializable;

/**
 *
 * @author PP41387
 */
public class MAExpert implements Serializable{
    
    /** Creates a new instance of MAExpert */
    public MAExpert() {
    }
    private Integer id_ma;
    private Integer id_resource;
    private int educationYears1;
private int educationYears2;
private int educationYears3;
private int professionYears;
private String contentExpert1;
private String contentExpert2;
private String contentExpert3;
private String contentExpert4;
private String contentExpert5;
private String contentExpert6;
private String education1;
private String education2;
private String education3;
private String educationLevel1;
private String educationLevel2;
private String educationLevel3;
private String profession;

private double totalScore;
private double contentExpertScore;
private double educatedInScore;
private double professionScore;

    public Integer getId_ma() {
        return id_ma;
    }

    public void setId_ma(Integer id_ma) {
        this.id_ma = id_ma;
    }

    public Integer getId_resource() {
        return id_resource;
    }

    public void setId_resource(Integer id_resource) {
        this.id_resource = id_resource;
    }

    public int getEducationYears1() {
        return educationYears1;
    }

    public void setEducationYears1(int educationYears1) {
        this.educationYears1 = educationYears1;
    }

    public int getEducationYears2() {
        return educationYears2;
    }

    public void setEducationYears2(int educationYears2) {
        this.educationYears2 = educationYears2;
    }

    public int getEducationYears3() {
        return educationYears3;
    }

    public void setEducationYears3(int educationYears3) {
        this.educationYears3 = educationYears3;
    }

    public int getProfessionYears() {
        return professionYears;
    }

    public void setProfessionYears(int professionYears) {
        this.professionYears = professionYears;
    }

    public String getContentExpert1() {
        return contentExpert1;
    }

    public void setContentExpert1(String contentExpert1) {
        this.contentExpert1 = contentExpert1;
    }

    public String getContentExpert2() {
        return contentExpert2;
    }

    public void setContentExpert2(String contentExpert2) {
        this.contentExpert2 = contentExpert2;
    }

    public String getContentExpert3() {
        return contentExpert3;
    }

    public void setContentExpert3(String contentExpert3) {
        this.contentExpert3 = contentExpert3;
    }

    public String getContentExpert4() {
        return contentExpert4;
    }

    public void setContentExpert4(String contentExpert4) {
        this.contentExpert4 = contentExpert4;
    }

    public String getContentExpert5() {
        return contentExpert5;
    }

    public void setContentExpert5(String contentExpert5) {
        this.contentExpert5 = contentExpert5;
    }

    public String getContentExpert6() {
        return contentExpert6;
    }

    public void setContentExpert6(String contentExpert6) {
        this.contentExpert6 = contentExpert6;
    }

    public String getEducation1() {
        return education1;
    }

    public void setEducation1(String education1) {
        this.education1 = education1;
    }

    public String getEducation2() {
        return education2;
    }

    public void setEducation2(String education2) {
        this.education2 = education2;
    }

    public String getEducation3() {
        return education3;
    }

    public void setEducation3(String education3) {
        this.education3 = education3;
    }

    public String getEducationLevel1() {
        return educationLevel1;
    }

    public void setEducationLevel1(String educationLevel1) {
        this.educationLevel1 = educationLevel1;
    }

    public String getEducationLevel2() {
        return educationLevel2;
    }

    public void setEducationLevel2(String educationLevel2) {
        this.educationLevel2 = educationLevel2;
    }

    public String getEducationLevel3() {
        return educationLevel3;
    }

    public void setEducationLevel3(String educationLevel3) {
        this.educationLevel3 = educationLevel3;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getContentExpertScore() {
        return contentExpertScore;
    }

    public void setContentExpertScore(double contentExpertScore) {
        this.contentExpertScore = contentExpertScore;
    }

    public double getEducatedInScore() {
        return educatedInScore;
    }

    public void setEducatedInScore(double educatedInScore) {
        this.educatedInScore = educatedInScore;
    }

    public double getProfessionScore() {
        return professionScore;
    }

    public void setProfessionScore(double professionScore) {
        this.professionScore = professionScore;
    }

    
}
