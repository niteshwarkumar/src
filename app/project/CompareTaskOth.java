//CompareTaskOth.java contains the code for sorting Oth Tasks

package app.project;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.client.*;
import app.project.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import java.util.*;
import java.text.*;

public class CompareTaskOth implements Comparator 
{  
        private static CompareTaskOth instance = null;

	public CompareTaskOth()
	{

	}
        
        //return the instance of CompareTask
	public static synchronized CompareTaskOth getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new CompareTaskOth();
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
        
        if(bean1 instanceof OthTask) { //if comparing OthTasks
            ot1 = (OthTask) bean1;
            ot2 = (OthTask) bean2;
            s1 = ot1.getTaskName();
            s2 = ot2.getTaskName();
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