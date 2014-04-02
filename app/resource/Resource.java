package app.resource;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Resource implements Serializable {

    
    
    
   /** nullable persistent field */
    private String enteredById;

    /** nullable persistent field */
    private Date enteredByTS;

      /** nullable persistent field */
    private String lastModifiedById;

    /** nullable persistent field */
    private Date lastModifiedByTS;


    /** identifier field */
    private Integer resourceId;

    /** nullable persistent field */
    private String firstName;

    /** nullable persistent field */
    private String lastName;

    /** nullable persistent field */
    private String companyName;

    /** nullable persistent field */
    private boolean agency;

    /** nullable persistent field */
    private String resume;
    
    /** nullable persistent field */
    private String qualityProcess;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String url;

    /** nullable persistent field */
    private boolean doNotUse;

    /** nullable persistent field */
    private boolean translator;

    /** nullable persistent field */
    private boolean editor;

    /** nullable persistent field */
    private boolean proofreader;

    /** nullable persistent field */
    private boolean evaluator;

    /** nullable persistent field */
    private boolean localizer;

    /** nullable persistent field */
    private boolean dtp;

    /** nullable persistent field */
    private boolean icr;

    /** nullable persistent field */
    private Date yearsInIndustry;

    /** nullable persistent field */
    private String nativeCountry;

    /** nullable persistent field */
    private Double projectScoreAverage;

    /** nullable persistent field */
    private String Address_1;

    /** nullable persistent field */
    private String Address_2;

    /** nullable persistent field */
    private String City;

    /** nullable persistent field */
    private String State_prov;

    /** nullable persistent field */
    private String Zip_postal_code;

    /** nullable persistent field */
    private String Country;

    /** nullable persistent field */
    private String Main_telephone_numb1;

    /** nullable persistent field */
    private String workPhoneEx1;

    /** nullable persistent field */
    private String Main_telephone_numb2;

    /** nullable persistent field */
    private String workPhoneEx2;

    /** nullable persistent field */
    private String Fax_number;

    /** nullable persistent field */
    private String cellPhone;

    /** nullable persistent field */
    private String Email_address1;

    /** nullable persistent field */
    private String Email_address2;

    /** nullable persistent field */
    private String Email_address3;

    /** nullable persistent field */
    private String Note;

    /** nullable persistent field */
    private boolean confiAgreement;

    /** nullable persistent field */
    private Date msaSent;

    /** nullable persistent field */
    private Date msaReceived;

    /** nullable persistent field */
    private boolean usesTrados;

    /** nullable persistent field */
    private String tradosVersion;

    /** nullable persistent field */
    private boolean usesDejavu;

    /** nullable persistent field */
    private String dejavuVersion;

    /** nullable persistent field */
    private boolean usesCatalyst;

    /** nullable persistent field */
    private String catalystVersion;

    /** nullable persistent field */
    private boolean usesSdlx;

    /** nullable persistent field */
    private String sdlxVersion;

    /** nullable persistent field */
    private boolean usesTransit;

    /** nullable persistent field */
    private String transitVersion;

    /** nullable persistent field */
    private boolean usesOther;

    /** nullable persistent field */
    private String otherName;

    /** nullable persistent field */
    private String otherVersion;

    /** nullable persistent field */
    private Double medicalScore;

    /** nullable persistent field */
    private Double technicalScore;

    /** nullable persistent field */
    private Double softwareScore;

    /** nullable persistent field */
    private Double legalFinancialScore;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double min;

    /** nullable persistent field */
    private Double t;

    /** nullable persistent field */
    private String tunit;

    /** nullable persistent field */
    private Double e;

    /** nullable persistent field */
    private String eunit;

    /** nullable persistent field */
    private Double te;

    /** nullable persistent field */
    private String teunit;

    /** nullable persistent field */
    private Double p;

    /** nullable persistent field */
    private String punit;

    /** nullable persistent field */
    private String scaleRep;

    /** nullable persistent field */
    private String scale100;

    /** nullable persistent field */
    private String scale95;

    /** nullable persistent field */
    private String scale85;

    /** nullable persistent field */
    private String scale75;

    /** nullable persistent field */
    private String scaleNew;

      /** persistent field */
    private String skypeId;

    /** nullable persistent field */
    private boolean scaleDefault;

    /** nullable persistent field */
    private boolean beingTested;

     /** nullable persistent field */
    private String beingTestedBy;

       /** nullable persistent field */
    private boolean informationTechnology;

        /** nullable persistent field */
    private boolean humanResource;

        /** nullable persistent field */
    private boolean office;

        /** nullable persistent field */
    private boolean sales;

        /** nullable persistent field */
    private boolean accounting;

        /** nullable persistent field */
    private boolean bsdOther;

        /** nullable persistent field */
    private boolean prodOther;





    /** persistent field */
    private Set LanguagePairs;

    /** persistent field */
    private Set Unavails;

    /** persistent field */
    private Set Industries;

    /** persistent field */
    private Set SpecificIndustries;

    /** persistent field */
    private Set ResourceTools;

    /** persistent field */
    private Set ResourceContacts;

    /** persistent field */
    private Set RateScoreDtps;

    private String projectCount = "";
    private String wordCount = ""; 
    /** nullable persistent field */
    private boolean other;
    private boolean tne;
    private boolean consultant;
    private boolean partner;
    private boolean engineering;
    private boolean businesssuport;
    private boolean fqa;
    private String singleCompanyName;
    
    private boolean interpreting;
    private boolean expert;
    private boolean quality; 
    
    private String linRatesNotes = ""; 
    private String dtpRatesNotes = ""; 
    
    private String osaNotes = ""; 
    
    private String linISANotes = ""; 
    private String dtpISANotes = ""; 
    
    private String linDiscountName1 = ""; 
    private String linDiscountDescription1 = ""; 
    private String linDiscountName2 = ""; 
    private String linDiscountDescription2 = ""; 
    private String linDiscountName3 = ""; 
    private String linDiscountDescription3 = ""; 
    private String linDiscountName4 = ""; 
    private String linDiscountDescription4 = ""; 
    private String dtpDiscountName1 = ""; 
    private String dtpDiscountDescription1 = ""; 
    private String dtpDiscountName2 = ""; 
    private String dtpDiscountDescription2 = ""; 
    private String dtpDiscountName3 = ""; 
    private String dtpDiscountDescription3 = ""; 
    private String dtpDiscountName4 = ""; 
    private String dtpDiscountDescription4 = "";

    private String scalePerfect;
    private String scaleContext;
    private String scale8599;
    private String scaleNew4;
    private String riskrating;

     private String prodOtherText;
    private String bsdOtherText;
    private String otherText;
    private String nativeLanguage;
    private String teamNotes;

 
    public Resource(String enteredById, Date enteredByTS, String lastModifiedById, Date lastModifiedByTS, Integer resourceId, String firstName, String lastName, String companyName, boolean agency, String resume, String qualityProcess, String status, String url, boolean doNotUse, boolean translator, boolean editor, boolean proofreader, boolean evaluator, boolean localizer, boolean dtp, boolean icr, Date yearsInIndustry, String nativeCountry, Double projectScoreAverage, String Address_1, String Address_2, String City, String State_prov, String Zip_postal_code, String Country, String Main_telephone_numb1, String workPhoneEx1, String Main_telephone_numb2, String workPhoneEx2, String Fax_number, String cellPhone, String Email_address1, String Email_address2, String Email_address3, String Note, boolean confiAgreement, Date msaSent, Date msaReceived, boolean usesTrados, String tradosVersion, boolean usesDejavu, String dejavuVersion, boolean usesCatalyst, String catalystVersion, boolean usesSdlx, String sdlxVersion, boolean usesTransit, String transitVersion, boolean usesOther, String otherName, String otherVersion, Double medicalScore, Double technicalScore, Double softwareScore, Double legalFinancialScore, String currency, Double min, Double t, String tunit, Double e, String eunit, Double te, String teunit, Double p, String punit, String scaleRep, String scale100, String scale95, String scale85, String scale75, String scaleNew, String skypeId, boolean scaleDefault, boolean beingTested, String beingTestedBy, boolean informationTechnology, boolean humanResource, boolean office, boolean sales, boolean accounting, boolean bsdOther, boolean prodOther, Set LanguagePairs, Set Unavails, Set Industries, Set SpecificIndustries, Set ResourceTools, Set ResourceContacts, Set RateScoreDtps, boolean other, boolean tne, boolean consultant, boolean partner, boolean engineering, boolean businesssuport, boolean fqa, String singleCompanyName, boolean interpreting, boolean expert, boolean quality, String scalePerfect, String scaleContext, String scale8599, String scaleNew4, String riskrating, String prodOtherText, String bsdOtherText, String otherText,String teamNotes, String nativeLanguage) {
        this.enteredById = enteredById;
        this.enteredByTS = enteredByTS;
        this.lastModifiedById = lastModifiedById;
        this.lastModifiedByTS = lastModifiedByTS;
        this.resourceId = resourceId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.agency = agency;
        this.resume = resume;
        this.qualityProcess = qualityProcess;
        this.status = status;
        this.url = url;
        this.doNotUse = doNotUse;
        this.translator = translator;
        this.editor = editor;
        this.proofreader = proofreader;
        this.evaluator = evaluator;
        this.localizer = localizer;
        this.dtp = dtp;
        this.icr = icr;
        this.yearsInIndustry = yearsInIndustry;
        this.nativeCountry = nativeCountry;
        this.projectScoreAverage = projectScoreAverage;
        this.Address_1 = Address_1;
        this.Address_2 = Address_2;
        this.City = City;
        this.State_prov = State_prov;
        this.Zip_postal_code = Zip_postal_code;
        this.Country = Country;
        this.Main_telephone_numb1 = Main_telephone_numb1;
        this.workPhoneEx1 = workPhoneEx1;
        this.Main_telephone_numb2 = Main_telephone_numb2;
        this.workPhoneEx2 = workPhoneEx2;
        this.Fax_number = Fax_number;
        this.cellPhone = cellPhone;
        this.Email_address1 = Email_address1;
        this.Email_address2 = Email_address2;
        this.Email_address3 = Email_address3;
        this.Note = Note;
        this.confiAgreement = confiAgreement;
        this.msaSent = msaSent;
        this.msaReceived = msaReceived;
        this.usesTrados = usesTrados;
        this.tradosVersion = tradosVersion;
        this.usesDejavu = usesDejavu;
        this.dejavuVersion = dejavuVersion;
        this.usesCatalyst = usesCatalyst;
        this.catalystVersion = catalystVersion;
        this.usesSdlx = usesSdlx;
        this.sdlxVersion = sdlxVersion;
        this.usesTransit = usesTransit;
        this.transitVersion = transitVersion;
        this.usesOther = usesOther;
        this.otherName = otherName;
        this.otherVersion = otherVersion;
        this.medicalScore = medicalScore;
        this.technicalScore = technicalScore;
        this.softwareScore = softwareScore;
        this.legalFinancialScore = legalFinancialScore;
        this.currency = currency;
        this.min = min;
        this.t = t;
        this.tunit = tunit;
        this.e = e;
        this.eunit = eunit;
        this.te = te;
        this.teunit = teunit;
        this.p = p;
        this.punit = punit;
        this.scaleRep = scaleRep;
        this.scale100 = scale100;
        this.scale95 = scale95;
        this.scale85 = scale85;
        this.scale75 = scale75;
        this.scaleNew = scaleNew;
        this.skypeId = skypeId;
        this.scaleDefault = scaleDefault;
        this.beingTested = beingTested;
        this.beingTestedBy = beingTestedBy;
        this.informationTechnology = informationTechnology;
        this.humanResource = humanResource;
        this.office = office;
        this.sales = sales;
        this.accounting = accounting;
        this.bsdOther = bsdOther;
        this.prodOther = prodOther;
        this.LanguagePairs = LanguagePairs;
        this.Unavails = Unavails;
        this.Industries = Industries;
        this.SpecificIndustries = SpecificIndustries;
        this.ResourceTools = ResourceTools;
        this.ResourceContacts = ResourceContacts;
        this.RateScoreDtps = RateScoreDtps;
        this.other = other;
        this.tne = tne;
        this.consultant = consultant;
        this.partner = partner;
        this.engineering = engineering;
        this.businesssuport = businesssuport;
        this.fqa = fqa;
        this.singleCompanyName = singleCompanyName;
        this.interpreting = interpreting;
        this.expert = expert;
        this.quality = quality;
        this.scalePerfect = scalePerfect;
        this.scaleContext = scaleContext;
        this.scale8599 = scale8599;
        this.scaleNew4 = scaleNew4;
        this.riskrating = riskrating;
        this.prodOtherText = prodOtherText;
        this.bsdOtherText = bsdOtherText;
        this.otherText = otherText;
        this.teamNotes = teamNotes;
        this.nativeLanguage = nativeLanguage;
    }


    
//    /** full constructor */
//    public Resource(String firstName, String lastName, String companyName, boolean agency, String resume, String status, String url, boolean doNotUse, boolean translator, boolean editor, boolean proofreader, boolean localizer, boolean dtp, boolean icr, Date yearsInIndustry, String nativeCountry, Double projectScoreAverage, String Address_1, String Address_2, String City, String State_prov, String Zip_postal_code, String Country, String Main_telephone_numb1, String workPhoneEx1, String Main_telephone_numb2, String workPhoneEx2, String Fax_number, String cellPhone, String Email_address1, String Email_address2, String Email_address3, String Note, boolean confiAgreement, Date msaSent, Date msaReceived, boolean usesTrados, String tradosVersion, boolean usesDejavu, String dejavuVersion, boolean usesCatalyst, String catalystVersion, boolean usesSdlx, String sdlxVersion, boolean usesTransit, String transitVersion, boolean usesOther, String otherName, String otherVersion, Double medicalScore, Double technicalScore, Double softwareScore, Double legalFinancialScore, String currency, Double min, Double t, String tunit, Double e, String eunit, Double te, String teunit, Double p, String punit, String scaleRep, String scale100, String scale95, String scale85, String scale75, String scaleNew, boolean scaleDefault, Set LanguagePairs, Set Unavails, Set Industries, Set SpecificIndustries, Set ResourceTools, Set ResourceContacts, Set RateScoreDtps, boolean other, boolean consultant, boolean partner, boolean engineering,boolean businesssuport,boolean fqa, String singleCompanyName, String skypeId, boolean beingTested, String beingTestedBy) {
//
//        this.other = other;
//        this.tne=tne;
//        this.consultant = consultant;
//        this.partner = partner;
//        this.engineering = engineering;
//        this.businesssuport = businesssuport;
//        this.businesssuport = fqa;
//        this.singleCompanyName=singleCompanyName;
//
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.companyName = companyName;
//        this.agency = agency;
//        this.resume = resume;
//        this.status = status;
//        this.url = url;
//        this.doNotUse = doNotUse;
//        this.translator = translator;
//        this.editor = editor;
//        this.proofreader = proofreader;
//        this.localizer = localizer;
//        this.dtp = dtp;
//        this.icr = icr;
//        this.yearsInIndustry = yearsInIndustry;
//        this.nativeCountry = nativeCountry;
//        this.projectScoreAverage = projectScoreAverage;
//        this.Address_1 = Address_1;
//        this.Address_2 = Address_2;
//        this.City = City;
//        this.State_prov = State_prov;
//        this.Zip_postal_code = Zip_postal_code;
//        this.Country = Country;
//        this.Main_telephone_numb1 = Main_telephone_numb1;
//        this.workPhoneEx1 = workPhoneEx1;
//        this.Main_telephone_numb2 = Main_telephone_numb2;
//        this.workPhoneEx2 = workPhoneEx2;
//        this.Fax_number = Fax_number;
//        this.cellPhone = cellPhone;
//        this.Email_address1 = Email_address1;
//        this.Email_address2 = Email_address2;
//        this.Email_address3 = Email_address3;
//        this.Note = Note;
//        this.confiAgreement = confiAgreement;
//        this.msaSent = msaSent;
//        this.msaReceived = msaReceived;
//        this.usesTrados = usesTrados;
//        this.tradosVersion = tradosVersion;
//        this.usesDejavu = usesDejavu;
//        this.dejavuVersion = dejavuVersion;
//        this.usesCatalyst = usesCatalyst;
//        this.catalystVersion = catalystVersion;
//        this.usesSdlx = usesSdlx;
//        this.sdlxVersion = sdlxVersion;
//        this.usesTransit = usesTransit;
//        this.transitVersion = transitVersion;
//        this.usesOther = usesOther;
//        this.otherName = otherName;
//        this.otherVersion = otherVersion;
//        this.medicalScore = medicalScore;
//        this.technicalScore = technicalScore;
//        this.softwareScore = softwareScore;
//        this.legalFinancialScore = legalFinancialScore;
//        this.currency = currency;
//        this.min = min;
//        this.t = t;
//        this.tunit = tunit;
//        this.e = e;
//        this.eunit = eunit;
//        this.te = te;
//        this.teunit = teunit;
//        this.p = p;
//        this.punit = punit;
//        this.scaleRep = scaleRep;
//        this.scale100 = scale100;
//        this.scale95 = scale95;
//        this.scale85 = scale85;
//        this.scale75 = scale75;
//        this.scaleNew = scaleNew;
//        this.scaleDefault = scaleDefault;
//        this.LanguagePairs = LanguagePairs;
//        this.Unavails = Unavails;
//        this.Industries = Industries;
//        this.SpecificIndustries = SpecificIndustries;
//        this.ResourceTools = ResourceTools;
//        this.ResourceContacts = ResourceContacts;
//        this.RateScoreDtps = RateScoreDtps;
//        this.skypeId = skypeId;
//        this.beingTested = beingTested;
//        this.beingTestedBy = beingTestedBy;
//
//    }



    /** default constructor */
    public Resource() {
    }

    /** minimal constructor */
    public Resource(Set LanguagePairs, Set Unavails, Set Industries, Set SpecificIndustries, Set ResourceTools, Set ResourceContacts, Set RateScoreDtps) {
        this.LanguagePairs = LanguagePairs;
        this.Unavails = Unavails;
        this.Industries = Industries;
        this.SpecificIndustries = SpecificIndustries;
        this.ResourceTools = ResourceTools;
        this.ResourceContacts = ResourceContacts;
        this.RateScoreDtps = RateScoreDtps;
    }

    public Integer getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isAgency() {
        return this.agency;
    }

    public void setAgency(boolean agency) {
        this.agency = agency;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDoNotUse() {
        return this.doNotUse;
    }

    public void setDoNotUse(boolean doNotUse) {
        this.doNotUse = doNotUse;
    }

    public boolean isTranslator() {
        return this.translator;
    }

    public void setTranslator(boolean translator) {
        this.translator = translator;
    }

    public boolean isEditor() {
        return this.editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public boolean isProofreader() {
        return this.proofreader;
    }

    public void setProofreader(boolean proofreader) {
        this.proofreader = proofreader;
    }

    public boolean isEvaluator() {
        return evaluator;
    }

    public void setEvaluator(boolean evaluator) {
        this.evaluator = evaluator;
    }


    public boolean isLocalizer() {
        return this.localizer;
    }

    public void setLocalizer(boolean localizer) {
        this.localizer = localizer;
    }

    public boolean isDtp() {
        return this.dtp;
    }

    public void setDtp(boolean dtp) {
        this.dtp = dtp;
    }

    public boolean isIcr() {
        return this.icr;
    }

    public void setIcr(boolean icr) {
        this.icr = icr;
    }

    public Date getYearsInIndustry() {
        return this.yearsInIndustry;
    }

    public void setYearsInIndustry(Date yearsInIndustry) {
        this.yearsInIndustry = yearsInIndustry;
    }

    public String getNativeCountry() {
        return this.nativeCountry;
    }

    public void setNativeCountry(String nativeCountry) {
        this.nativeCountry = nativeCountry;
    }

    public Double getProjectScoreAverage() {
        return this.projectScoreAverage;
    }

    public void setProjectScoreAverage(Double projectScoreAverage) {
        this.projectScoreAverage = projectScoreAverage;
    }

    public String getAddress_1() {
        return this.Address_1;
    }

    public void setAddress_1(String Address_1) {
        this.Address_1 = Address_1;
    }

    public String getAddress_2() {
        return this.Address_2;
    }

    public void setAddress_2(String Address_2) {
        this.Address_2 = Address_2;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState_prov() {
        return this.State_prov;
    }

    public void setState_prov(String State_prov) {
        this.State_prov = State_prov;
    }

    public String getZip_postal_code() {
        return this.Zip_postal_code;
    }

    public void setZip_postal_code(String Zip_postal_code) {
        this.Zip_postal_code = Zip_postal_code;
    }

    public String getCountry() {
        return this.Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getMain_telephone_numb1() {
        return this.Main_telephone_numb1;
    }

    public void setMain_telephone_numb1(String Main_telephone_numb1) {
        this.Main_telephone_numb1 = Main_telephone_numb1;
    }

    public String getWorkPhoneEx1() {
        return this.workPhoneEx1;
    }

    public void setWorkPhoneEx1(String workPhoneEx1) {
        this.workPhoneEx1 = workPhoneEx1;
    }

    public String getMain_telephone_numb2() {
        return this.Main_telephone_numb2;
    }

    public void setMain_telephone_numb2(String Main_telephone_numb2) {
        this.Main_telephone_numb2 = Main_telephone_numb2;
    }

    public String getWorkPhoneEx2() {
        return this.workPhoneEx2;
    }

    public void setWorkPhoneEx2(String workPhoneEx2) {
        this.workPhoneEx2 = workPhoneEx2;
    }

    public String getFax_number() {
        return this.Fax_number;
    }

    public void setFax_number(String Fax_number) {
        this.Fax_number = Fax_number;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail_address1() {
        return this.Email_address1;
    }

    public void setEmail_address1(String Email_address1) {
        this.Email_address1 = Email_address1;
    }

    public String getEmail_address2() {
        return this.Email_address2;
    }

    public void setEmail_address2(String Email_address2) {
        this.Email_address2 = Email_address2;
    }

    public String getEmail_address3() {
        return this.Email_address3;
    }

    public void setEmail_address3(String Email_address3) {
        this.Email_address3 = Email_address3;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public boolean isConfiAgreement() {
        return this.confiAgreement;
    }

    public void setConfiAgreement(boolean confiAgreement) {
        this.confiAgreement = confiAgreement;
    }

    public Date getMsaSent() {
        return this.msaSent;
    }

    public void setMsaSent(Date msaSent) {
        this.msaSent = msaSent;
    }

    public Date getMsaReceived() {
        return this.msaReceived;
    }

    public void setMsaReceived(Date msaReceived) {
        this.msaReceived = msaReceived;
    }

    public boolean isUsesTrados() {
        return this.usesTrados;
    }

    public void setUsesTrados(boolean usesTrados) {
        this.usesTrados = usesTrados;
    }

    public String getTradosVersion() {
        return this.tradosVersion;
    }

    public void setTradosVersion(String tradosVersion) {
        this.tradosVersion = tradosVersion;
    }

    public boolean isUsesDejavu() {
        return this.usesDejavu;
    }

    public void setUsesDejavu(boolean usesDejavu) {
        this.usesDejavu = usesDejavu;
    }

    public String getDejavuVersion() {
        return this.dejavuVersion;
    }

    public void setDejavuVersion(String dejavuVersion) {
        this.dejavuVersion = dejavuVersion;
    }

    public boolean isUsesCatalyst() {
        return this.usesCatalyst;
    }

    public void setUsesCatalyst(boolean usesCatalyst) {
        this.usesCatalyst = usesCatalyst;
    }

    public String getCatalystVersion() {
        return this.catalystVersion;
    }

    public void setCatalystVersion(String catalystVersion) {
        this.catalystVersion = catalystVersion;
    }

    public boolean isUsesSdlx() {
        return this.usesSdlx;
    }

    public void setUsesSdlx(boolean usesSdlx) {
        this.usesSdlx = usesSdlx;
    }

    public String getSdlxVersion() {
        return this.sdlxVersion;
    }

    public void setSdlxVersion(String sdlxVersion) {
        this.sdlxVersion = sdlxVersion;
    }

    public boolean isUsesTransit() {
        return this.usesTransit;
    }

    public void setUsesTransit(boolean usesTransit) {
        this.usesTransit = usesTransit;
    }

    public String getTransitVersion() {
        return this.transitVersion;
    }

    public void setTransitVersion(String transitVersion) {
        this.transitVersion = transitVersion;
    }

    public boolean isUsesOther() {
        return this.usesOther;
    }

    public void setUsesOther(boolean usesOther) {
        this.usesOther = usesOther;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherVersion() {
        return this.otherVersion;
    }

    public void setOtherVersion(String otherVersion) {
        this.otherVersion = otherVersion;
    }

    public Double getMedicalScore() {
        return this.medicalScore;
    }

    public void setMedicalScore(Double medicalScore) {
        this.medicalScore = medicalScore;
    }

    public Double getTechnicalScore() {
        return this.technicalScore;
    }

    public void setTechnicalScore(Double technicalScore) {
        this.technicalScore = technicalScore;
    }

    public Double getSoftwareScore() {
        return this.softwareScore;
    }

    public void setSoftwareScore(Double softwareScore) {
        this.softwareScore = softwareScore;
    }

    public Double getLegalFinancialScore() {
        return this.legalFinancialScore;
    }

    public void setLegalFinancialScore(Double legalFinancialScore) {
        this.legalFinancialScore = legalFinancialScore;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMin() {
        return this.min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getT() {
        return this.t;
    }

    public void setT(Double t) {
        this.t = t;
    }

    public String getTunit() {
        return this.tunit;
    }

    public void setTunit(String tunit) {
        this.tunit = tunit;
    }

    public Double getE() {
        return this.e;
    }

    public void setE(Double e) {
        this.e = e;
    }

    public String getEunit() {
        return this.eunit;
    }

    public void setEunit(String eunit) {
        this.eunit = eunit;
    }

    public Double getTe() {
        return this.te;
    }

    public void setTe(Double te) {
        this.te = te;
    }

    public String getTeunit() {
        return this.teunit;
    }

    public void setTeunit(String teunit) {
        this.teunit = teunit;
    }

    public Double getP() {
        return this.p;
    }

    public void setP(Double p) {
        this.p = p;
    }

    public String getPunit() {
        return this.punit;
    }

    public void setPunit(String punit) {
        this.punit = punit;
    }

    public String getScaleRep() {
        return this.scaleRep;
    }

    public void setScaleRep(String scaleRep) {
        this.scaleRep = scaleRep;
    }

    public String getScale100() {
        return this.scale100;
    }

    public void setScale100(String scale100) {
        this.scale100 = scale100;
    }

    public String getScale95() {
        return this.scale95;
    }

    public void setScale95(String scale95) {
        this.scale95 = scale95;
    }

    public String getScale85() {
        return this.scale85;
    }

    public void setScale85(String scale85) {
        this.scale85 = scale85;
    }

    public String getScale75() {
        return this.scale75;
    }

    public void setScale75(String scale75) {
        this.scale75 = scale75;
    }

    public String getScaleNew() {
        return this.scaleNew;
    }

    public void setScaleNew(String scaleNew) {
        this.scaleNew = scaleNew;
    }

    public boolean isScaleDefault() {
        return this.scaleDefault;
    }

    public void setScaleDefault(boolean scaleDefault) {
        this.scaleDefault = scaleDefault;
    }

    public Set getLanguagePairs() {
        return this.LanguagePairs;
    }

    public void setLanguagePairs(Set LanguagePairs) {
        this.LanguagePairs = LanguagePairs;
    }

    public Set getUnavails() {
        return this.Unavails;
    }

    public void setUnavails(Set Unavails) {
        this.Unavails = Unavails;
    }

    public Set getIndustries() {
        return this.Industries;
    }

    public void setIndustries(Set Industries) {
        this.Industries = Industries;
    }

    public Set getSpecificIndustries() {
        return this.SpecificIndustries;
    }

    public void setSpecificIndustries(Set SpecificIndustries) {
        this.SpecificIndustries = SpecificIndustries;
    }

    public Set getResourceTools() {
        return this.ResourceTools;
    }

    public void setResourceTools(Set ResourceTools) {
        this.ResourceTools = ResourceTools;
    }

    public Set getResourceContacts() {
        return this.ResourceContacts;
    }

    public void setResourceContacts(Set ResourceContacts) {
        this.ResourceContacts = ResourceContacts;
    }

    public Set getRateScoreDtps() {
        return this.RateScoreDtps;
    }

    public void setRateScoreDtps(Set RateScoreDtps) {
        this.RateScoreDtps = RateScoreDtps;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceId", getResourceId())
            .toString();
    }

    public String getEnteredById() {
        return enteredById;
    }

    public void setEnteredById(String enteredById) {
        this.enteredById = enteredById;
    }

    public Date getEnteredByTS() {
        return enteredByTS;
    }

    public void setEnteredByTS(Date enteredByTS) {
        this.enteredByTS = enteredByTS;
    }

    public String getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public Date getLastModifiedByTS() {
        return lastModifiedByTS;
    }

    public void setLastModifiedByTS(Date lastModifiedByTS) {
        this.lastModifiedByTS = lastModifiedByTS;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isConsultant() {
        return consultant;
    }

    public void setConsultant(boolean consultant) {
        this.consultant = consultant;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    public boolean isEngineering() {
        return engineering;
    }

    public void setEngineering(boolean engineering) {
        this.engineering = engineering;
    }

    public boolean isBusinesssuport() {
        return businesssuport;
    }

    public void setBusinesssuport(boolean businesssuport) {
        this.businesssuport = businesssuport;
    }

    public boolean isFqa() {
        return fqa;
    }

    public void setFqa(boolean fqa) {
        this.fqa = fqa;
    }

    public boolean isInterpreting() {
        return interpreting;
    }

    public void setInterpreting(boolean interpreting) {
        this.interpreting = interpreting;
    }

    public boolean isExpert() {
        return expert;
    }

    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    public boolean isQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }
  
    public String getProjectCount() {
        return projectCount;
    }

    
    public void setProjectCount(String projectCount) {
        this.projectCount = projectCount;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getQualityProcess() {
        return qualityProcess;
    }

    public void setQualityProcess(String qualityProcess) {
        this.qualityProcess = qualityProcess;
    }

    public String getLinRatesNotes() {
        return linRatesNotes;
    }

    public void setLinRatesNotes(String linRatesNotes) {
        this.linRatesNotes = linRatesNotes;
    }

    public String getDtpRatesNotes() {
        return dtpRatesNotes;
    }

    public void setDtpRatesNotes(String dtpRatesNotes) {
        this.dtpRatesNotes = dtpRatesNotes;
    }

    public String getLinDiscountName1() {
        return linDiscountName1;
    }

    public void setLinDiscountName1(String linDiscountName1) {
        this.linDiscountName1 = linDiscountName1;
    }

    public String getLinDiscountDescription1() {
        return linDiscountDescription1;
    }

    public void setLinDiscountDescription1(String linDiscountDescription1) {
        this.linDiscountDescription1 = linDiscountDescription1;
    }

    public String getLinDiscountName2() {
        return linDiscountName2;
    }

    public void setLinDiscountName2(String linDiscountName2) {
        this.linDiscountName2 = linDiscountName2;
    }

    public String getLinDiscountDescription2() {
        return linDiscountDescription2;
    }

    public void setLinDiscountDescription2(String linDiscountDescription2) {
        this.linDiscountDescription2 = linDiscountDescription2;
    }

    public String getLinDiscountName3() {
        return linDiscountName3;
    }

    public void setLinDiscountName3(String linDiscountName3) {
        this.linDiscountName3 = linDiscountName3;
    }

    public String getLinDiscountDescription3() {
        return linDiscountDescription3;
    }

    public void setLinDiscountDescription3(String linDiscountDescription3) {
        this.linDiscountDescription3 = linDiscountDescription3;
    }

    public String getLinDiscountName4() {
        return linDiscountName4;
    }

    public void setLinDiscountName4(String linDiscountName4) {
        this.linDiscountName4 = linDiscountName4;
    }

    public String getLinDiscountDescription4() {
        return linDiscountDescription4;
    }

    public void setLinDiscountDescription4(String linDiscountDescription4) {
        this.linDiscountDescription4 = linDiscountDescription4;
    }

    public String getDtpDiscountName1() {
        return dtpDiscountName1;
    }

    public void setDtpDiscountName1(String dtpDiscountName1) {
        this.dtpDiscountName1 = dtpDiscountName1;
    }

    public String getDtpDiscountDescription1() {
        return dtpDiscountDescription1;
    }

    public void setDtpDiscountDescription1(String dtpDiscountDescription1) {
        this.dtpDiscountDescription1 = dtpDiscountDescription1;
    }

    public String getDtpDiscountName2() {
        return dtpDiscountName2;
    }

    public void setDtpDiscountName2(String dtpDiscountName2) {
        this.dtpDiscountName2 = dtpDiscountName2;
    }

    public String getDtpDiscountDescription2() {
        return dtpDiscountDescription2;
    }

    public void setDtpDiscountDescription2(String dtpDiscountDescription2) {
        this.dtpDiscountDescription2 = dtpDiscountDescription2;
    }

    public String getDtpDiscountName3() {
        return dtpDiscountName3;
    }

    public void setDtpDiscountName3(String dtpDiscountName3) {
        this.dtpDiscountName3 = dtpDiscountName3;
    }

    public String getDtpDiscountDescription3() {
        return dtpDiscountDescription3;
    }

    public void setDtpDiscountDescription3(String dtpDiscountDescription3) {
        this.dtpDiscountDescription3 = dtpDiscountDescription3;
    }

    public String getDtpDiscountName4() {
        return dtpDiscountName4;
    }

    public void setDtpDiscountName4(String dtpDiscountName4) {
        this.dtpDiscountName4 = dtpDiscountName4;
    }

    public String getDtpDiscountDescription4() {
        return dtpDiscountDescription4;
    }

    public void setDtpDiscountDescription4(String dtpDiscountDescription4) {
        this.dtpDiscountDescription4 = dtpDiscountDescription4;
    }

    public String getLinISANotes() {
        return linISANotes;
    }

    public void setLinISANotes(String linISANotes) {
        this.linISANotes = linISANotes;
    }

    public String getDtpISANotes() {
        return dtpISANotes;
    }

    public void setDtpISANotes(String dtpISANotes) {
        this.dtpISANotes = dtpISANotes;
    }

    public String getOsaNotes() {
        return osaNotes;
    }

    public void setOsaNotes(String osaNotes) {
        this.osaNotes = osaNotes;
    }

    public boolean isTne() {
        return tne;
    }

    public void setTne(boolean tne) {
        this.tne = tne;
    }

      public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public boolean isBeingTested() {
        return beingTested;
    }

    public void setBeingTested(boolean beingTested) {
        this.beingTested = beingTested;
    }

    public String getBeingTestedBy() {
        return beingTestedBy;
    }

    public void setBeingTestedBy(String beingTestedBy) {
        this.beingTestedBy = beingTestedBy;
    }

    public String getSingleCompanyName() {
        return singleCompanyName;
    }

    public void setSingleCompanyName(String singleCompanyName) {
        this.singleCompanyName = singleCompanyName;
    }

    public String getScale8599() {
        return scale8599;
    }

    public void setScale8599(String scale8599) {
        this.scale8599 = scale8599;
    }

    public String getScaleContext() {
        return scaleContext;
    }

    public void setScaleContext(String scaleContext) {
        this.scaleContext = scaleContext;
    }

    public String getScaleNew4() {
        return scaleNew4;
    }

    public void setScaleNew4(String scaleNew4) {
        this.scaleNew4 = scaleNew4;
    }

    public String getScalePerfect() {
        return scalePerfect;
    }

    public void setScalePerfect(String scalePerfect) {
        this.scalePerfect = scalePerfect;
    }

    public String getRiskrating() {
        return riskrating;
    }

    public void setRiskrating(String riskrating) {
        this.riskrating = riskrating;
    }

    public boolean isAccounting() {
        return accounting;
    }

    public void setAccounting(boolean accounting) {
        this.accounting = accounting;
    }

    public boolean isBsdOther() {
        return bsdOther;
    }

    public void setBsdOther(boolean bsdOther) {
        this.bsdOther = bsdOther;
    }

    public boolean isHumanResource() {
        return humanResource;
    }

    public void setHumanResource(boolean humanResource) {
        this.humanResource = humanResource;
    }

    public boolean isInformationTechnology() {
        return informationTechnology;
    }

    public void setInformationTechnology(boolean informationTechnology) {
        this.informationTechnology = informationTechnology;
    }

    public boolean isOffice() {
        return office;
    }

    public void setOffice(boolean office) {
        this.office = office;
    }

    public boolean isProdOther() {
        return prodOther;
    }

    public void setProdOther(boolean prodOther) {
        this.prodOther = prodOther;
    }

    public boolean isSales() {
        return sales;
    }

    public void setSales(boolean sales) {
        this.sales = sales;
    }

    public String getBsdOtherText() {
        return bsdOtherText;
    }

    public void setBsdOtherText(String bsdOtherText) {
        this.bsdOtherText = bsdOtherText;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    public String getProdOtherText() {
        return prodOtherText;
    }

    public void setProdOtherText(String prodOtherText) {
        this.prodOtherText = prodOtherText;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getTeamNotes() {
        return teamNotes;
    }

    public void setTeamNotes(String teamNotes) {
        this.teamNotes = teamNotes;
    }

 
}
