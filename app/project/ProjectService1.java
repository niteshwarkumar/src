/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author niteshwar
 */
public class ProjectService1 {

    private static ProjectService1 instance = null;
    private final DecimalFormat df = new DecimalFormat("#,###.##");

    public ProjectService1() {
    }

    ProjectService ps;

    //return the instance of ProjectService
    public static synchronized ProjectService1 getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new ProjectService1();
        }
        return instance;
    }

    public String getPMFee(Change1 c, Project p, double feeTotal) {
      
        return getPMFee(c, p, feeTotal, false);

    }

    public String getRushFee(Change1 c, Project p, double feeTotal) {

        return getRushFee(c, p, feeTotal, false);
    }

    public String getDiscountFee(Change1 c, Project p, double feeTotal) {

        return getDiscountFee(c, p, feeTotal, false);
    }
    
    public Double getPMTotalFee(Change1 c, Project p, double feeTotal){
    
        double total = 0.00;
    
        total +=Double.parseDouble(getPMFee(c, p, feeTotal,false).replaceAll(",", ""));
        return total;
        
    }
    
    public Double getRushTotalFee(Change1 c, Project p, double feeTotal){
    
        double total = 0.00;
       
        total +=Double.parseDouble(getRushFee(c, p, feeTotal,false).replaceAll(",", ""));
        
        return total;
        
    }
    
    public Double getDiscountTotalFee(Change1 c, Project p, double feeTotal){
    
        double total = 0.00;
        total -=Double.parseDouble(getDiscountFee(c, p, feeTotal,false).replaceAll(",", ""));
       
        return total;
        
    }
    
     public String getPMFee(Change1 c, Project p, double feeTotal, boolean currencyConvert) {
        String pmPercentDollar = "";
        String pmFee = "0.00";
        if (c == null) {
            try {
                
                pmPercentDollar = ""+ Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""));
//                Double.parseDouble(pmPercentDollar);
            } catch (NumberFormatException e) {
                pmPercentDollar = "";
            }
            if (p.getPmPercent() != null && !p.getPmPercent().equals("") && !p.getPmPercent().equals("0.00")) {
                double discountRate = Double.valueOf(p.getPmPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100) * feeTotal));
            } else if (!pmPercentDollar.equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    return df.format(Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return df.format(Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", "")));
            }
        } else {

            try {
                Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""));
            } catch (NumberFormatException e) {
                pmPercentDollar = "";
            }
            if (c.getPmPercent() != null && !c.getPmPercent().equals("") && !c.getPmPercent().equals("0.00")) {
                double discountRate = Double.valueOf(c.getPmPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100) * feeTotal));
            } else if (!pmPercentDollar.equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    return df.format(Double.parseDouble(c.getPmPercentDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return df.format(Double.parseDouble(c.getPmPercentDollarTotal().replaceAll(",", "")));
            }
        }
        return "0.00";

    }

    public String getRushFee(Change1 c, Project p, double feeTotal, boolean currencyConvert) {
        if (c == null) {
            if (p.getRushPercent() != null && !p.getRushPercent().equals("") && !p.getRushPercent().equals("0.00")) {

                double discountRate = Double.valueOf(p.getRushPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100) * feeTotal));
            } else if (p.getRushPercentDollarTotal() != null && !p.getRushPercentDollarTotal().equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                return df.format(Double.parseDouble(p.getRushPercentDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return df.format(Double.parseDouble(p.getRushPercentDollarTotal().replaceAll(",", "")));
            }
        } else {
            if (c.getRushPercent() != null && !c.getRushPercent().equals("") && !c.getRushPercent().equals("0.00")) {
                double discountRate = Double.valueOf(c.getRushPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100) * feeTotal));
            } else if (c.getRushPercentDollarTotal() != null && !c.getRushPercentDollarTotal().equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                return df.format(Double.parseDouble(c.getRushPercentDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return df.format(Double.parseDouble(c.getRushPercentDollarTotal().replaceAll(",", "")));
            }
        }
        return "0.00";
    }

    public String getDiscountFee(Change1 c, Project p, double feeTotal, boolean currencyConvert) {
        String discountDollar = "";
        if (c == null) {
            try {
                discountDollar = ""+Double.parseDouble(p.getDiscountDollarTotal());
            } catch (Exception e) {
                discountDollar = "";
            }
            if (p.getDiscountPercent() != null && !p.getDiscountPercent().equals("") && !p.getDiscountPercent().equals("0.00")) {
                double discountRate = Double.valueOf(p.getDiscountPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100)) * feeTotal);
            } else if (!discountDollar.equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                return df.format(Double.parseDouble(p.getDiscountDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return  df.format(Double.parseDouble(p.getDiscountDollarTotal()));
            }
        } else {
           try {
                discountDollar = ""+Double.parseDouble(c.getDiscountDollarTotal());
            } catch (Exception e) {
                discountDollar = "";
            }
            if (c.getDiscountPercent() != null && !c.getDiscountPercent().equals("") && !c.getDiscountPercent().equals("0.00")) {
                double discountRate = Double.valueOf(c.getDiscountPercent().replaceAll(",", ""));
                return df.format(((discountRate / 100)) * feeTotal);
            } else if (!discountDollar.equals("")) {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                return df.format(Double.parseDouble(c.getDiscountDollarTotal().replaceAll(",", ""))*p.getEuroToUsdExchangeRate());
                }
                return  df.format(Double.parseDouble(c.getDiscountDollarTotal()));
            }
        }
        return "0.00";
    }
    
    public Double getPMTotalFee(Change1 c, Project p, double feeTotal, boolean currencyConvert){
    
        double total = 0.00;
    
        total +=Double.parseDouble(getPMFee(c, p, feeTotal,currencyConvert).replaceAll(",", ""));
        return total;
        
    }
    
    public Double getRushTotalFee(Change1 c, Project p, double feeTotal, boolean currencyConvert){
    
        double total = 0.00;
       
        total +=Double.parseDouble(getRushFee(c, p, feeTotal,currencyConvert).replaceAll(",", ""));
        
        return total;
        
    }
    
    public Double getDiscountTotalFee(Change1 c, Project p, double feeTotal, boolean currencyConvert){
    
        double total = 0.00;
        total -=Double.parseDouble(getDiscountFee(c, p, feeTotal,currencyConvert).replaceAll(",", ""));
       
        return total;
        
    }
    
    public double getTotalFee(Project p){
        double totfee = p.getProjectAmount();
//        if(!p.getCompany().getCcurrency().equalsIgnoreCase("USD")){
//            return totfee/p.getEuroToUsdExchangeRate();
//        }
       
        return totfee;
        
    }
    
    
    
    public void updateProjectAmount(Project p, User u){
        
        //get this project's sources
        Set sources = p.getSourceDocs();
        Map<String,Double> totalFee = new HashMap();
        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        List totalDtpTasks = new ArrayList();
        List totalOthTasks = new ArrayList();
        double subTotal = 0.0;
        //for each source
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    totalLinTasks.add(lt);
                }

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }

                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    totalOthTasks.add(ot);
                }
            }
        }
        
        //Sort by task (orderNum), then source (language), then target (language)
        Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
        Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
        Collections.sort(totalEngTasks, CompareTaskLanguages.getInstance());

        Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
        Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);

        
                //find total of LinTasks
