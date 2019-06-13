/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.ToolManager;

/**
 *
 * @author niteshwar
 */
public class TemplateUtil {

    private static TemplateUtil self = null;
    private VelocityEngine vEngine;
//    private HashMap<String, Template> templMap;
    public static boolean bDebugMode = false;

    public static TemplateUtil getInstance() {
        if (self == null) {
            // two or more threads might be here!!!
            // critical section
            synchronized (TemplateUtil.class) { // thread protection,
                // double-check idiom
                if (self == null) { // must check again as one of the blocked
                    // threads can still enter
                    try {
                        self = new TemplateUtil();
                    } catch (Exception e) {
                        System.err.println("Error instantiting TemplteUtil: " + e);
                        self = null;
                    }
                }
            }
        }
        return (self);
    }

    private TemplateUtil() {
        try {

            vEngine = new VelocityEngine();
//             props.put("file.resource.loader.path",
//            if (bDebugMode) {
//                vEngine.setProperty("file.resource.loader.path", "/Users/abhisheksingh/Project/templates/");
//            } else {
                vEngine.setProperty("file.resource.loader.path", "C://templates/");
//            }
//            vEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//            vEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//            vEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
//            vEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
//            vEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
            vEngine.init();

        } catch (Exception e) {

        }
    }

    public synchronized Template getTemplate(String name) throws ResourceNotFoundException, ParseErrorException, Exception {

        Template t = vEngine.getTemplate(name + ".vm");
        return t;

    }

    public static Context getVelocityContext() {
        ToolManager manager = new ToolManager();
        return manager.createContext();

    }
}
