//CompareResource.java contains the code for sorting resources
package app.resource;

import java.util.*;

public class CompareResourceByWordCount implements Comparator {

    private static CompareResourceByWordCount instance = null;

    public CompareResourceByWordCount() {
    }

    //return the instance of CompareResource
    public static synchronized CompareResourceByWordCount getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new CompareResourceByWordCount();
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
        Double i1 = null;
        Double i2 = null;

        if (bean1 instanceof Resource) { //if comparing LinTasks
            r1 = (Resource) bean1;
            r2 = (Resource) bean2;
            //i1 = r1.getResourceId();
            //i2 = r2.getResourceId();
            try {
                if (r1.getWordCount() != null && !r1.getWordCount().equalsIgnoreCase("null")) {
                    i1 = new Double(r1.getWordCount());
                } else {
                    i1 = new Double(0);
                }
            } catch (Exception e) {
                i1 = new Double(0);
            }

            try {
                if (r2.getWordCount() != null && !r2.getWordCount().equalsIgnoreCase("null")) {
                    i2 = new Double(r2.getWordCount());
                } else {
                    i2 = new Double(0);
                }
            } catch (Exception e) {
                i2 = new Double(0);
            }

        }

        //the compare of one field within two objects of same type
        return i1.compareTo(i2);

    }
}
