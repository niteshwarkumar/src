/*
 * MyCategoryURLGenerator.java
 *
 * Created on October 20, 2006, 6:56 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.tracker;

import org.jfree.chart.urls.StandardCategoryURLGenerator; 
import org.jfree.data.category.CategoryDataset; 
import java.util.Hashtable;

public class MyCategoryURLGenerator extends StandardCategoryURLGenerator 
{ 
        
        private Hashtable mouseOverMessages = new Hashtable(); 

        public String generateURL(CategoryDataset dataset, int series, 
                              int category) { 
        
     /*  String prefix = "drillDown"; 
        String seriesParameterName = "series"; 
        String        categoryParameterName = "month"; 

        
         
        Comparable seriesKey = dataset.getRowKey(series); 
        Comparable categoryKey = dataset.getColumnKey(category); 
        String url = prefix;
       url += "( {" + seriesParameterName + ": " 
            + "'" + seriesKey.toString() + "', "; 

        url += categoryParameterName + ": " 
            + "'" + categoryKey.toString() + "' } )"; 

        return url; */

            
            
        String mouseOverText = (String) mouseOverMessages.get(""+series+"_"+category);

       String url = " onmouseout=\"hideTooltip();\" "; 
           url +=  " onmouseover=\"showTooltip(event,'"+mouseOverText+"');return false;\" ";

        return url; 
    } 

    public Hashtable getMouseOverMessages() {
        return mouseOverMessages;
    }

    public void setMouseOverMessages(Hashtable mouseOverMessages) {
        this.mouseOverMessages = mouseOverMessages;
    }

} 

