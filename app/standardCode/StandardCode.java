//StandardCode.java contains code that is shared accros all
//modules, for example, providing a list of countries
//or removing "null" from printouts
package app.standardCode;

import app.extjs.helpers.ProjectHelper;
import javax.servlet.http.Cookie;
import java.util.*;
import java.text.*;
import app.user.*;
import app.project.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class StandardCode {

    //use this instance during all accesses
    private static StandardCode instance = null;

    //allow any code to get this instance
    public static StandardCode getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new StandardCode();
        }
        return instance;
    }
    public String CHANGEORIGINAL =  "Original";

    public Cookie setCookie(String name, String value) {
        //create stored cookie with <code>name</code> and <code>value</code>
        Cookie cookie;
        try {
            
            cookie = new Cookie(name, URLEncoder.encode(noNull(value), "UTF-8"));
       
        //cookie good for 1 year
        cookie.setMaxAge(60 * 60 * 24 * 365);
         return cookie;
         } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StandardCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return this cookis
        return null;
       
    }

    public String getCookie(String name, Cookie[] cookies) {
        //get cookie <code>name</code> from <code>cookies</code>
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null; //cookie not present
    }

    public Double getEuro() {

        URL yahoo;
        String inputLine;
        String inputLineArray = "";
        String conversion1 = "";
        Double conversion = 1.40;

        try {
            conversion = ProjectHelper.getLatestCurrencyRate();
        } catch (Exception e) {
        }

//        try {
//            
//
//
//         //   yahoo = new URL("http://search.yahoo.com/search?p=1+Euro+in+USD");
//             yahoo=new URL("http://www.webservicex.net/CurrencyConvertor.asmx/ConversionRate?FromCurrency=EUR&ToCurrency=USD");
//            URLConnection yc = yahoo.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//
//            while ((inputLine = in.readLine()) != null) {
//                //System.out.println(inputLine);
//
////             if (inputLine.indexOf("<b>1.00 Euro =") != -1) {
////                    inputLineArray = (String) inputLine.subSequence(inputLine.indexOf("<b>1.00 Euro ="), inputLine.indexOf("U.S. Dollars</b>"));
////                }
//                 if (inputLine.indexOf("<double xmlns=\"http://www.webserviceX.NET/\">") != -1) {
//                    inputLineArray = (String) inputLine.subSequence(inputLine.indexOf("<double xmlns=\"http://www.webserviceX.NET/\">"), inputLine.indexOf("</double>"));
//                }
//                conversion1 = inputLineArray.substring(inputLineArray.indexOf(">") + 1);
//////System.out.println("Conversion Rate"+conversion);
//              //   conversion=1/Double.parseDouble(inputLineArray.substring(inputLineArray.indexOf("=")+1));
//            }
//            in.close();
//
//           conversion = Double.parseDouble(conversion1);
//        } catch (Exception e) {
//
//              conversion = 1.44;
//           // Logger.getLogger(StandardCode.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
        ////System.out.println("Conversion Rate" + conversion);
        return conversion;
    }
    
    public String normalizeText(String input){
        StringCleaner sc = new StringCleaner();
        return sc.convertToAscii(input, false);
        //return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("’", "'");
    }
    
    public String getChangeName(String changeName){
     return (noNull(changeName).isEmpty()) ? CHANGEORIGINAL : changeName;
        
    }

    //remove "null" from printout to screen
    public String noNull(String nn) {
        return (nn == null ||  nn.equalsIgnoreCase("null")) ? "" : nn;
    }
    //remove "null" from printout to screen
    public String noNull(String nn, String replace) {
        return (nn == null ||  nn.equalsIgnoreCase("null")) ? replace : nn;
    }
    //remove "null" from printout to screen
    public String noNull(Date nn, String replace) {
        return (nn == null ) ? replace : nn.toString();
    }
    public Double noNull(Double nn) {
        return (nn == null) ? 0.00 : nn;
    }
    public Integer noNull(Integer nn) {
        return (nn == null) ? 0 : nn;
    }
    public Boolean noNull(Boolean nn) {
        return (nn == null) ? false : nn;
    }

    //provide a list of countries for forms
    public String[] getCountries() {
        String Countries[] = {"USA", "AFGHANISTAN", "ALBANIA", "ALGERIA", "AMERICAN SAMOA", "ANDORRA", "ANGOLA", "ANGUILLA", "ANTARCTICA", "ANTIGUA AND BARBUDA", "ARGENTINA", "ARMENIA", "ARUBA", "AUSTRALIA", "AUSTRIA", "AZERBAIJAN", "BAHAMAS", "BAHRAIN", "BANGLADESH", "BARBADOS", "BELARUS", "BELGIUM", "BELIZE", "BENIN", "BERMUDA", "BHUTAN", "BOLIVIA", "BOSNIA AND HERZEGOWINA", "BOTSWANA", "BOUVET ISLAND", "BRAZIL", "BRITISH INDIAN OCEAN TERRITORY", "BRUNEI DARUSSALAM", "BULGARIA", "BURKINA FASO", "BURUNDI", "CAMBODIA", "CAMEROON", "CANADA", "CAPE VERDE", "CAYMAN ISLANDS", "CENTRAL AFRICAN REPUBLIC", "CHAD", "CHILE", "CHINA", "CHRISTMAS ISLAND", "COCOS (KEELING) ISLANDS", "COLOMBIA", "COMOROS", "CONGO", "COOK ISLANDS", "COSTA RICA", "COTE D'IVOIRE", "CROATIA", "CUBA", "CYPRUS", "CZECH REPUBLIC", "DENMARK", "DJIBOUTI", "DOMINICA", "DOMINICAN REPUBLIC", "EAST TIMOR", "ECUADOR", "EGYPT", "EL SALVADOR", "EQUATORIAL GUINEA", "ERITREA", "ESTONIA", "ETHIOPIA", "FALKLAND ISLANDS (MALVINAS)", "FAROE ISLANDS", "FIJI", "FINLAND", "FRANCE", "FRENCH GUIANA", "FRENCH POLYNESIA", "FRENCH SOUTHERN TERRITORIES", "GABON", "GAMBIA", "GEORGIA", "GERMANY", "GHANA", "GIBRALTAR", "GREECE", "GREENLAND", "GRENADA", "GUADELOUPE", "GUAM", "GUATEMALA", "GUINEA", "GUINEA-BISSAU", "GUYANA", "HAITI", "HEARD AND MC DONALD ISLANDS", "HONDURAS", "HONG KONG", "HUNGARY", "ICELAND", "INDIA", "INDONESIA", "IRAN (ISLAMIC REPUBLIC OF)", "IRAQ", "IRELAND", "ISRAEL", "ITALY", "JAMAICA", "JAPAN", "JORDAN", "KAZAKHSTAN", "KENYA", "KIRIBATI", "KOREA", "KUWAIT", "KYRGYZSTAN", "LAO PEOPLE'S DEMOCRATIC REPUBLIC", "LATVIA", "LEBANON", "LESOTHO", "LIBERIA", "LIBYAN ARAB JAMAHIRIYA", "LIECHTENSTEIN", "LITHUANIA", "LUXEMBOURG", "MACAU", "MACEDONIA", "MADAGASCAR", "MALAWI", "MALAYSIA", "MALDIVES", "MALI", "MALTA", "MARSHALL ISLANDS", "MARTINIQUE", "MAURITANIA", "MAURITIUS", "MAYOTTE", "MEXICO", "MICRONESIA (FEDERATED STATES OF)", "MOLDOVA", "MONACO", "MONGOLIA", "MONTSERRAT", "MOROCCO", "MOZAMBIQUE", "MYANMAR", "NAMIBIA", "NAURU", "NEPAL", "NETHERLANDS", "NETHERLANDS ANTILLES", "NEW CALEDONIA", "NEW ZEALAND", "NICARAGUA", "NIGER", "NIGERIA", "NIUE", "NORFOLK ISLAND", "NORTHERN MARIANA ISLANDS", "NORWAY", "OMAN", "PAKISTAN", "PALAU", "PANAMA", "PAPUA NEW GUINEA", "PARAGUAY", "PERU", "PHILIPPINES", "PITCAIRN", "POLAND", "PORTUGAL", "PUERTO RICO", "QATAR", "REUNION", "ROMANIA", "RUSSIAN FEDERATION", "RWANDA", "ST. HELENA", "SAINT KITTS AND NEVIS", "SAINT LUCIA", "ST. PIERRE AND MIQUELON", "SAINT VINCENT AND THE GRENADINES", "SAMOA", "SAN MARINO", "SAO TOME AND PRINCIPE", "SAUDI ARABIA", "SERBIA", "SENEGAL", "SERBIA", "SEYCHELLES", "SIERRA LEONE", "SINGAPORE", "SLOVAKIA", "SLOVENIA", "SOLOMON ISLANDS", "SOMALIA", "SOUTH AFRICA", "SOUTH GEORGIA AND THE SOUTH SANDWIC", "SPAIN", "SRI LANKA", "SUDAN", "SURINAME", "SVALBARD AND JAN MAYEN ISLANDS", "SWAZILAND", "SWEDEN", "SWITZERLAND", "SYRIAN ARAB REPUBLIC", "TAIWAN", "TAJIKISTAN", "TANZANIA", "THAILAND", "TOGO", "TOKELAU", "TONGA", "TRINIDAD AND TOBAGO", "TUNISIA", "TURKEY", "TURKMENISTAN", "TURKS AND CAICOS ISLANDS", "TUVALU", "UGANDA", "UKRAINE", "UNITED ARAB EMIRATES", "UNITED KINGDOM", "UNITED STATES MINOR OUTLYING ISLAND", "URUGUAY", "UZBEKISTAN", "VANUATU", "VATICAN CITY STATE (HOLY SEE)", "VENEZUELA", "VIET NAM", "VIRGIN ISLANDS (BRITISH)", "VIRGIN ISLANDS (U.S.)", "WALLIS AND FUTUNA ISLANDS", "WESTERN SAHARA", "YEMEN", "YUGOSLAVIA", "ZAIRE", "ZAMBIA", "ZIMBABWE"};

        return Countries;
    }

    public String[] getMedicalDevice() {
        String MedicalDevice[] = {"Class I", "Class II", "Class III"};

        return MedicalDevice;
    }

    //return a Double from a String
    public Double getDoubleFromString(String s) {
        String temp = s.replaceAll(",", "");
        temp = temp.replaceAll("$", "");
        temp = temp.trim();

        return new Double(temp);
    }

