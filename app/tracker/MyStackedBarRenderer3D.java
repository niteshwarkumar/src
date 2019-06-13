/*
 * MyStackedBarRenderer3D.java
 *
 * Created on October 19, 2006, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.tracker;

/**
 *
 * @author PP41387
 */
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import java.awt.Color;
import java.awt.Paint; 
import java.util.Hashtable;


class MyStackedBarRenderer3D extends StackedBarRenderer3D
{
	private Hashtable toolTipMessages = null;   
	//Private Hashtable allMilestones:
	//key: series_item, value Millan object.
	//if Millan's completed date == null, paint it red, otherwise paint it gray
	
	public Paint getItemPaint(int series, int item) { 
            // here we assume we're working with the primary dataset 
            //XYDataset dataset = getPlot().getDataset(); 
            //double value = dataset.getYValue(series, item); 
            ////System.out.println("item="+item+",series="+series);
            
            String result = (String) getToolTipMessages().get(""+series+"_"+item);
            if(result==null){
                result = "";
            }
            if (result.indexOf("Completed")>-1) { 
                return Color.LIGHT_GRAY; 
            } else { 
                return Color.blue; 
            } 
            
            /*if (series<2) { 
                return Color.gray; 
            } else { 
                return Color.red; 
            } */
            
        } 

    public Hashtable getToolTipMessages() {
        return toolTipMessages;
    }

    public void setToolTipMessages(Hashtable toolTipMessages) {
        this.toolTipMessages = toolTipMessages;
    }


}

