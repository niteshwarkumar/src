/*
 * Comparators.java
 *
 * Created on November 26, 2006, 6:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.project;
import app.client.Client;
import app.resource.Resource;
import java.util.*;
import app.standardCode.*;
import app.quote.*;
 
/**
 *
 * @author Aleksandar
 */
public class Comparators {

    public String compareField = "";
    public String compareClass = "";
    public int multiplier = 1;
    
  public Comparator createComparator() {
    return new Comparator() {

      public int compare(Object o1, Object o2) {
        
        String s1 = "";
        String s2 = "";
        
        if("PROJECT".equals(compareClass)){
            Project p1 = (Project)o1;
            Project p2 = (Project)o2;
            if("Number".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getNumber());
                s2 = StandardCode.getInstance().noNull(p2.getNumber());
            }else if("Client".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getCompany().getCompany_name());
                s2 = StandardCode.getInstance().noNull(p2.getCompany().getCompany_name());
            }else if("DueDate".equals(compareField)){
                
                
                return compareDates(p1.getDueDate(),p2.getDueDate());
                
            }else if("DeliveryDate".equals(compareField)){
                return compareDates(p1.getDeliveryDate(),p2.getDeliveryDate());
            }else if("Status".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getStatus());
                s2 = StandardCode.getInstance().noNull(p2.getStatus());
            }else if("Amount".equals(compareField)){
                return compareDoubles(p1.getProjectAmount(), p2.getProjectAmount());
                
            }else if("ProjectManager".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getPm());
                s2 = StandardCode.getInstance().noNull(p2.getPm());
            }else if("StartDate".equals(compareField)){
                return compareDates(p1.getStartDate(),p2.getStartDate());
            }
            
            
        }else if("PROJECTSTATUS".equals(compareClass)){

          Project p1 = (Project)o1;
            Project p2 = (Project)o2;
            if("Project number".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getNumber());
                s2 = StandardCode.getInstance().noNull(p2.getNumber());
            }else if("Client PO".equals(compareField)){
                //getClientPO
                s1 = StandardCode.getInstance().noNull(p1.getClientPO());
                s2 = StandardCode.getInstance().noNull(p2.getClientPO());
              //  s1 = StandardCode.getInstance().noNull(p1.getCompany().getCompany_name());
               // s2 = StandardCode.getInstance().noNull(p2.getCompany().getCompany_name());
            }else if("Start Date".equals(compareField)){
                return compareDates(p1.getStartDate(),p2.getStartDate());
            }else if("Delivery Date".equals(compareField)){
                return compareDates(p1.getDeliveryDate(),p2.getDeliveryDate());
            }else if("Product".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getProduct());
                s2 = StandardCode.getInstance().noNull(p2.getProduct());
            //}else if("Amount".equals(compareField)){
             //   return compareDoubles(p1.getProjectAmount(), p2.getProjectAmount());

            }else if("Client Fee".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getFee());
                s2 = StandardCode.getInstance().noNull(p2.getFee());
           // }else if("StartDate".equals(compareField)){
            //    return compareDates(p1.getStartDate(),p2.getStartDate());
            }else if("Description".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getProductDescription());
                s2 = StandardCode.getInstance().noNull(p2.getProductDescription());
            }



      }else if("CLIENT".equals(compareClass)){
            Client p1 = (Client)o1;
            Client p2 = (Client)o2;
            
                   
              if("CompanyName".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getCompany_name());
                s2 = StandardCode.getInstance().noNull(p2.getCompany_name());
            }else if("Address".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getAddress_1());
                s2 = StandardCode.getInstance().noNull(p2.getAddress_1());
            }else if("City".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getCity());
                s2 = StandardCode.getInstance().noNull(p2.getCity());
            }else if("State".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getState_prov());
                s2 = StandardCode.getInstance().noNull(p2.getState_prov());
            }else if("ZipCode".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getZip_postal_code());
                s2 = StandardCode.getInstance().noNull(p2.getZip_postal_code());
            }else if("MainTelephone".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getMain_telephone_numb());
                s2 = StandardCode.getInstance().noNull(p2.getMain_telephone_numb());
            }else if("PM".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getProject_mngr());
                s2 = StandardCode.getInstance().noNull(p2.getProject_mngr());
            }else if("BackupPM".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getBackup_pm());
                s2 = StandardCode.getInstance().noNull(p2.getBackup_pm());
            }
        }else if("TEAM".equals(compareClass)){
            Resource p1 = (Resource)o1;
           
            Resource p2 = (Resource)o2;
            
                   
              if("ResourceName".equals(compareField)){
                  if(!p1.isAgency())
                  s1 = StandardCode.getInstance().noNull( p1.getFirstName()+p1.getLastName() );
                  else
                  s1 = StandardCode.getInstance().noNull(p1.getCompanyName());

                  if(!p2.isAgency())
                  s2 = StandardCode.getInstance().noNull( p2.getFirstName()+p2.getLastName());
                  else
                  s2 = StandardCode.getInstance().noNull(p2.getCompanyName());
                 

            }else if("Currency".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(p1.getCurrency());
                s2 = StandardCode.getInstance().noNull(p2.getCurrency());
            }else if("WordCount".equals(compareField)){
                Double si1 = new Double(0);
                Double si2 = new Double(0);
                
              if(!"".equals(StandardCode.getInstance().noNull(p1.getWordCount()))){
                si1 = new Double(p1.getWordCount().replaceAll(",", ""));
              }
                if(!"".equals(StandardCode.getInstance().noNull(p2.getWordCount()))){
                    si2 = new Double(p2.getWordCount().replaceAll(",", ""));
              }
              
               return compareDoubles(si1,si2);
                
            }else if("ProjectCount".equals(compareField)){
                
                Integer si1 = new Integer(p1.getProjectCount());
               Integer si2 = new Integer(p2.getProjectCount());
               return compareIntegers(si1,si2);
               
            }else if("AvgScore".equals(compareField)){
                if(p1.getProjectScoreAverage()==null){
                    p1.setProjectScoreAverage(new Double(0));
                }
                if(p2.getProjectScoreAverage()==null){
                    p2.setProjectScoreAverage(new Double(0));
                }
                return compareDoubles(p1.getProjectScoreAverage(),p2.getProjectScoreAverage());
                
            }else if("TRate".equals(compareField)){
             
             
                
                return compareDoubles(p1.getT(),p2.getT());
                
            }else if("ERate".equals(compareField)){
             
               
                
                return compareDoubles(p1.getE(),p2.getE());
                
            }else if("TERate".equals(compareField)){
             
               
                
                return compareDoubles(p1.getTe(),p2.getTe());
                
            }else if("PRate".equals(compareField)){
             
                return compareDoubles(p1.getP(),p2.getP());
                
            }
            
            
            
            
            
        }
        
        s1 = s1.toUpperCase();
        s2 = s2.toUpperCase();
        
        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);
        char v1[] = s1.toCharArray();
        char v2[] = s2.toCharArray();
        int pos = 0;

        while (n-- != 0) {
          char c1 = v1[pos];
          char c2 = v2[pos];
          if (c1 != c2) {
            return multiplier * (c1 - c2);
          }
          pos++;
        }
        return multiplier * (len1 - len2);
      }
    };
  }

  //public Comparator integerComparator() {
   // return new Comparator() {

      public int compareDoubles(Object o1, Object o2) {
        if(o1==null && o2==null){
              return 0;
          }else if(o1==null){
              return -1;
          }else if(o2==null){
              return 1;
          }
        int val1 = ((Double)o1).intValue();
        int val2 = ((Double)o2).intValue();
        return multiplier*(val1<val2 ? -1 : (val1==val2 ? 0 : 1));
      }
      
      public int compareIntegers(Object o1, Object o2) {
        if(o1==null && o2==null){
              return 0;
          }else if(o1==null){
              return -1;
          }else if(o2==null){
              return 1;
          }
        int val1 = ((Integer)o1).intValue();
        int val2 = ((Integer)o2).intValue();
        return multiplier*(val1<val2 ? -1 : (val1==val2 ? 0 : 1));
      }
    //};
  //}

  //public Comparator dateComparator() {
  //  return new Comparator() {

      public int compareDates(Object o1, Object o2) {
          if(o1==null && o2==null){
              return 0;
          }else if(o1==null){
              return -1;
          }else if(o2==null){
              return 1;
          }
        long val1 = ((Date)o1).getTime();
        long val2 = ((Date)o2).getTime();
        return multiplier*(val1<val2 ? -1 : (val1==val2 ? 0 : 1));

     // }
    //};
  }
      public int compareString(String s1, String s2){
           s1 = s1.toUpperCase();
        s2 = s2.toUpperCase();

        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);
        char v1[] = s1.toCharArray();
        char v2[] = s2.toCharArray();
        int pos = 0;

        while (n-- != 0) {
          char c1 = v1[pos];
          char c2 = v2[pos];
          if (c1 != c2) {
            return multiplier * (c1 - c2);
          }
          pos++;
        }
        return multiplier * (len1 - len2);
      }
            
}
