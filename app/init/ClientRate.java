/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.init;

import app.standardCode.ExcelConstants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author niteshwar
 */
public enum ClientRate {
    
    TYPEOFTEXT;
    
    public List<Integer> getList(){
        List<Integer> clientlist = new ArrayList();
        clientlist.add(ExcelConstants.CLIENT_BBS);
//        clientlist.add(104);
        return clientlist;
    }
    
}
