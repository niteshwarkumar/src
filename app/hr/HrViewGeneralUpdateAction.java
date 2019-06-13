//HrViewGeneralUpdateAction.java gets updated hr info
//from a form.  It then uploads the new values for
//this employee to the db

package app.hr;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.StandardCode;


public final class HrViewGeneralUpdateAction extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {

	// Extract attributes we will need
	MessageResources messages = getResources(request);	

	// save errors
	ActionMessages errors = new ActionMessages();
        
        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) { 
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        
	//which user to update from hidden value in form
        String id = request.getParameter("userViewId");  
        //get the user to be updated from db
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(id));
        
         if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
//            return (mapping.findForward("accessDenied"));
//        }//END PRIVS check that admin user is viewing this page
        
        //PRIVS check that logged-in user is editing this employee record 
        if(!request.getSession(false).getAttribute("username").toString().equals(u.getUsername())) {//this user is not editing only there employee information
            return (mapping.findForward("Success"));
        }//END PRIVS check that logged-in user is editing this employee record 
      }  
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        
        String firstName = (String) uvg.get("firstName");
        String lastName = (String) uvg.get("lastName");
        FormFile picture = (FormFile) uvg.get("picture");
        FormFile signature = (FormFile) uvg.get("signature");
        
        String position = (String) uvg.get("position");
        Position1 p = UserService.getInstance().getSinglePosition(position);
        
        String department = (String) uvg.get("department");
        Department d = UserService.getInstance().getSingleDepartment(department);
        
        String location = (String) uvg.get("location");
        Location l = UserService.getInstance().getSingleLocation(location);
        
        String homeAddress = (String) uvg.get("homeAddress");
        String homeCity = (String) uvg.get("homeCity");
        String homeState = (String) uvg.get("homeState");
        String homeCountry = (String) uvg.get("homeCountry");
        String homeZip = (String) uvg.get("homeZip");
        String homePhone = (String) uvg.get("homePhone");
        String homeCell = (String) uvg.get("homeCell");
        String homeEmail1 = (String) uvg.get("homeEmail1");
        String homeEmail2 = (String) uvg.get("homeEmail2");
        String workAddress = (String) uvg.get("workAddress");
        String workCity = (String) uvg.get("workCity");
        String workState = (String) uvg.get("workState");
        String workCountry = (String) uvg.get("workCountry");
        String workZip = (String) uvg.get("workZip");
        String workPhone = (String) uvg.get("workPhone");
        String workPhoneEx = (String) uvg.get("workPhoneEx");
        String workCell = (String) uvg.get("workCell");
        String workEmail1 = (String) uvg.get("workEmail1");
        String workEmail2 = (String) uvg.get("workEmail2");
        String emergencyContactName = (String) uvg.get("emergencyContactName");
        String emergencyContactRelation = (String) uvg.get("emergencyContactRelation");
        String emergencyContactPhone = (String) uvg.get("emergencyContactPhone");
        String activeLevel = (String) uvg.get("activeLevel");
        String skypeId = (String) uvg.get("skypeId");
        
        //process picture upload
        String saveFileName = null;
        if(picture.getFileName().length() > 0) {
            String fileName = picture.getFileName();
            byte[] fileData  = picture.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + saveFileName);        
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }

                //process Signature upload
        String saveFileName1 = null;
        if(signature.getFileName().length() > 0) {
            String fileName1 = signature.getFileName();
            byte[] fileData1  = signature.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName1 = String.valueOf(gen.nextInt()) + fileName1;
            File saveFile1 = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + saveFileName1);
            FileOutputStream out = new FileOutputStream(saveFile1);
            out.write(fileData1);
            out.flush();
            out.close();
        }
        
        //update the user's values
        u.setFirstName(firstName);
        u.setLastName(lastName);
        
        if(saveFileName != null) { //was a new picture uploaded
            u.setPicture(saveFileName);
        }
        if(saveFileName1 != null) { //was a new picture uploaded
            u.setSignature(saveFileName1);
        }
        
        u.setPosition(p);
        u.setDepartment(d);
        u.setLocation(l);
        u.setHomeAddress(homeAddress);
        u.setHomeCity(homeCity);
        u.setHomeState(homeState);
        u.setHomeCountry(homeCountry);
        u.setHomeZip(homeZip);
        u.setHomePhone(homePhone);
        u.setHomeCell(homeCell);
        u.setHomeEmail1(homeEmail1);
        u.setHomeEmail2(homeEmail2);
        u.setWorkAddress(workAddress);
        u.setWorkCity(workCity);
        u.setWorkState(workState);
        u.setWorkCountry(workCountry);
        u.setWorkZip(workZip);
        u.setWorkPhone(workPhone);
        u.setWorkPhoneEx(workPhoneEx);
        u.setWorkCell(workCell);
        u.setWorkEmail1(workEmail1);
        u.setWorkEmail2(workEmail2);
        u.setEmergencyContactName(emergencyContactName);
        u.setEmergencyContactRelation(emergencyContactRelation);
        u.setEmergencyContactPhone(emergencyContactPhone);
        u.setActiveLevel(activeLevel);
        u.setSkypeId(skypeId);
        
        //set updated values to db
        UserService.getInstance().updateUser(u);
        
        //place user id into request for display
        request.setAttribute("user", u);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