//format an integer value
    public String formatInteger(Integer num) {
        NumberFormat format = NumberFormat.getIntegerInstance();

        return format.format(num);
    }

//format a double value for two decimal places as percent
    public String formatDoublePercent(Double num) {
        double numTemp = num.doubleValue() * 100;
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2); //we want at least 2 digits displayed

        return format.format(new Double(numTemp));
    }

    //format a double value for two decimal places as percent
    public String formatMoney(double num) {
        NumberFormat format = new DecimalFormat("#,###,###.00");
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2); //we want at least 2 digits displayed
        format.setGroupingUsed(true);
        format.setMinimumIntegerDigits(1);
        return format.format(num);
    }
    //format a double value for two decimal places

    public String formatDouble(Double num) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2); //we want at least 2 digits displayed
        format.setMinimumIntegerDigits(1);
        return format.format(num);
    }

     //format a double value for two decimal places
    public String formatDouble0(Double num) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
        format.setMinimumFractionDigits(0); //we want at least 2 digits displayed
        format.setMinimumIntegerDigits(0);
        return format.format(num);
    }

    //format a double value for three decimal places
    public String formatDouble3(Double num) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(3);
        format.setMinimumFractionDigits(3); //we want at least 3 digits displayed

        return format.format(num);
    }

    //format a double value for 4 decimal places
    public String formatDouble4(Double num) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(4); //we want at least 4 digits displayed

        return format.format(num);
    }

    //check whether a privilege string is present or not
    //privs comes from session.getAttribute("userPrivs")
    public boolean checkPrivStringArray(String[] privs, String priv) {
        boolean present = false;
        for (int i = 0; i < privs.length; i++) {
            if (privs[i].equals(priv)) {

                present = true;
            }
        }

        //true if present; false if not present
        return present;
    }

    //check whether a privilege string is present or not
    //privs comes from user.getPrivileges()
    public boolean checkPrivSet(Set privs, String priv) {
        boolean present = false;
        for (Iterator iter = privs.iterator(); iter.hasNext();) {
            Privilege p = (Privilege) iter.next();
            if (p.getPrivilege().equals(priv)) {
                present = true;
            }
        }

        //true if present; false if not present
        return present;
    }

    //return just first name of a string
    public String getFirstName(String name) {
        String[] names = name.split(" ");

        return names[0];
    }

    //return just last name of a string
    public String getLastName(String name) {
        String[] names = name.split(" ");

        String returnName = new String("");

        for (int i = 1; i < names.length; i++) {
            returnName = returnName + " " + names[i];
        }

        return returnName.trim();
    }

    //return location address
    public String printLocation(Location l) {
        StringBuffer sb = new StringBuffer("");

        sb.append(l.getAddress_1() + "\r");
        if (l.getAddress_2() != null && l.getAddress_2().length() > 0) {
            sb.append(l.getAddress_2() + "\r");
        }
        sb.append(l.getCity() + ", " + l.getState_prov() + " " + l.getZip_postal_code());
        if (l.getCountry() != null && l.getCountry().length() > 0 && !l.getCountry().equals("USA")) {
            sb.append(", " + l.getCountry());
        }

        return sb.toString();
    }

    //Aleks: This method determines if a project needs invoicing
    // That means that the delivery date is not null and clientInvoiceTotal < invoicedSoFar
    public boolean projectStillNeedsInvoicing(Project p) {

        boolean needsInvoicing = false;

        if (p.getDeliveryDate() != null) {

            Double clientInvoiceTotal = null;
            Double invoicedSoFar = null;

            if (p.getProjectAmount() != null) {
                clientInvoiceTotal = p.getProjectAmount();
            } else {
                clientInvoiceTotal = new Double(0);
            }

            if (p.getTotalAmountInvoiced() != null && !"".equals(p.getTotalAmountInvoiced())) {
                invoicedSoFar = new Double(p.getTotalAmountInvoiced().replaceAll(",", ""));
            } else {
                invoicedSoFar = new Double(0);
            }

            double doubleClientInvoiceTotal = roundDouble(clientInvoiceTotal.doubleValue(), 2);
            double doubleInvoicedSoFar = roundDouble(invoicedSoFar.doubleValue(), 2);

            if (doubleClientInvoiceTotal - doubleInvoicedSoFar > 0) {
                needsInvoicing = true;
            }

        }

        return needsInvoicing;
    }

    //Aleks: This method determines if a project is non invoiced
    // That means that clientInvoiceTotal < invoicedSoFar
    public boolean isNonInvoiced(Project p) {

        boolean needsInvoicing = false;

        Double clientInvoiceTotal = null;
        Double invoicedSoFar = null;

        if (p.getProjectAmount() != null) {
            clientInvoiceTotal = p.getProjectAmount();
        } else {
            clientInvoiceTotal = new Double(0);
        }

        if (p.getTotalAmountInvoiced() != null && !"".equals(p.getTotalAmountInvoiced())) {
            invoicedSoFar = new Double(p.getTotalAmountInvoiced().replaceAll(",", ""));
        } else {
            invoicedSoFar = new Double(0);
        }

        double doubleClientInvoiceTotal = roundDouble(clientInvoiceTotal.doubleValue(), 2);
        double doubleInvoicedSoFar = roundDouble(invoicedSoFar.doubleValue(), 2);

        if (doubleClientInvoiceTotal - doubleInvoicedSoFar > 0) {
            needsInvoicing = true;
        }

        return needsInvoicing;
    }

    double roundDouble(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0) {
            power_of_ten *= 10.0;
        }
        return Math.round(value * power_of_ten) / power_of_ten;
    }

    public Double roundDouble1(double value) {
        int decimalPlace = 2;
        double power_of_ten = 1;
        while (decimalPlace-- > 0) {
            power_of_ten *= 10.0;
        }
        return Math.round(value * power_of_ten) / power_of_ten;
    }

    //Aleks: This method determines if a project needs invoicing
    // That means that the delivery date is not null and clientInvoiceTotal < invoicedSoFar

    public String projectInvoicingStatus(Project p) {

        String invoicingStatus = "No";

//        if (p.getDeliveryDate() != null) {

            Double clientInvoiceTotal = null;
            Double invoicedSoFar = null;

            if (p.getProjectAmount() != null) {
                
                if(p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    clientInvoiceTotal = p.getProjectAmount()/p.getEuroToUsdExchangeRate();
                }else{
                    clientInvoiceTotal = p.getProjectAmount();
                }
            } else {
                clientInvoiceTotal = new Double(0);
            }

            if (p.getTotalAmountInvoiced() != null && !"".equals(p.getTotalAmountInvoiced()) && p.getTotalAmountInvoiced() != "?") {
                invoicedSoFar = new Double(noNull(p.getTotalAmountInvoiced()).replaceAll(",", ""));
            } else {
                invoicedSoFar = new Double(0);
            }

            double doubleClientInvoiceTotal = roundDouble(clientInvoiceTotal.doubleValue(), 2);
            double doubleInvoicedSoFar = roundDouble(invoicedSoFar.doubleValue(), 2);
            if (doubleInvoicedSoFar == 0) {
                invoicingStatus = "No";
            }else if (doubleClientInvoiceTotal - doubleInvoicedSoFar > 0) {
                invoicingStatus = "Partial";
            } else {
                invoicingStatus = "Invoiced";
            }

//        }

        return invoicingStatus;
    }

    public static int getWorkDays(Date startDt, Date endDt) {
        Calendar startCal, endCal;
        startCal = Calendar.getInstance();
        startCal.setTime(startDt);
        endCal = Calendar.getInstance();
        endCal.setTime(endDt);
        int workDays = 0;

//Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }
//Just in case the dates were transposed this prevents infinite loop
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDt);
            endCal.setTime(startDt);
        }

        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());

        return workDays;
    }

    public String convertTextToUTF(String notes) {
        try {
            if (null != notes) {
                byte ptext[] = notes.getBytes();
                notes = new String(ptext, "UTF-8");
            } else {
                notes = notes;
            }
        } catch (UnsupportedEncodingException ex) {
            notes = notes;
//        //System.out.println(ex);
            System.err.println("Hibernate Exception:" + ex.getMessage());
//        Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notes;
    }

    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }
    
    
    public String getPropertyValue(String property){
        Properties prop = new Properties();
        InputStream input = null;
        String value = "";
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        prop.load(classLoader.getResourceAsStream("resources/application.properties"));
        value = prop.getProperty(property);
        }catch(IOException ex){}finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        }
        return value;
    
    }
    
    private Properties getEmailProperty(){
        Properties prop = new Properties();
        InputStream input = null;
        String value = "";
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        prop.load(classLoader.getResourceAsStream("resources/email.properties"));
        
        }catch(IOException ex){}finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        }
        return prop;
    
    }
    
    //    private static final String SMTP_HOST_NAME = "xltrans.com";
    private static final String SMTP_AUTH_USER = "excelnet@xltrans.com";
    private static final String SMTP_AUTH_PWD = "3vB@zMsp";
