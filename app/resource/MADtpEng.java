/*
 * MADtpEng.java
 *
 * Created on June 17, 2009, 2:49 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package app.resource;

/**
 *
 * @author PP41387
 */
public class MADtpEng {

    /**
     * Creates a new instance of MADtpEng
     */
    public MADtpEng() {
    }
    private Integer id_ma;
    private Integer id_resource;
    private int sizeFullTime;
    private int yearFounded;
    private String contentExpert1;
    private String contentExpert2;
    private String contentExpert3;
    private String contentExpert4;
    private String contentExpert5;
    private String contentExpert6;
    private String iso1;
    private String iso2;
    private String iso3;
    private String iso4;
    private String industry1;
    private String industry2;
    private String industry3;
    private String countryCert1;
    private String countryCert2;
    private String countryCert3;

    private String tools1;
    private String tools2;
    private String tools3;
    private String tools4;
    private String tools5;
    private String tools6;

    private double size1Score;
    private double size2Score;
    private double toolsScore;
    private double accreditationScore;
    private double contentExpertScore;

    private double totalScore;

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

    public int getSizeFullTime() {
        return sizeFullTime;
    }

    public void setSizeFullTime(int sizeFullTime) {
        this.sizeFullTime = sizeFullTime;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(int yearFounded) {
        this.yearFounded = yearFounded;
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

    public String getIso1() {
        return iso1;
    }

    public void setIso1(String iso1) {
        this.iso1 = iso1;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso4() {
        return iso4;
    }

    public void setIso4(String iso4) {
        this.iso4 = iso4;
    }

    public String getIndustry1() {
        return industry1;
    }

    public void setIndustry1(String industry1) {
        this.industry1 = industry1;
    }

    public String getIndustry2() {
        return industry2;
    }

    public void setIndustry2(String industry2) {
        this.industry2 = industry2;
    }

    public String getIndustry3() {
        return industry3;
    }

    public void setIndustry3(String industry3) {
        this.industry3 = industry3;
    }

    public String getCountryCert1() {
        return countryCert1;
    }

    public void setCountryCert1(String countryCert1) {
        this.countryCert1 = countryCert1;
    }

    public String getCountryCert2() {
        return countryCert2;
    }

    public void setCountryCert2(String countryCert2) {
        this.countryCert2 = countryCert2;
    }

    public String getCountryCert3() {
        return countryCert3;
    }

    public void setCountryCert3(String countryCert3) {
        this.countryCert3 = countryCert3;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getSize1Score() {
        return size1Score;
    }

    public void setSize1Score(double size1Score) {
        this.size1Score = size1Score;
    }

    public double getSize2Score() {
        return size2Score;
    }

    public void setSize2Score(double size2Score) {
        this.size2Score = size2Score;
    }

    public double getToolsScore() {
        return toolsScore;
    }

    public void setToolsScore(double toolsScore) {
        this.toolsScore = toolsScore;
    }

    public double getAccreditationScore() {
        return accreditationScore;
    }

    public void setAccreditationScore(double accreditationScore) {
        this.accreditationScore = accreditationScore;
    }

    public double getContentExpertScore() {
        return contentExpertScore;
    }

    public void setContentExpertScore(double contentExpertScore) {
        this.contentExpertScore = contentExpertScore;
    }

    public String getTools1() {
        return tools1;
    }

    public void setTools1(String tools1) {
        this.tools1 = tools1;
    }

    public String getTools2() {
        return tools2;
    }

    public void setTools2(String tools2) {
        this.tools2 = tools2;
    }

    public String getTools3() {
        return tools3;
    }

    public void setTools3(String tools3) {
        this.tools3 = tools3;
    }

    public String getTools4() {
        return tools4;
    }

    public void setTools4(String tools4) {
        this.tools4 = tools4;
    }

    public String getTools5() {
        return tools5;
    }

    public void setTools5(String tools5) {
        this.tools5 = tools5;
    }

    public String getTools6() {
        return tools6;
    }

    public void setTools6(String tools6) {
        this.tools6 = tools6;
    }

}
