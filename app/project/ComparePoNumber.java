//ComparePoNumber.java contains the code for sorting PoNumbers
//(needed for generating a new poNumber for each project)

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

public class ComparePoNumber implements Comparator 
{  
        private static ComparePoNumber instance = null;

	public ComparePoNumber()
	{

	}
        
        //return the instance of ComparePoNumber
	public static synchronized ComparePoNumber getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new ComparePoNumber();
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