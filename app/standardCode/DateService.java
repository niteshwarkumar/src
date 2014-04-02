//DateService.java converts dates, such as from the
//javascript calendar, to a GregorianCalendar
//which is the new java standard for working
//with dates

package app.standardCode;
import java.util.*;

public class DateService {
    
    //use this instance during all accesses
    private static DateService instance = null;
    
    //allow any code to get this instance
    public static synchronized DateService getInstance()
    {
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new DateService();
		}
		return instance;
    }
    
    /** Creates a new instance of DateService */
    public DateService() {
    }
    
    public GregorianCalendar convertDate(String d) {
        GregorianCalendar gc = new GregorianCalendar();
        String[] parts = d.split("/");
        gc.set(Calendar.MONTH, Integer.parseInt(parts[0])-1);
        gc.set(Calendar.DATE, Integer.parseInt(parts[1]));
        if(parts[2].length() == 2)
            gc.set(Calendar.YEAR, Integer.parseInt(parts[2]) + 2000);
        else
            gc.set(Calendar.YEAR, Integer.parseInt(parts[2]));
        return gc;
    }
    
    

    public GregorianCalendar convertDate1(String d) {
        GregorianCalendar gc = new GregorianCalendar();
        String[] parts1 = d.split("T");
        String[] parts = parts1[0].split("-");
        gc.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
        gc.set(Calendar.DATE, Integer.parseInt(parts[2]));
        if(parts[0].length() == 2)
            gc.set(Calendar.YEAR, Integer.parseInt(parts[0]) + 2000);
        else
            gc.set(Calendar.YEAR, Integer.parseInt(parts[0]));
        return gc;
    }
    
    public int getYear(String x) {
        if(x.length() == 8) {
            x = x.substring(6, 8);
            return Integer.parseInt(x) + 2000;
        }            
        else {
            x = x.substring(6, 10);
            return Integer.parseInt(x);
        }
    }
    
    public int getMonth(String x) {
        x = x.substring(0, 2);
        return Integer.parseInt(x) - 1;
    }
    
    public int getDate(String x) {
        x = x.substring(3, 5);
        return Integer.parseInt(x);
    }
    
    public int getHour(String x) {
        x = x.substring(0, 2);
        return Integer.parseInt(x);
    }
    
    public int getMinute(String x) {
       x = x.substring(3, 5);
       return Integer.parseInt(x);
    }
    
    public boolean isAm(String x) {
        x = x.substring(6, 7);
        if(x.equals("A"))
            return true;
        else 
            return false;
    }

    public String ConvertyyyymmddTommddyyyy(String unFormatedDate){
        String formatedDate;
        try{
        String[] parts = unFormatedDate.split("-");
        formatedDate=parts[1]+"-"+parts[2]+"-"+parts[0];
        return (formatedDate);
        }catch(Exception e){return ("");}
    
    }

}
