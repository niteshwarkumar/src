/*
 * MALinguistic.java
 *
 * Created on June 17, 2009, 1:32 PM
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
public class MALinguistic implements Serializable{
    
    /** Creates a new instance of MALinguistic */
    public MALinguistic() {
    }
    
    private Integer ID_linMA;
    private Integer id_resource;
    private String nativeSpeaker;
    private String professionalLinguist;
    private String professionalLinguistFT;
    private String professionalLinguistPT;
    private int formalEducationYears;
   private String nativeLocation;
   private String formalEducationLevel;
   private String contentExpert1;
   private String contentExpert3;
   private String contentExpert2;
   private String contentExpert4;
   private String contentExpert5;
   private String contentExpert6;
   private String profession;
   private String educatedIn1;
   private String educatedIn2;
   private String educatedIn3;
   private String educatedInLevel1;
   private String educatedInLevel2;
   private String educatedInLevel3;
   private int educatedInYears1;
   private int educatedInYears3;
  private int educatedInYears2;
  private int professionYears;
  private int medicalExpirienceYears;
  private int softwareExpirienceYears;
  private int techExpirienceYears;
  private int legalExpirienceYears;
  private int marketingExpirienceYears;
  
  private String medicalExpirience;
  private String softwareExpirience;
  private String techExpirience;
  private String legalExpirience;
  private String marketingExpirience;
  
  
 private String iso9001;
 private String iso13485;
 private String iso14971;
 private String otherIso;
 private String industry1;
 private String industry2;
 private String industry3;
 private String countryCert1;
 private String countryCert2;
 private String countryCert3;
 private String reference;
 private String referenceNotes;
  private int sizeFullTimeEmp;
 private double sizeVolume;
 private String sizeUnit;
 private String location1;
 private String location2;
 private String location3;
private String nicheLangs;
private String nicheSpecialization;
private double totalScore;

