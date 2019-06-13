/*
 * CreateJFreeChart.java
 *
 * Created on October 19, 2006, 12:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.tracker;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.*;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import java.util.*;
import app.project.*;
/**
 *
 * @author PP41387
 */
public class CreateJFreeChart {
//    
//    
//    private Hashtable translateTasks;
//    private Project p = null;
//   
//    public int numberOfLanguages = 0;
//    public int numberOfInspections = 0;
//    /** Creates a new instance of CreateJFreeChart */
//    public CreateJFreeChart() {
//        
//        translateTasks = new Hashtable();
//        //task name, inspection milestone name combo                                                  
//        translateTasks.put("Preliminary Analysis","Preliminary Analysis");
//        translateTasks.put("Verification of Integrity","Verification of Integrity of Submitted Materials");
//        translateTasks.put("Preparation","Preparation and Start-Up");
//        translateTasks.put("Glossary Creation","Compilation of Glossaries");
//        translateTasks.put("In-Process QA","QA");
//        translateTasks.put("Proofreading / Linguistic QA","Proofreading");
//        translateTasks.put("Final QA","Final QA");
//        translateTasks.put("Translation","Translation");
//        translateTasks.put("Editing","Editing");
//        translateTasks.put("Desktop Publishing","Desktop Publishing");
//        translateTasks.put("Format Proofing","Format Proofing");
//        translateTasks.put("Functionality Testing","Functionality Testing");
//        translateTasks.put("Close-out","Close-out");
//        translateTasks.put("Delivery","Delivery");
//        translateTasks.put("In-Country / Client Review","In-country Client Review");
//        translateTasks.put("Evaluation","Evaluation of Client Review");
//        translateTasks.put("Compilation","Compilation of Style Sheets");
//        translateTasks.put("Testing / Troubleshooting","Engineering testing, QA, & Troubleshooting");
//       
//    }
//    
//    public JFreeChart createChart(){
//        
//        /*******************************************************/
//        //Set tracker checkbox values
//        Hashtable millanValues = new Hashtable();
//        List trackerItems = TrackerService.getInstance().getMilLanList(p.getProjectId());
//        if(trackerItems!=null){    
//        for(ListIterator iter = trackerItems.listIterator(); iter.hasNext(); ) {
//                MilestoneLanguage millan = (MilestoneLanguage) iter.next();
//                ////THIS FORM: MILLAN_<%=iDisplay.getInspectionId()+"_"+languageId+"_"+iDisplay.getProject().getProjectId()
//                millanValues.put("MILLAN_"+millan.getMilestone_id()+"_"+millan.getLang_id()+"_"+millan.getProject_id(),millan);
//            }
//        }
//        
//        Set sources = p.getSourceDocs();
//        Hashtable totalLanguages = new Hashtable();
//        Hashtable expectedDates = new Hashtable();
//        //for each source
//        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
//            SourceDoc sd = (SourceDoc) sourceIter.next();
//                
//            //for each target of this source
//            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
//                TargetDoc td = (TargetDoc) linTargetIter.next();
//                
//                //for each lin Task of this target
//                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
//                    LinTask lt = (LinTask) linTaskIter.next();
//                   
//                    if(!"All".equalsIgnoreCase(lt.getTargetLanguage())){
//                        
//                    totalLanguages.put(lt.getTargetLanguage(),ProjectService.getInstance().getLanguageId(lt.getTargetLanguage())); 
//                        if(translateTasks.get(lt.getTaskName())!=null && lt.getDueDateDate()!=null){
//                            expectedDates.put((Integer)totalLanguages.get(lt.getTargetLanguage())+"_"+(String)translateTasks.get(lt.getTaskName()),lt.getDueDateDate());
//                            ////System.out.println("adding:"+(Integer)totalLanguages.get(lt.getTargetLanguage())+"_"+(String)translateTasks.get(lt.getTaskName())+","+lt.getDueDateDate());
//                        }
//                    }
//                    
//                    }
//                //for each eng Task of this target
//                for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
//                    
//                    EngTask et = (EngTask) engTaskIter.next();
//                     if(!"All".equalsIgnoreCase(et.getTargetLanguage())){
//                         
//                    totalLanguages.put(et.getTargetLanguage(),ProjectService.getInstance().getLanguageId(et.getTargetLanguage()));
//                     if(translateTasks.get(et.getTaskName())!=null && et.getDueDateDate()!=null){
//                            expectedDates.put((Integer)totalLanguages.get(et.getTargetLanguage())+"_"+(String)translateTasks.get(et.getTaskName()),et.getDueDateDate());
//                        }
//                   }
//                }
//                
//                //for each dtp Task of this target
//                for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
//                    
//                    
//                    DtpTask dt = (DtpTask) dtpTaskIter.next();
//                    
//                     if(!"All".equalsIgnoreCase(dt.getTargetLanguage())){
//                        
//                    totalLanguages.put(dt.getTargetLanguage(),ProjectService.getInstance().getLanguageId(dt.getTargetLanguage()));
//                    
//                    if(translateTasks.get(dt.getTaskName())!=null && dt.getDueDateDate()!=null){
//                            expectedDates.put((Integer)totalLanguages.get(dt.getTargetLanguage())+"_"+(String)translateTasks.get(dt.getTaskName()),dt.getDueDateDate());
//                        }
//                 }
//                    
//                }
//                
//                //for each oth Task of this target
//                for(Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
//                    OthTask ot = (OthTask) othTaskIter.next();
//                     if(!"All".equalsIgnoreCase(ot.getTargetLanguage())){
//                         
//                        totalLanguages.put(ot.getTargetLanguage(),ProjectService.getInstance().getLanguageId(ot.getTargetLanguage()));
//                     }
//                                          
//                }       
//            
//            }
//
//        } 
//        
//        Vector languages = new Vector();
//         Enumeration langs = totalLanguages.keys();
//        while(langs.hasMoreElements()) { 
//          languages.add((String)langs.nextElement());
//        }
//        Set inspections = p.getInspections();
//       Inspection[] inspectionsArray = (Inspection[]) inspections.toArray(new Inspection[0]);
//       int countavailinspect = 0;
//       for(int i=0; i< inspectionsArray.length; i++){
//           if(inspectionsArray[i].isApplicable()){
//                countavailinspect++;
//                
//           }
//        }
//       numberOfInspections = countavailinspect+1;
//       
//       String[] milestones = new String[countavailinspect+1];
//       int milestoneCount = 1;
//       String startDate = "";
//       java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
//       if(p.getStartDate()!=null){
//           startDate = "("+sdf.format(p.getStartDate())+")";
//       }
//       String endDate = "";
//       if(p.getDueDate()!=null){
//           endDate = "("+sdf.format(p.getDueDate())+")";
//       }
//       
//       milestones[0]= startDate + " Start";
//       
//       Inspection[] newInspectArr = new Inspection[countavailinspect+1];
//       
//       for(int i=0; i< inspectionsArray.length; i++){
//           
//           if(inspectionsArray[i].isApplicable()){
//                String mlst = inspectionsArray[i].getMilestone();
//                if(mlst.length()>15){
//                   mlst = mlst.substring(0,15);
//                 }
//               milestones[milestoneCount]=mlst;
//              
//               newInspectArr[milestoneCount]=inspectionsArray[i];
//                ////System.out.println("adding milestone:"+milestones[milestoneCount]);
//                milestoneCount++;
//           }
//        }
//       
//       milestones[countavailinspect]+=" "+endDate;
//        //milestones[milestoneCount]="Finish";
//      // //System.out.println("about to create valueAxis with milestones.length="+milestones.length);
//       /**************************************************************************************/
//      
//        //X-AXIS (Create SymbolicAxis)
//		//1) TODO: Get list of all selected Milestones ("Start" is a dummy one, add it manualy)
//       //String[] milestones = new String[]{"Start","Inspection", "Editing", "Proofreading","QA", "Changes ", "Final"}; 
//		SymbolAxis valueAxis = new SymbolAxis("Milestones",milestones); 
//		valueAxis.setGridBandsVisible(false); 
//		valueAxis.setTickLabelsVisible(true); 
//		////System.out.println("created valueAxis");
//		//Y-AXIS
//		final CategoryAxis categoryAxis = new CategoryAxis("Languages");
//		
//		//Initialize Data
//		//String[] languagesArr = new String[]{"German","Japanese", "Italian", "Korean"}; 
//		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		//2) TODO: for every milestone/lang combo, dataset.setValue(new Long(1), milestones[i], languages[j]);
//		////System.out.println("milestones.length="+milestones.length);
//                ////System.out.println("languages.size()="+languages.size());
//       
//       Hashtable toolTipMessages = new Hashtable();
//        ////System.out.println("milestones.length="+milestones.length+", languages.size()="+languages.size());
//        
//        //Add Start
//        for(int j=0;j<languages.size();j++) { 
//                        dataset.setValue(new Long(0), milestones[0], (String)languages.elementAt(j));
//        }
//       numberOfLanguages = languages.size();
//                for(int i=1; i< milestones.length; i++){
//                    for(int j=0;j<languages.size();j++) { 
//                        dataset.setValue(new Long(1), milestones[i], (String)languages.elementAt(j));
//                        
//                        
//                     
//                    // if(i>0){
//                    
//                        String completedTs = "";
//                         String expectedDate = "";
//                         String bgColor = "red";
//                        
//                        Integer languageId = (Integer)totalLanguages.get((String)languages.elementAt(j));
//                        String cellName = "MILLAN_"+newInspectArr[i].getInspectionId()+"_"+languageId+"_"+p.getProjectId();
//                        MilestoneLanguage millan = (MilestoneLanguage)millanValues.get(cellName);
//                        
//                        if( expectedDates.get(languageId+ "_"+newInspectArr[i].getMilestone())!=null){
//                            java.util.Calendar c = java.util.Calendar.getInstance();
//                            c.setTime((Date)expectedDates.get(languageId+ "_"+newInspectArr[i].getMilestone()));
//                            expectedDate +=  "<br>Expected " + (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR)+". ";
//                            
//                        }
//                        
//                        
//                        if(millan!=null){
//                            java.util.Calendar c = java.util.Calendar.getInstance();
//                            c.setTime(new Date(millan.getCompleted_ts().getTime()));
//                            completedTs += "<br>Completed "+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR);
//                
//                            bgColor = "gray";
//                        }
//                       String hoverText = "<u>"+newInspectArr[i].getMilestone()+"</u>"+expectedDate+completedTs;
//                       
//                       
//                       
//                       toolTipMessages.put(""+i+"_"+j, hoverText);
//                       
//                     
//                    // }
//                       
//                    }
//                }
//                
//                /*dataset.setValue(new Long(1), "Inspection", "German");
//		dataset.setValue(new Long(1), "Inspection", "Japanese");
//                dataset.setValue(new Long(1), "Inspection", "Italian");
//                dataset.setValue(new Long(1), "Inspection", "Korean");
//                dataset.setValue(new Long(1), "Editing", "German");
//		dataset.setValue(new Long(1), "Editing", "Japanese");
//                dataset.setValue(new Long(1), "Editing", "Italian");
//                dataset.setValue(new Long(1), "Editing", "Korean");
//		dataset.setValue(new Long(1), "Proofreading", "German");
//		dataset.setValue(new Long(1), "Proofreading", "Japanese");
//		dataset.setValue(new Long(1), "Proofreading", "Italian");
//                dataset.setValue(new Long(1), "Proofreading", "Korean");
//		dataset.setValue(new Long(1), "QA", "German");
//		dataset.setValue(new Long(1), "QA", "Japanese");
//		dataset.setValue(new Long(1), "QA", "Italian");
//                dataset.setValue(new Long(1), "QA", "Korean");
//		dataset.setValue(new Long(1), "Changes", "German");
//		dataset.setValue(new Long(1), "Changes", "Japanese");
//		dataset.setValue(new Long(1), "Changes", "Italian");
//                dataset.setValue(new Long(1), "Changes", "Korean");*/
//	
//
//        JFreeChart chart = ChartFactory.createStackedBarChart3D(
//								"Progress Chart",  // chart title
//								"Category",            // domain axis label
//								"Value",               // range axis label
//								dataset,               // data
//								PlotOrientation.HORIZONTAL,
//								false,                  // include legend
//								false,               //tooltips
//								true  //url
//							);
//					
//				
//				/*final NumberAxis valueAxis = new NumberAxis("Milestones");
//				valueAxis.setTickLabelsVisible(true);
//				valueAxis.setRange(new org.jfree.data.Range(0,6)); 
//				NumberTickUnit ntu= new NumberTickUnit(1); 
//				valueAxis.setTickUnit(ntu); */
//			
//				final CategoryPlot plot = chart.getCategoryPlot();
//				plot.setDomainAxis(categoryAxis) ;
//				plot.setRangeAxis(valueAxis);
//
//				final MyStackedBarRenderer3D renderer = new MyStackedBarRenderer3D();
//                                renderer.setToolTipMessages(toolTipMessages);
//				//3) TODO: renderer.setMilestonesHT(session.getAttribute("milestoneHT");
//                                /*MyToolTipGenerator mytooltipgenerator = new MyToolTipGenerator();
//                                mytooltipgenerator.setToolTipMessages(toolTipMessages);
//				renderer.setToolTipGenerator(mytooltipgenerator);
//                                */
//				//renderer.setItemURLGenerator(new StandardCategoryURLGenerator());
//                                MyCategoryURLGenerator myCategoryURLGenerator = new MyCategoryURLGenerator();
//                                myCategoryURLGenerator.setMouseOverMessages(toolTipMessages);
//                                renderer.setItemURLGenerator(myCategoryURLGenerator); 
//				plot.setRenderer(renderer);
//
//        chart.setBackgroundPaint(java.awt.Color.white);
//        
//        return chart;
//        
//    }
//
//    public Project getProject() {
//        return p;
//    }
//
//    public void setProject(Project project) {
//        this.p = project;
//    }

    
    
}
