/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import app.client.Client;
import app.client.ClientService;
import app.extjs.helpers.TeamHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nishika
 */
public class ResourceClientSaveAction extends Action {

  /*
   * To change this template, choose Tools | Templates
   * and open the template in the editor.
   */
  public ActionForward execute(ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response)
          throws Exception {


    String jsonClient = request.getParameter("ClientResourcesJSON");
    String resource = request.getParameter("resourceViewId");
    //String ClientResource = request.getParameter("ClientResource");


    ResourceService.getInstance().unlinkResourceClient(Integer.parseInt(resource));

    if (jsonClient != null && !"".equals(jsonClient)) {
      JSONArray products = new JSONArray(jsonClient);

      for (int i = 0; i < products.length(); i++) {
        JSONObject j = (JSONObject) products.get(i);

        ResourceClient rc = new ResourceClient();

   try{
        Client cl = ClientService.getInstance().getSingleClientByName(j.getString("client"));
        rc.setClient(j.getString("client"));
        String level = "";
        String primary = "";
        String language = "";
        String secondry = "";
        
        if(j.has("level")){level = j.getString("level");}
        if(j.has("primary")){primary = j.getString("primary");}
        if(j.has("language")){language = j.getString("language");}
        if(j.has("secondry")){secondry = j.getString("secondry");}
        
        try{rc.setLevel(level);}catch(Exception e){}
        try{rc.setPrimry(primary);}catch(Exception e){}
        try{rc.setLanguage(language);}catch(Exception e){}
       // rc.setPrimaryCount(resource);
        try{rc.setSecondry(secondry);}catch(Exception e){}
        
   
        try{rc.setPrimaryCount(TeamHelper.getResourceTaskCount(Integer.parseInt(resource), cl.getClientId(), primary, language));}catch(Exception e){e.printStackTrace();}
        try{rc.setSecondryCount(TeamHelper.getResourceTaskCount(Integer.parseInt(resource), cl.getClientId(), secondry,language));}catch(Exception e){e.printStackTrace();}
        rc.setResourceId(Integer.parseInt(resource));
       // rc.setSecondryCount(jsonClient);

        ResourceService.getInstance().addResourceClient(rc);
        }catch(Exception e){}
      }

    }
    request.setAttribute("resourceViewId", resource);
    //request.setAttribute("ClientResource", ClientResource);

    return (mapping.findForward("Success"));

  }
}