//        double linTaskTotal = 0;
        for (LinTask linTasksArray1 : linTasksArray) {
            if (linTasksArray1.getDollarTotalFee() != null) {
                //remove comma's
                String linTotal = linTasksArray1.getDollarTotalFee();
                linTotal = linTotal.replaceAll(",", "");
                if ("".equals(linTotal)) {
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                String changeKey = StandardCode.getInstance().getChangeName(linTasksArray1.getChangeDesc());
                double linTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    linTaskTotal = totalFee.get(changeKey);
                }
                 
                    linTaskTotal += total;
     
                totalFee.put(changeKey, linTaskTotal);
                subTotal+=total;
            }
        }
        
        //find total of EngTasks
//        double engTaskTotal = 0;
        for (EngTask engTasksArray1 : engTasksArray) {
            if (engTasksArray1.getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray1.getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                if ("".equals(engTotal)) {
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                String changeKey = StandardCode.getInstance().getChangeName(engTasksArray1.getChangeDesc());
                double engTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    engTaskTotal = totalFee.get(changeKey);
                }
         
                        engTaskTotal += total;
               
//                engTaskTotal += total;
                totalFee.put(changeKey, engTaskTotal);
                subTotal+=total;
            }
        }

        //find total of DtpTasks
//        double dtpTaskTotal = 0;
        for (DtpTask dtpTasksArray1 : dtpTasksArray) {
            if (dtpTasksArray1.getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray1.getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                if ("".equals(dtpTotal)) {
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                String changeKey = StandardCode.getInstance().getChangeName(dtpTasksArray1.getChangeDesc());
                double dtpTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    dtpTaskTotal = totalFee.get(changeKey);
                }
    
                        dtpTaskTotal += total;
             
                //dtpTaskTotal += total;
                totalFee.put(changeKey, dtpTaskTotal);
//                dtpTaskTotal += total.doubleValue();
subTotal+=total;
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                if ("".equals(othTotal)) {
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total;
                subTotal+=total;
            }
        }
        
            //START subTotal value
       // double subTotal = 0.0;
//        try {
////            subTotal = p.getProjectAmount();
//            subTotal = Double.parseDouble(p.getSubDollarTotal().replaceAll(",", ""));
//        } catch (Exception e) {
//            try{
//                subTotal = p.getProjectAmount();
//            }catch(Exception e1){}
//
//        }
        //END subTotal value
        Double subDollarTotal = subTotal;
        if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            subDollarTotal = subDollarTotal * p.getEuroToUsdExchangeRate();
        }
        p.setSubDollarTotal(StandardCode.getInstance().formatDouble(subDollarTotal));
        
        String change ;
        String pmPercent;
        String pmPercentDollarTotal ;
        String rushPercent;
        String rushPercentDollarTotal;
        String discountPercent ;
        String discountDollarTotal;
        
        double pmRate = 0;
        double pmPercentDollarTotalDouble = 0;

        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
            double pmPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              pmPercent = p.getPmPercent();
              pmPercentDollarTotal = p.getPmPercentDollarTotal();
            }else{
                pmPercent = change1.getPmPercent();
                pmPercentDollarTotal = change1.getPmPercentDollarTotal();
            }
            
            if (pmPercent != null  && !pmPercent.equals("") && !pmPercent.equals("0.00")) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent);
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotalPerChange = +(pmRate / 100) * entry.getValue();
        } else if (pmPercentDollarTotal != null  && !pmPercentDollarTotal.equals("") && !pmPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                pmPercentDollarTotalPerChange = +Double.valueOf(pmPercentDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }

        }
            totalFee.put(entry.getKey(), entry.getValue()+pmPercentDollarTotalPerChange);
            pmPercentDollarTotalDouble+=pmPercentDollarTotalPerChange;
	}
        

        //END pm block


        //START sub total with pm block
        double subPmTotal = subTotal + pmPercentDollarTotalDouble;
         double subPmDollarTotal = subPmTotal;
        if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            subPmDollarTotal = subPmDollarTotal * p.getEuroToUsdExchangeRate();
        }
        
        p.setSubPmDollarTotal(StandardCode.getInstance().formatDouble(subPmDollarTotal));
        //END sub total with pm block


        //START rush block
        

        double rushRate = 0;
        double rushPercentDollarTotalDouble = 0;
        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
             double rushPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              rushPercent = p.getRushPercent();
              rushPercentDollarTotal = p.getRushPercentDollarTotal();
            }else{
                rushPercent = change1.getRushPercent();
                rushPercentDollarTotal = change1.getRushPercentDollarTotal();
            }
        
        if (rushPercent != null && !rushPercent.equals("") && !rushPercent.equals("0.00")) { //if rushPercent is present

            try {
                rushRate = Double.valueOf(rushPercent);
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotalPerChange = (rushRate / 100) * entry.getValue();
        } else if (rushPercentDollarTotal != null && !rushPercentDollarTotal.equals("") && !rushPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                rushPercentDollarTotalPerChange = Double.valueOf(rushPercentDollarTotal.replaceAll(",", ""));
//                try {
//                    if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
//                        rushPercentDollarTotalPerChange = rushPercentDollarTotalPerChange * p.getEuroToUsdExchangeRate();
//                    }
//                } catch (Exception e) {
//                }
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }

        }
        totalFee.put(entry.getKey(), entry.getValue()+rushPercentDollarTotalPerChange);
            rushPercentDollarTotalDouble+=rushPercentDollarTotalPerChange;
        }
        
      
        //END rush block


        //START total value
        if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            rushPercentDollarTotalDouble = rushPercentDollarTotalDouble * p.getEuroToUsdExchangeRate();
        }
        double projectTotal = subPmDollarTotal + rushPercentDollarTotalDouble;

        //START DISCOUNT block
        
        double discountRate = 0;
