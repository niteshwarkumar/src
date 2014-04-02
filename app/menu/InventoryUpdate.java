/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.menu;

/**
 *
 * @author Neil
 */
import java.util.List;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryUpdate extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String jsonHW = request.getParameter("jsonHW");
        String jsonHWWish = request.getParameter("jsonHWWish");
        String jsonSW = request.getParameter("jsonSW");
        String jsonSWWish = request.getParameter("jsonSWWish");
        List HWList = MenuService.getInstance().getHWList();
        List HWWishList = MenuService.getInstance().getHWWishList();
        List SWList = MenuService.getInstance().getSWList();
        List SWWishList = MenuService.getInstance().getSWWishList();
        Hardware hw;
        Hardware hwWish;
        Sw S;
        Sw SwWish;
        int flag = 1;


        if (jsonHW != null && !"".equals(jsonHW)) {
            JSONArray hwl = new JSONArray(jsonHW);
            //    System.out.println("json"+hwl.length()+"         Hardware   "+jsonHW.length());
            for (int i = 0; i < hwl.length(); i++) {
                flag = 1;
                if (i < HWList.size()) {
                    try {
                        hw = (Hardware) HWList.get(i);
                    } catch (Exception e) {
                        hw = new Hardware();
                    }

                    //  if(hw.getWish().TRUE)flag=0;
                } else {
                    hw = new Hardware();
                }
                if (flag == 1) {

                    JSONObject j = (JSONObject) hwl.get(i);
                    System.out.println("setEquipmentsetEquipmentsetEquipment" + j.getString("Equipment"));
                    hw.setDescription(j.getString("Description"));
                    hw.setEquipment(j.getString("Equipment"));
                    hw.setDetail(j.getString("Detail"));
                    hw.setMake(j.getString("Make"));
                    hw.setModel(j.getString("Model"));

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("Purchase")).getTime());
                        hw.setPurchase_date(d2);
                    } catch (Exception ex) {
                    }
                    // hw.setPurchase_date(j.getString(""));
                    hw.setWish(Boolean.FALSE);
                    MenuService.getInstance().updateHWList(hw);
                }
            }
        }


        if (jsonHWWish != null && !"".equals(jsonHWWish)) {
            JSONArray hwl1 = new JSONArray(jsonHWWish);
            for (int i = 0; i < hwl1.length(); i++) {
                flag = 1;
                if (i < HWList.size()) {
                    try {
                        hwWish = (Hardware) HWWishList.get(i);
                    } catch (Exception e) {
                        hwWish = new Hardware();
                    }//if(hwWish.getWish().FALSE)flag=0;
                } else {
                    hwWish = new Hardware();
                }
                if (flag == 1) {
                    JSONObject j = (JSONObject) hwl1.get(i);
                    System.out.println("setEquipmentsetEquipmentsetEquipmentsdfvsdfvsdfvsd" + j.getString("Equipment"));
                    hwWish.setDescription(j.getString("Description"));
                    hwWish.setEquipment(j.getString("Equipment"));
                    hwWish.setDetail(j.getString("Detail"));
                    hwWish.setMake(j.getString("Make"));
                    hwWish.setModel(j.getString("Model"));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("Purchase")).getTime());
                        hwWish.setPurchase_date(d2);
                    } catch (Exception ex) {
                    }
                    // hw.setPurchase_date(j.getString(""));
                    hwWish.setWish(Boolean.TRUE);
                    MenuService.getInstance().updateHWList(hwWish);
                }
            }
        }


        if (jsonSW != null && !"".equals(jsonSW)) {
            JSONArray Swl = new JSONArray(jsonSW);
            //    System.out.println("json"+hwl.length()+"         Hardware   "+jsonHW.length());
            for (int i = 0; i < Swl.length(); i++) {
                flag = 1;
                if (i < SWList.size()) {
                    try {
                        S = (Sw) SWList.get(i);
                    } catch (Exception e) {
                        S = new Sw();
                    }
                    //  if(S.getWish().TRUE)flag=0;
                } else {
                    S = new Sw();
                }
                if (flag == 1) {
                    JSONObject j = (JSONObject) Swl.get(i);
                    S.setApplication(j.getString("Application"));
                    S.setDescription(j.getString("Description"));
                    S.setPlatform(j.getString("Platform"));
                    S.setVersion(j.getString("Version"));
                    S.setLanguage(j.getString("Languages"));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("Purchase")).getTime());
                        S.setPurchase_date(d2);
                    } catch (Exception ex) {
                    }
                    // S.setPurchase_date(j.get("Purchase"));
                    S.setWish(Boolean.FALSE);
                    MenuService.getInstance().updateSWList(S);
                }
            }
        }
        if (jsonSWWish != null && !"".equals(jsonSWWish)) {
            JSONArray Swl = new JSONArray(jsonSWWish);
            for (int i = 0; i < Swl.length(); i++) {
                if (i < SWWishList.size()) {
                    try {
                        SwWish = (Sw) SWWishList.get(i);
                    } catch (Exception e) {
                        SwWish = new Sw();
                    }
                } else {
                    SwWish = new Sw();
                    // if(SwWish.getWish().FALSE)flag=0;
                }
                if (flag == 1) {
                    JSONObject j = (JSONObject) Swl.get(i);
                    SwWish.setApplication(j.getString("Application"));
                    SwWish.setDescription(j.getString("Description"));
                    SwWish.setPlatform(j.getString("Platform"));
                    SwWish.setVersion(j.getString("Version"));
                    SwWish.setLanguage(j.getString("Languages"));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("Purchase")).getTime());
                        SwWish.setPurchase_date(d2);
                    } catch (Exception ex) {
                    }
                    // hw.setPurchase_date(j.getString(""));
                    SwWish.setWish(Boolean.TRUE);
                    MenuService.getInstance().updateSWList(SwWish);
                }
            }
        }

        return (mapping.findForward("Success"));
    }
}
