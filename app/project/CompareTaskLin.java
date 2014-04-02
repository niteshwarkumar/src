//CompareTaskLin.java contains the code for sorting Lin Tasks

package app.project;

import java.util.*;

public class CompareTaskLin implements Comparator 
{  
        private static CompareTaskLin instance = null;

	public CompareTaskLin()
	{

	}
        
        //return the instance of CompareTask
	public static synchronized CompareTaskLin getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new CompareTaskLin();
		}
		return instance;
	}
        
      public int compare(Object bean1, Object bean2) {
        // Get the value of the properties
        LinTask lt1 = null;
        LinTask lt2 = null;
        EngTask et1 = null;
        EngTask et2 = null;
        DtpTask dt1 = null;
        DtpTask dt2 = null;
        OthTask ot1 = null;
        OthTask ot2 = null;
        
        //values for comparing two field types
        String s1 = null;
        String s2 = null;        
        Integer i1 = null;
        Integer i2 = null;
        
        if(bean1 instanceof LinTask) { //if comparing LinTasks
            lt1 = (LinTask) bean1;
            lt2 = (LinTask) bean2;
            i1 = lt1.getOrderNum();
            i2 = lt2.getOrderNum();
        }
        
        //the compare of one field within two objects of same type
        if(s1 != null) {
            return s1.compareTo(s2); 
        }
        else if(i1 != null) {
            return i1.compareTo(i2);
        }
        
        //default
        return 0;
  }  

}