//    public static final String emailFromAddress = "nkumar@xltrans.com";
    public static final String emailFromAddress = "excelnet@xltrans.com";
    
    private static final String emailFromAddressAlias = "Excel Translations";
    
    private static final String SMTP_HOST_NAME = "172.16.2.20";
//    private static final String SMTP_AUTH_USER = "rdecortie@xltrans.com";
//    private static final String SMTP_AUTH_PWD = "t#92i$eP";
//    public static final String emailFromAddress = "rdecortie@xltrans.com"; 

    public Message getMimeMessage() {
        boolean debug = true;
        Properties props = getEmailProperty();
        Session session = Session.getDefaultInstance(props,
                       new javax.mail.Authenticator() {
                           protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                               return new javax.mail.PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
                           }
                       });

        session.setDebug(debug);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(emailFromAddress, "Excel Translations"));
        } catch (MessagingException ex) {
            Logger.getLogger(StandardCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StandardCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    
        public Message getMimeMessage(String fromAdd, String pwd) {
        boolean debug = true;
        Properties props = getEmailProperty();
        Session session = Session.getDefaultInstance(props,
                       new javax.mail.Authenticator() {
                           protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                               return new javax.mail.PasswordAuthentication(fromAdd, pwd);
                           }
                       });

        session.setDebug(debug);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromAdd));
        } catch (MessagingException ex) {
            Logger.getLogger(StandardCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    
//    private class SMTPAuthenticator extends javax.mail.Authenticator {
//
//        public javax.mail.PasswordAuthentication getPasswordAuthentication() {
//            String username = SMTP_AUTH_USER;
//            String password = SMTP_AUTH_PWD;
//            return new javax.mail.PasswordAuthentication(username, password);
//        }
//    }

    public Date formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

   DateFormat originalFormat = new SimpleDateFormat(initDateFormat, Locale.ENGLISH);
    DateFormat targetFormat = new SimpleDateFormat(endDateFormat);
    Date fdate = originalFormat.parse(date);
    String formattedDate = targetFormat.format(fdate);  
    return fdate;
}
    public static void main(String[] args) {
           String text ="João Buchmuller";
       //System.out.println(StandardCode.getInstance().noNull(text));
        //System.out.println(text);
        
    }
}



