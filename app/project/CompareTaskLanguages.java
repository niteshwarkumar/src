//CompareTaskLin.java contains the code for sorting Lin Tasks

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

public class CompareTaskLanguages implements Comparator 
{  
        private static CompareTaskLanguages instance = null;

	public CompareTaskLanguages()
	{

	}
        
        //return the instance of CompareTask
	public static synchronized CompareTaskLanguages getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new CompareTaskLanguages();
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
        if(bean1==null || bean2==null){
            return 0;
        }
        if(bean1 instanceof LinTask && bean2 instanceof LinTask) { //if comparing LinTasks
            lt1 = (LinTask) bean1;
            lt2 = (LinTask) bean2;
            s1 = lt1.getTargetLanguage();
            s2 = lt2.getTargetLanguage();
        }else  if(bean1 instanceof DtpTask && bean2 instanceof DtpTask) { //if comparing LinTasks
            dt1 = (DtpTask) bean1;
            dt2 = (DtpTask) bean2;
            s1 = dt1.getTargetLanguage();
            s2 = dt2.getTargetLanguage();
        }else  if(bean1 instanceof EngTask && bean2 instanceof EngTask) { //if comparing LinTasks
            et1 = (EngTask) bean1;
            et2 = (EngTask) bean2;
            s1 = et1.getTargetLanguage();
            s2 = et2.getTargetLanguage();
        }
        
        //the compare of one field within two objects of same type
        if(s1 != null && s2 != null) {
            return s1.compareTo(s2); 
        }
        else if(i1 != null) {
            return i1.compareTo(i2);
        }
        
        //default
        return 0;
  }  

}