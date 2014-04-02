/*
 * MyToolTipGenerator.java
 *
 * Created on October 19, 2006, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.tracker;

import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import java.util.Hashtable;

class MyToolTipGenerator extends StandardCategoryToolTipGenerator
	{
private Hashtable toolTipMessages = null;   
		
        public String generateToolTip(CategoryDataset dataset, int row, int column)  { 
        
            
			String result = null; 
            //Number value = dataset.getValue(row, column); 
            result = (String) toolTipMessages.get(""+row+"_"+column);
                        
            
            /*if (row==0) { 
                result = "Expected date: 10/10/2006. Actual Completion Date: 10/10/2006"; 
            }else if (row==1) { 
                result = "Expected date: 10/13/2006. Actual Completion Date: 10/12/2006"; 
            }else  { 
                result = "Expected date: 10/15/2006"; 
            }   */ 
            if(result==null){
                result = "";
            }
            return result; 
            
        } 

    public Hashtable getToolTipMessages() {
        return toolTipMessages;
    }

    public void setToolTipMessages(Hashtable toolTipMessages) {
        this.toolTipMessages = toolTipMessages;
    }


	};
