/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.report.dbport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.io.*;
/**
 *
 * @author Nadesh
 */
public class DBConnection {
     Connection conn = null;
	static Logger logger = Logger.getLogger("DBConnection");
	public DBConnection()
	{

	}
	public Connection getConnection()
	{
	  try {
		Class.forName("org.gjt.mm.mysql.Driver");
                 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel","root","root");
//                 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/excelspatial","root","root");
	  }
	  catch (ClassNotFoundException cnfe)
	  {
		logger.log(Level.SEVERE, " #####  Error "+cnfe);
	  }
	  
	  catch (SQLException sqle)
	  {
		logger.log(Level.SEVERE, " #####  Error "+sqle);
                System.out.println("Hello Error here!!!");
	  }
	  
	     return conn;
	 } // End of Connection method


}
