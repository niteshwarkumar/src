//CompareResource.java contains the code for sorting resources

package app.resource;

import java.util.*;

public class CompareResource implements Comparator 
{  
        private static CompareResource instance = null;

	public CompareResource()
	{

	}
        
        //return the instance of CompareResource
	public static synchronized CompareResource getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new CompareResource();
		}
		return instance;
	}
        
      public int compare(Object bean1, Object bean2) {
        // Get the value of the properties
        Resource r1 = null;
        Resource r2 = null;
                
        //values for comparing two field types
        //Integer i1 = null;
        //Integer i2 = null;    
        String i1 = null;
        String i2 = null;    
                
        if(bean1 instanceof Resource) { //if comparing LinTasks
            r1 = (Resource) bean1;
            r2 = (Resource) bean2;
            //i1 = r1.getResourceId();
            //i2 = r2.getResourceId();
            i1 = r1.getLastName();
            if(i1 == null || "".equals(i1)){
                i1 = r1.getCompanyName();
            }
            
            i2 = r2.getLastName();
            if(i2==null || "".equals(i2)){
                i2 = r2.getCompanyName();
            }
            
        }
        
        //the compare of one field within two objects of same type
        return i1.compareTo(i2);       
      
  }  

}