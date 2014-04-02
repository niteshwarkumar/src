//CompareResource.java contains the code for sorting resources
package app.resource;

import java.util.*;

public class CompareResourceByProjectCount implements Comparator {

    private static CompareResourceByProjectCount instance = null;

    public CompareResourceByProjectCount() {
    }

    //return the instance of CompareResource
    public static synchronized CompareResourceByProjectCount getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new CompareResourceByProjectCount();
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
        Integer i1 = null;
        Integer i2 = null;

        if (bean1 instanceof Resource) { //if comparing LinTasks
            r1 = (Resource) bean1;
            r2 = (Resource) bean2;
            //i1 = r1.getResourceId();
            //i2 = r2.getResourceId();
            try {
                if (r1.getProjectCount() != null && !r1.getProjectCount().equalsIgnoreCase("null")) {
                    i1 = new Integer(r1.getProjectCount());
                } else {
                    i1 = new Integer(0);
                }
            } catch (Exception e) {
                i1 = new Integer(0);
            }

            try {
                if (r2.getProjectCount() != null && !r2.getProjectCount().equalsIgnoreCase("null")) {
                    i2 = new Integer(r2.getProjectCount());
                } else {
                    i2 = new Integer(0);
                }
            } catch (Exception e) {
                i2 = new Integer(0);
            }

        }

        //the compare of one field within two objects of same type
        return i1.compareTo(i2);

    }
}