//        double discountPercentDollarTotal = 0;
        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
            double discountPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              discountPercent = p.getDiscountPercent();
              discountDollarTotal = p.getDiscountDollarTotal();
            }else{
                discountPercent = change1.getDiscountPercent();
                discountDollarTotal = change1.getDiscountDollarTotal();
            }
        
        if (discountPercent != null && !discountPercent.equals("") && !discountPercent.equals("0.00")) { //if rushPercent is present
            try {
                discountRate = Double.valueOf(discountPercent.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            //discountDollarTotal = ;
            discountPercentDollarTotalPerChange =  (discountRate / 100) * entry.getValue();
        } else if (discountDollarTotal != null && !discountDollarTotal.equals("") && !discountDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                discountPercentDollarTotalPerChange = Double.valueOf(discountDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }

        }
        totalFee.put(entry.getKey(), entry.getValue()-discountPercentDollarTotalPerChange);
        
        if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            discountPercentDollarTotalPerChange = discountPercentDollarTotalPerChange * p.getEuroToUsdExchangeRate();
        }
            projectTotal = projectTotal - discountPercentDollarTotalPerChange;
        }
         
       
        p.setSubDiscountDollarTotal("" + projectTotal);


        String otherText = p.getOtherText();
        String otherPercent=p.getOtherPercent();
        String otherDollarTotal=p.getOtherDollarTotal();
        
        double otherPercentRate = 0;
        double otherPercentDollarTotal = 0;

        if (otherPercent != null && !otherPercent.equals("") && !otherPercent.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentRate = Double.valueOf(otherPercent.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentRate = 0;
            }
            //discountDollarTotal = ;
            projectTotal =projectTotal+((otherPercentRate / 100) * projectTotal);
        } else if (otherDollarTotal != null && !otherDollarTotal.equals("") && !otherDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentDollarTotal = Double.valueOf(otherDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentDollarTotal = 0;
            }
             if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            otherPercentDollarTotal = otherPercentDollarTotal * p.getEuroToUsdExchangeRate();
        }
            projectTotal = projectTotal + otherPercentDollarTotal;

        }

        //END DISCOUNT block


        p.setProjectAmount(projectTotal);
        //END total value
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        //update project to db
        ProjectService.getInstance().updateProject(p);
    
    }
    
    
    public double getTotalPMFee(Project p){
        return getTotalPMFee(p,false);
    }
    public double getTotalPMFee(Project p, boolean currencyConvert){
        Map<String,Double> totalFee = getTotalFeeMap(p);
        
        String change ;
        String pmPercent;
        String pmPercentDollarTotal ;
        
        
        double pmRate = 0;
        double pmPercentDollarTotalDouble = 0;

        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
            double pmPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              pmPercent = p.getPmPercent();
              pmPercentDollarTotal = p.getPmPercentDollarTotal();
            }else{
                pmPercent = change1.getPmPercent();
                pmPercentDollarTotal = change1.getPmPercentDollarTotal();
            }
            
            if (pmPercent != null  && !pmPercent.equals("") && !pmPercent.equals("0.00")) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent);
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotalPerChange = +(pmRate / 100) * entry.getValue();
//             if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
//                 pmPercentDollarTotalPerChange = pmPercentDollarTotalPerChange/p.getEuroToUsdExchangeRate();
//             }
            
        } else if (pmPercentDollarTotal != null  && !pmPercentDollarTotal.equals("") && !pmPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                 if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    pmPercentDollarTotalPerChange = +Double.valueOf(pmPercentDollarTotal.replaceAll(",", ""))*p.getEuroToUsdExchangeRate();
                }else
                pmPercentDollarTotalPerChange = +Double.valueOf(pmPercentDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }

        }
            totalFee.put(entry.getKey(), entry.getValue()+pmPercentDollarTotalPerChange);
            pmPercentDollarTotalDouble+=pmPercentDollarTotalPerChange;
	}
        

        //END pm block


        //START sub total with pm block
        return pmPercentDollarTotalDouble;
        
        //END sub total with pm block

    }
    
    public double getTotalRushFee(Project p){
        return getTotalRushFee(p, false);
    }
    
     public double getTotalRushFee(Project p, boolean currencyConvert){
        Map<String,Double> totalFee = getTotalFeeMap(p);
     //START subTotal value
        double subTotal = 0.0;
        try {
//            subTotal = p.getProjectAmount();
            subTotal = Double.parseDouble(p.getSubDollarTotal().replaceAll(",", ""));
        } catch (Exception e) {
            try{
                subTotal = p.getProjectAmount();
            }catch(Exception e1){}

        }
        //END subTotal value
        
        String change ;

        String rushPercent;
        String rushPercentDollarTotal;

      //  double subPmTotal = Double.parseDouble(p.getSubPmDollarTotal().replaceAll(",", ""));



        //START rush block
        

        double rushRate = 0;
        double rushPercentDollarTotalDouble = 0;
        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
             double rushPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              rushPercent = p.getRushPercent();
              rushPercentDollarTotal = p.getRushPercentDollarTotal();
            }else{
                rushPercent = change1.getRushPercent();
                rushPercentDollarTotal = change1.getRushPercentDollarTotal();
            }
        
        if (rushPercent != null && !rushPercent.equals("") && !rushPercent.equals("0.00")) { //if rushPercent is present

            try {
                rushRate = Double.valueOf(rushPercent);
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotalPerChange = (rushRate / 100) * entry.getValue();
        } else if (rushPercentDollarTotal != null && !rushPercentDollarTotal.equals("") && !rushPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                 if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    rushPercentDollarTotalPerChange = Double.valueOf(rushPercentDollarTotal.replaceAll(",", ""))*p.getEuroToUsdExchangeRate();
                }else
                rushPercentDollarTotalPerChange = Double.valueOf(rushPercentDollarTotal.replaceAll(",", ""));
//                try {
//                    if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
//                        rushPercentDollarTotalPerChange = rushPercentDollarTotalPerChange * p.getEuroToUsdExchangeRate();
//                    }
//                } catch (Exception e) {
//                }
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }

        }
        totalFee.put(entry.getKey(), entry.getValue()+rushPercentDollarTotalPerChange);
            rushPercentDollarTotalDouble+=rushPercentDollarTotalPerChange;
        }
        
      
        //END rush block
        return rushPercentDollarTotalDouble;

       
    
    }
      public double getTotalDiscount(Project p){
          return getTotalDiscount(p, false);
      }
     
      public double getTotalDiscount(Project p, boolean currencyConvert){
        Map<String,Double> totalFee = getTotalFeeMap(p);
     //START subTotal value
        double subTotal = 0.0;
        try {
//            subTotal = p.getProjectAmount();
            subTotal = Double.parseDouble(p.getSubDollarTotal().replaceAll(",", ""));
        } catch (Exception e) {
            try{
                subTotal = p.getProjectAmount();
            }catch(Exception e1){}

        }
        //END subTotal value
        
        String change ;
      
        String rushPercent;
        String rushPercentDollarTotal;
        String discountPercent ;
        String discountDollarTotal;
        
  
        double subPmTotal = Double.parseDouble(p.getSubPmDollarTotal().replaceAll(",", ""));
        //START rush block
        

        double rushRate = 0;
        double rushPercentDollarTotalDouble = 0;
        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
             double rushPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              rushPercent = p.getRushPercent();
              rushPercentDollarTotal = p.getRushPercentDollarTotal();
            }else{
                rushPercent = change1.getRushPercent();
                rushPercentDollarTotal = change1.getRushPercentDollarTotal();
            }
        
        if (rushPercent != null && !rushPercent.equals("") && !rushPercent.equals("0.00")) { //if rushPercent is present

            try {
                rushRate = Double.valueOf(rushPercent);
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotalPerChange = (rushRate / 100) * entry.getValue();
        } else if (rushPercentDollarTotal != null && !rushPercentDollarTotal.equals("") && !rushPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    rushPercentDollarTotalPerChange = Double.valueOf(rushPercentDollarTotal.replaceAll(",", ""))*p.getEuroToUsdExchangeRate();
                }else
                rushPercentDollarTotalPerChange = Double.valueOf(rushPercentDollarTotal.replaceAll(",", ""));
//                try {
//                    if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
//                        rushPercentDollarTotalPerChange = rushPercentDollarTotalPerChange * p.getEuroToUsdExchangeRate();
//                    }
//                } catch (Exception e) {
//                }
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }

        }
        totalFee.put(entry.getKey(), entry.getValue()+rushPercentDollarTotalPerChange);
            rushPercentDollarTotalDouble+=rushPercentDollarTotalPerChange;
        }
        
      
        //END rush block


        //START total value
        double projectTotal = subPmTotal + rushPercentDollarTotalDouble;

        //START DISCOUNT block
        
        double discountRate = 0;
        double discountPercentDollarTotal = 0;
        for (Map.Entry<String, Double> entry : totalFee.entrySet()) {
            double discountPercentDollarTotalPerChange = 0;
            Change1 change1 = ProjectService.getInstance().getSingleChange(p.getProjectId(), entry.getKey());
            if(change1 == null|| entry.getKey().equalsIgnoreCase("Original")){
              discountPercent = p.getDiscountPercent();
              discountDollarTotal = p.getDiscountDollarTotal();
            }else{
                discountPercent = change1.getDiscountPercent();
                discountDollarTotal = change1.getDiscountDollarTotal();
            }
        
        if (discountPercent != null && !discountPercent.equals("") && !discountPercent.equals("0.00")) { //if rushPercent is present
            try {
                discountRate = Double.valueOf(discountPercent.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            //discountDollarTotal = ;
            discountPercentDollarTotalPerChange =  (discountRate / 100) * entry.getValue();
        } else if (discountDollarTotal != null && !discountDollarTotal.equals("") && !discountDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                if(currencyConvert && p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    discountPercentDollarTotalPerChange = Double.valueOf(discountDollarTotal.replaceAll(",", ""))*p.getEuroToUsdExchangeRate();
                }else
                discountPercentDollarTotalPerChange = Double.valueOf(discountDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }

        }
        discountPercentDollarTotal+=discountPercentDollarTotalPerChange;
        }
         
       
        return discountPercentDollarTotal;
    
    }

    private Map<String, Double> getTotalFeeMap(Project p) {
        
         //get this project's sources
        Set sources = p.getSourceDocs();
         //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        List totalDtpTasks = new ArrayList();
        List totalOthTasks = new ArrayList();
        
        //for each source
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    totalLinTasks.add(lt);
                }

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }

                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    totalOthTasks.add(ot);
                }
            }
        }
        
        //Sort by task (orderNum), then source (language), then target (language)
        Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
        Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
        Collections.sort(totalEngTasks, CompareTaskLanguages.getInstance());

        Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
        Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);
        Map<String,Double> totalFee = new HashMap();
        
                //find total of LinTasks
