//CompareQualityNumber.java contains the code for sorting Quality Numbers
//(needed for generating a new quality Number for each project)

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

public class CompareQualityNumber implements Comparator 
{  
        private static CompareQualityNumber instance = null;

	public CompareQualityNumber()
	{

	}
        
        //return the instance of CompareQualityNumber
	public static synchronized CompareQualityNumber getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new CompareQualityNumber();
		}
		return instance;
	}
        
      public int compare(Object string1, Object string2) {
        //cast to string
        String s1 = (String) string1;
        String s2 = (String) string2;
        
        //the compare of one string to another
        if(s1 != null) {
            return s2.compareTo(s1); 
        }
                
        //default
        return 0;
  }  

}