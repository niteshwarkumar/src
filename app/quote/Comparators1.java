/*
 * Comparators.java
 *
 * Created on November 26, 2006, 6:43 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.quote;
import java.util.*;
import app.standardCode.*;
import app.project.*;

/**
 *
 * @author Aleksandar
 */
public class Comparators1 {

    public String compareField = "";
    public String compareClass = "";
    public int multiplier = 1;

  public Comparator createComparator() {
    return new Comparator() {

      public int compare(Object o1, Object o2) {

        String s1 = "";
        String s2 = "";

         if("QUOTE".equals(compareClass)){

            Quote1 q1 = (Quote1)o1;
            Quote1 q2 = (Quote1)o2;
        
          //  Project p2=(Project)o2;

            if("Quote Number".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(q1.getNumber());
                s2 = StandardCode.getInstance().noNull(q2.getNumber());

                //s1 = StandardCode.getInstance().noNull(q1.getProject().getQuotes());
                //s2 = StandardCode.getInstance().noNull(p2.getCompany().getCompany_name());

            }else if("Product".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(q1.getProject().getProduct());
                s2 = StandardCode.getInstance().noNull(q2.getProject().getProduct());
          //      s2 = StandardCode.getInstance().noNull(p2.getProduct());
            //}else if("Amount".equals(compareField)){
             //   return compareDoubles(p1.getProjectAmount(), p2.getProjectAmount());

                 // }else if("StartDate".equals(compareField)){
            //    return compareDates(p1.getStartDate(),p2.getStartDate());
            }else if("Description".equals(compareField)){
              s1 = StandardCode.getInstance().noNull(q1.getProject().getProjectDescription());
              s2 = StandardCode.getInstance().noNull(q2.getProject().getProjectDescription());
            }else if("Status".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(q1.getStatus());
                s2 = StandardCode.getInstance().noNull(q2.getStatus());

            }else if("Company".equals(compareField)){
                s1 = StandardCode.getInstance().noNull(q1.getProject().getCompany().getCompany_name());
                s2 = StandardCode.getInstance().noNull(q2.getProject().getCompany().getCompany_name());
            }else if("Quote Date".equals(compareField)){
                return compareDates(q1.getQuoteDate(),q2.getQuoteDate());
            }else if("Quote Amount".equals(compareField)){
                return compareDoubles(q1.getQuoteDollarTotal(), q2.getQuoteDollarTotal());
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