//        double linTaskTotal = 0;
        for (LinTask linTasksArray1 : linTasksArray) {
            if (linTasksArray1.getDollarTotalFee() != null) {
                //remove comma's
                String linTotal = linTasksArray1.getDollarTotalFee();
                linTotal = linTotal.replaceAll(",", "");
                if ("".equals(linTotal)) {
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                String changeKey = StandardCode.getInstance().getChangeName(linTasksArray1.getChangeDesc());
                double linTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    linTaskTotal = totalFee.get(changeKey);
                }
                try {
                    // //System.out.println("Currencyyyyyyyy"+lt.getCurrencyFee());
                    if (linTasksArray1.getCurrencyFee().equalsIgnoreCase("EURO")) {
                        //linCurrencyTotal=thisTotal;
                        linTaskTotal += total; //* p.getEuroToUsdExchangeRate();
                    } else {
                        linTaskTotal += total;
                    }
                }catch (Exception e) {
                    
                    linTaskTotal += total;
                }
                totalFee.put(changeKey, linTaskTotal);
            }
        }
        
        //find total of EngTasks
//        double engTaskTotal = 0;
        for (EngTask engTasksArray1 : engTasksArray) {
            if (engTasksArray1.getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray1.getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                if ("".equals(engTotal)) {
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                String changeKey = StandardCode.getInstance().getChangeName(engTasksArray1.getChangeDesc());
                double engTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    engTaskTotal = totalFee.get(changeKey);
                }
                engTaskTotal += total;
                totalFee.put(changeKey, engTaskTotal);
            }
        }

        //find total of DtpTasks
//        double dtpTaskTotal = 0;
        for (DtpTask dtpTasksArray1 : dtpTasksArray) {
            if (dtpTasksArray1.getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray1.getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                if ("".equals(dtpTotal)) {
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                String changeKey = StandardCode.getInstance().getChangeName(dtpTasksArray1.getChangeDesc());
                double dtpTaskTotal = 0;
                if(totalFee.containsKey(changeKey)){
                    dtpTaskTotal = totalFee.get(changeKey);
                }
                dtpTaskTotal += total;
                totalFee.put(changeKey, dtpTaskTotal);
//                dtpTaskTotal += total.doubleValue();
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                if ("".equals(othTotal)) {
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total.doubleValue();
            }
        }
        return  totalFee;
    }

}