private double speakerScore;
private double profLinScore;
private double nativeCountryScore;
private double formalEdScore;
private double contentExpertScore;
private double educatedInScore;
private double professionScore;
private double experienceScore;
private double toolsScore;
private double accreditationScore;
private double referenceScore;
private double size1Score;
private double size2Score;
private double locationScore;
private double nicheLangScore;
private double nicheSpecScore;


    public Integer getID_linMA() {
        return ID_linMA;
    }

    public void setID_linMA(Integer ID_linMA) {
        this.ID_linMA = ID_linMA;
    }

    public Integer getId_resource() {
        return id_resource;
    }

    public void setId_resource(Integer id_resource) {
        this.id_resource = id_resource;
    }

    public String getNativeSpeaker() {
        return nativeSpeaker;
    }

    public void setNativeSpeaker(String nativeSpeaker) {
        this.nativeSpeaker = nativeSpeaker;
    }

    public String getProfessionalLinguist() {
        return professionalLinguist;
    }

    public void setProfessionalLinguist(String professionalLinguist) {
        this.professionalLinguist = professionalLinguist;
    }

    public int getFormalEducationYears() {
        return formalEducationYears;
    }

    public void setFormalEducationYears(int formalEducationYears) {
        this.formalEducationYears = formalEducationYears;
    }

    public String getNativeLocation() {
        return nativeLocation;
    }

    public void setNativeLocation(String nativeLocation) {
        this.nativeLocation = nativeLocation;
    }

    public String getFormalEducationLevel() {
        return formalEducationLevel;
    }

    public void setFormalEducationLevel(String formalEducationLevel) {
        this.formalEducationLevel = formalEducationLevel;
    }

    public String getContentExpert1() {
        return contentExpert1;
    }

    public void setContentExpert1(String contentExpert1) {
        this.contentExpert1 = contentExpert1;
    }

    public String getContentExpert3() {
        return contentExpert3;
    }

    public void setContentExpert3(String contentExpert3) {
        this.contentExpert3 = contentExpert3;
    }

    public String getContentExpert2() {
        return contentExpert2;
    }

    public void setContentExpert2(String contentExpert2) {
        this.contentExpert2 = contentExpert2;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducatedIn1() {
        return educatedIn1;
    }

    public void setEducatedIn1(String educatedIn1) {
        this.educatedIn1 = educatedIn1;
    }

    public String getEducatedIn2() {
        return educatedIn2;
    }

    public void setEducatedIn2(String educatedIn2) {
        this.educatedIn2 = educatedIn2;
    }

    public String getEducatedIn3() {
        return educatedIn3;
    }

    public void setEducatedIn3(String educatedIn3) {
        this.educatedIn3 = educatedIn3;
    }

    public String getEducatedInLevel1() {
        return educatedInLevel1;
    }

    public void setEducatedInLevel1(String educatedInLevel1) {
        this.educatedInLevel1 = educatedInLevel1;
    }

    public String getEducatedInLevel2() {
        return educatedInLevel2;
    }

    public void setEducatedInLevel2(String educatedInLevel2) {
        this.educatedInLevel2 = educatedInLevel2;
    }

    public String getEducatedInLevel3() {
        return educatedInLevel3;
    }

    public void setEducatedInLevel3(String educatedInLevel3) {
        this.educatedInLevel3 = educatedInLevel3;
    }

    public int getEducatedInYears1() {
        return educatedInYears1;
    }

    public void setEducatedInYears1(int educatedInYears1) {
        this.educatedInYears1 = educatedInYears1;
    }

    public int getEducatedInYears3() {
        return educatedInYears3;
    }

    public void setEducatedInYears3(int educatedInYears3) {
        this.educatedInYears3 = educatedInYears3;
    }

    public int getEducatedInYears2() {
        return educatedInYears2;
    }

    public void setEducatedInYears2(int educatedInYears2) {
        this.educatedInYears2 = educatedInYears2;
    }

    public int getProfessionYears() {
        return professionYears;
    }

    public void setProfessionYears(int professionYears) {
        this.professionYears = professionYears;
    }

    public int getMedicalExpirienceYears() {
        return medicalExpirienceYears;
    }

    public void setMedicalExpirienceYears(int medicalExpirienceYears) {
        this.medicalExpirienceYears = medicalExpirienceYears;
    }

    public int getSoftwareExpirienceYears() {
        return softwareExpirienceYears;
    }

    public void setSoftwareExpirienceYears(int softwareExpirienceYears) {
        this.softwareExpirienceYears = softwareExpirienceYears;
    }

    public int getTechExpirienceYears() {
        return techExpirienceYears;
    }

    public void setTechExpirienceYears(int techExpirienceYears) {
        this.techExpirienceYears = techExpirienceYears;
    }

    public int getLegalExpirienceYears() {
        return legalExpirienceYears;
    }

    public void setLegalExpirienceYears(int legalExpirienceYears) {
        this.legalExpirienceYears = legalExpirienceYears;
    }

    public int getMarketingExpirienceYears() {
        return marketingExpirienceYears;
    }

    public void setMarketingExpirienceYears(int marketingExpirienceYears) {
        this.marketingExpirienceYears = marketingExpirienceYears;
    }

    public String getIso9001() {
        return iso9001;
    }

    public void setIso9001(String iso9001) {
        this.iso9001 = iso9001;
    }

    public String getIso13485() {
        return iso13485;
    }

    public void setIso13485(String iso13485) {
        this.iso13485 = iso13485;
    }

    public String getIso14971() {
        return iso14971;
    }

    public void setIso14971(String iso14971) {
        this.iso14971 = iso14971;
    }

    public String getOtherIso() {
        return otherIso;
    }

    public void setOtherIso(String otherIso) {
        this.otherIso = otherIso;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceNotes() {
        return referenceNotes;
    }

    public void setReferenceNotes(String referenceNotes) {
        this.referenceNotes = referenceNotes;
    }

    public int getSizeFullTimeEmp() {
        return sizeFullTimeEmp;
    }

    public void setSizeFullTimeEmp(int sizeFullTimeEmp) {
        this.sizeFullTimeEmp = sizeFullTimeEmp;
    }

    public double getSizeVolume() {
        return sizeVolume;
    }

    public void setSizeVolume(double sizeVolume) {
        this.sizeVolume = sizeVolume;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public String getLocation3() {
        return location3;
    }

    public void setLocation3(String location3) {
        this.location3 = location3;
    }

    public String getNicheLangs() {
        return nicheLangs;
    }

    public void setNicheLangs(String nicheLangs) {
        this.nicheLangs = nicheLangs;
    }

    public String getNicheSpecialization() {
        return nicheSpecialization;
    }

    public void setNicheSpecialization(String nicheSpecialization) {
        this.nicheSpecialization = nicheSpecialization;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getProfessionalLinguistFT() {
        return professionalLinguistFT;
    }

    public void setProfessionalLinguistFT(String professionalLinguistFT) {
        this.professionalLinguistFT = professionalLinguistFT;
    }

    public String getProfessionalLinguistPT() {
        return professionalLinguistPT;
    }

    public void setProfessionalLinguistPT(String professionalLinguistPT) {
        this.professionalLinguistPT = professionalLinguistPT;
    }

    public String getMedicalExpirience() {
        return medicalExpirience;
    }

    public void setMedicalExpirience(String medicalExpirience) {
        this.medicalExpirience = medicalExpirience;
    }

    public String getSoftwareExpirience() {
        return softwareExpirience;
    }

    public void setSoftwareExpirience(String softwareExpirience) {
        this.softwareExpirience = softwareExpirience;
    }

    public String getTechExpirience() {
        return techExpirience;
    }

    public void setTechExpirience(String techExpirience) {
        this.techExpirience = techExpirience;
    }

    public String getLegalExpirience() {
        return legalExpirience;
    }

    public void setLegalExpirience(String legalExpirience) {
        this.legalExpirience = legalExpirience;
    }

    public String getMarketingExpirience() {
        return marketingExpirience;
    }

    public void setMarketingExpirience(String marketingExpirience) {
        this.marketingExpirience = marketingExpirience;
    }

    public double getSpeakerScore() {
        return speakerScore;
    }

    public void setSpeakerScore(double speakerScore) {
        this.speakerScore = speakerScore;
    }

    public double getProfLinScore() {
        return profLinScore;
    }

    public void setProfLinScore(double profLinScore) {
        this.profLinScore = profLinScore;
    }

    public double getNativeCountryScore() {
        return nativeCountryScore;
    }

    public void setNativeCountryScore(double nativeCountryScore) {
        this.nativeCountryScore = nativeCountryScore;
    }

    public double getFormalEdScore() {
        return formalEdScore;
    }

    public void setFormalEdScore(double formalEdScore) {
        this.formalEdScore = formalEdScore;
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

    public double getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(double experienceScore) {
        this.experienceScore = experienceScore;
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

    public double getReferenceScore() {
        return referenceScore;
    }

    public void setReferenceScore(double referenceScore) {
        this.referenceScore = referenceScore;
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

    public double getLocationScore() {
        return locationScore;
    }

    public void setLocationScore(double locationScore) {
        this.locationScore = locationScore;
    }

    public double getNicheLangScore() {
        return nicheLangScore;
    }

    public void setNicheLangScore(double nicheLangScore) {
        this.nicheLangScore = nicheLangScore;
    }

    public double getNicheSpecScore() {
        return nicheSpecScore;
    }

    public void setNicheSpecScore(double nicheSpecScore) {
        this.nicheSpecScore = nicheSpecScore;
    }

    


    
}
