//SecurityService.java contains the code to check that a user
//has met login criteria (basically, have the users logged in).  If the criteria is not met
//the user is redirected to the login page.

package app.security;

import java.util.List;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import javax.servlet.http.HttpSession;
import java.util.*;
import app.db.*;
import app.user.*;


public class SecurityService
{

	private static SecurityService instance = null;

	public SecurityService()
	{

	}
        
         //return the instance of SecurityService
	public static synchronized SecurityService getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new SecurityService();
		}
		return instance;
	}

        //make sure user has passed through login page
        public boolean checkForLogin(HttpSession session) {
            SecurityObject securityObject = null;
            try {
                securityObject = (SecurityObject) session.getAttribute("SecurityObject");   
            }
            catch(Exception e) {
                return false;
            }
            
            //the object doesn't exist for the user or it is not of type SecurityObject
            if(securityObject == null || !(securityObject instanceof SecurityObject)) {
                return false;
            }
            String lastSessionUpdate = (String) session.getAttribute("lastUserSessionTableUpdate");
            
            
            //CHECK IF THIS IS NEEDED BY READING A VAR FROM THE DB and UPDATING it
            //This should speed things up
            
            if( lastSessionUpdate==null || "null".equals(lastSessionUpdate) || (System.currentTimeMillis()- Long.parseLong(lastSessionUpdate) > 1000*60*10)){
                long lastUserSessionTableUpdate = UserService.getInstance().updateUserSessionInfo((String)session.getAttribute("username"));
                session.setAttribute("lastUserSessionTableUpdate",""+lastUserSessionTableUpdate);
            }
            //user has security object in session
            return true;
        }
        
        //make sure user has passed through login page
        public boolean checkForTrackerLogin(HttpSession session) {
            SecurityObject securityObject = null;
            try {
                securityObject = (SecurityObject) session.getAttribute("SecurityObject");   
            }
            catch(Exception e) {
                return false;
            }
            
            //the object doesn't exist for the user or it is not of type SecurityObject
            if(securityObject == null || !(securityObject instanceof SecurityObject)) {
                return false;
            }
            
            //user has security object in session
            return true;
        }
        
	

}

