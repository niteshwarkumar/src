/*
 * MyURLTagFragmentGenerator.java
 *
 * Created on October 19, 2006, 11:06 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.tracker;

/**
 *
 * @author Aleksandar
 */
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator; 


public class MyURLTagFragmentGenerator extends StandardURLTagFragmentGenerator 
{ 
        public String generateURLFragment(String jsText) { 
        return " href=\"javascript:doNothing();\" " + jsText + ""; 
    } 

} 

