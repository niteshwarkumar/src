/*
 * LanguageAbbs.java
 *
 * Created on April 7, 2008, 10:38 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.global;

import app.project.Language;
import app.project.ProjectService;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author pp41387
 */
public class LanguageAbs {
    private static final LanguageAbs langAbs = new LanguageAbs();
    private static HashMap abs = null;
    private static long lastUpdate = 0;
    /** Creates a new instance of LanguageAbbs */
    private LanguageAbs() {
        
    }
    
    public static final synchronized LanguageAbs getInstance(){
       
        
            if(System.currentTimeMillis()-langAbs.lastUpdate > 24*60*60*1000){
                langAbs.refreshCache();
            }
    
        
        return langAbs;
    }

    public HashMap getAbs() {
        return abs;
    }

    
    public static void refreshCache(){
        //System.out.println("refreshing LanguageAbs");
        abs = new HashMap();
        List langs = ProjectService.getInstance().getLanguageList();
        ////System.out.println("langs.size()="+langs.size());
        for(int i=0; i<langs.size(); i++){
            //if(((Language)langs.get(i)).getAbr()!=null){
                abs.put( ((Language)langs.get(i)).getLanguage(), ((Language)langs.get(i)).getAbr());
                abs.put( ((Language)langs.get(i)).getAbr(),((Language)langs.get(i)).getLanguage() );
            //}
        }
        abs.put("All","All");
        langAbs.lastUpdate = System.currentTimeMillis();
        //System.out.println("finished refreshing LanguageAbs");
    }
  

    
}
