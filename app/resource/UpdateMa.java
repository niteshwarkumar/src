//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;
import org.json.JSONArray;
import org.json.JSONObject;


public final class UpdateMa extends Action {


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
    @Override
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
        String jsonMA = request.getParameter("maJSON");

         try {


        ////System.out.println("jsonProducts="+jsonProducts);

        //First delete all products, and then re-insert it
        if(jsonMA!=null && !"".equals(jsonMA)){
            JSONArray MA = new JSONArray(jsonMA);
           int i=0;
            Mechanical ma=ResourceService.getInstance().getMaScore();
            JSONObject j = (JSONObject)MA.get(i++); ma.setNativeSpeaker(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setFt(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setPt(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setNativeLocation(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setProfession(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setMedicalExpirience(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setTechExpirience(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setLegalExpirience(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setMarketingExpirience(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIso9001(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIso13485(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setOtherIso(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIndustry1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIndustry2(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIndustry3(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setCountryCert1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setCountryCert2 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setCountryCert3(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setReference(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setSizeFullTimeEmp(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setSize1Score(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setSizeVolume(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setSize2Score(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setLocation1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setLocation2(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setLocation3(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setNicheLangs(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setNicheSpecialization(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setIso14971(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setTrados(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setSdlx(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDejavu(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setTransit(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setCatalyst(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setOtherTool(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpFullTime(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpContentExpert1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpContentExpert2 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpContentExpert3 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAtools1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAtools2 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAtools3 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAiso1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAiso2 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAiso3 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAiso4 (j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAindustry1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAindustry2(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAcountryCert1(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAcountryCert2(j.getInt("dropdownValue"));
                 j = (JSONObject)MA.get(i++); ma.setDtpMAcountryCert3(j.getInt("dropdownValue"));

                 ResourceService.getInstance().updateMechanical(ma);

               // HrHelper.saveDropdown(pr);
               // session.save(pr);

        }}catch(Exception e){}

return (mapping.findForward("Success"));
    }}

