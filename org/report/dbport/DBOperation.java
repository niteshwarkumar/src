
package org.report.dbport;

//import com.mpi.invoice.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mpi.invoice.dbport.DBConnection;

//import org.apache.log4j.PropertyConfigurator;
//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;

public class DBOperation
{
	//static Logger logger = Logger.getLogger("DBOperation");
	private ResultSet resultset = null;
	public static Connection conn = null;
	private Statement statement = null;
	private int status = 0;
	private String select = "select ";
	private String delete = "delete ";
     	private String insert = "insert ";
	private String update = "update ";
	private String from = " from ";
     	private String into = " into ";
     	private String values = " values ";
     	private String openBrace = " ( ";
     	private String closeBrace = " ) ";
     	private String set = " set ";
     	private String where = " where ";
     	private String orderBy = " order by ";
     	 DBConnection dBConnection=null;


	/**
	 * Constructor without parameter
	 * @see getConnection()
	 */
	public DBOperation()
	{
		if(dBConnection==null){
			dBConnection = new DBConnection();
			conn =  dBConnection.getConnection();
		}
		//////System.out.println("Calling DB Operator Class");
		
	}

	public DBOperation(Connection conn)	{
	    DBOperation.conn = conn;
		//////System.out.println("here in dboperation 1");
		//if(dBConnection==null)
		//{
			//dBConnection = new DBConnection();
		//	conn =  dBConnection.getConnection();
		//}
	}
	
	
	public Connection getConnection(){
		if(conn!=null)
			return conn;
		
		return null;
	}
	
	/**
	  * Retrieves the value of the designated column 
     	  * of this <code>ResultSet</code> object. 
	  * Eg. 'select [*,count] from table'
	  * @param columnName the SQL name of the column, table name
          * @return a <code>ResultSet</code> object that contains the data produced 
          *  by the given query; 
          * @exception SQLException if a database access error occurs
          * @see constructSelectQuery(java.lang.String),executeSelectQuery(java.lang.String)
	  */
	public ResultSet selectData(String exp, String table)
	{
	        String queryString = exp + from + table;
		queryString = constructSelectQuery(queryString);
		resultset = executeSelectQuery(queryString);
		return resultset;
	}

     /**
     * Retrieves the value of the designated column with the condition
     * Eg: 'select [*,count] from table where [condition]'
     * @param columnName the SQL name of the column, table name, condition
     * @return a <code>ResultSet</code> object that contains the data produced 
     *         by the given query; 
     * @exception SQLException if a database access error occurs or the given
     *            SQL statement produces anything other than a single
     *            <code>ResultSet</code> object
     * @see constructSelectQuery(java.lang.String),executeSelectQuery(java.lang.String)
     */
	public ResultSet selectData(String exp, String table, String condition)
	{
	     String queryString = exp + from + table + where + condition;
		queryString = constructSelectQuery(queryString);
		resultset = executeSelectQuery(queryString);
		return resultset;
	}
     /**
     * Retrieves the value of the designated column with the condition in an order.
     * Eg: 'select [*,count] from table where [condition] order by' 
     * @param columnName the SQL name of the column, table name, condition ,order
     * @return a <code>ResultSet</code> object that contains the data produced 
     *         by the given query; 
     * @exception SQLException if a database access error occurs or the given
     *            SQL statement produces anything other than a single
     *            <code>ResultSet</code> object
     * @see constructSelectQuery(java.lang.String),executeSelectQuery(java.lang.String)
     */

	public ResultSet selectData(String exp, String table, String condition, String order){
	     String queryString = exp + from + table + where + condition + orderBy + order;
	        queryString = constructSelectQuery(queryString);
		resultset = executeSelectQuery(queryString);
		return resultset;
	}

     // for 'select <anything>'
	public ResultSet selectData(String stmt){
	        String queryString = select + stmt;
			resultset = executeSelectQuery(queryString);
			return resultset;
	}

	// for saving data 'insert into <table> values <data>
	/*public int saveData(String table, String[] values) {
	     String data = "";
		for(int i = 0; i <= values.length; i++){
		     data = data + values[i];
		     
		     if(i <= values.length)
     	                  data = data + ",";
		}
		String queryString = insert + into + table + values + openBrace + data + closeBrace;
		status = executeDBQuery(queryString);
		
		return status;
	} */
	/**
         * Saves the value of the designated column in to the database table.
         * Eg: 'insert into <tablename> values <data> ' 
         * @param java.lang.String - table name, java.lang.String - cloumn values
         * @return java.lang.Integer - the status whether inserted or not.
         * @exception SQLException if a database access error occurs or the given
         */// Modified by Abrar
	public int saveData(String table, String[] tableValues) {
	     String data = "";
	     for(int i = 0; i < tableValues.length; i++){
		     data = data + "'"+tableValues[i]+"'";
		     if(i < tableValues.length)
     	                  data = data + ",";
	     }
	        int indexComma = data.lastIndexOf(',');
		String excequery = data.substring(0, indexComma);
		String queryString = insert + into + table + values + openBrace + excequery + closeBrace;
		status = executeDBQuery(queryString);
		return status;
	}
	/**
         * Updates the value of the designated column in to the database table.
         * Eg: 'update <table> values <data> where <condition>'
         * @param java.lang.String - table name, java.lang.String - cloumn values, java.lang.String - condition.
         * @return java.lang.Integer - the status whether updated or not.
         * @exception SQLException if a database access error occurs or the given
         */
	public int updateData(String table, String[] tableValues, String condition){
                String data = "";
		for(int i = 0; i < tableValues.length; i++){
		         data = data +tableValues[i];
    		     if(i < tableValues.length)
     	                 data = data + ",";
		}
		int indexComma = data.lastIndexOf(',');
		String excequery = data.substring(0, indexComma);
		String queryString = update + table + set + excequery;
		if(condition!= null)
		 	queryString = queryString + where + condition;
			
		status = executeDBQuery(queryString);
		return status;
	}
	/**
         * delete the particular row in the database table.
         * Eg: 'delete from <table> where <condition>'
         * @param java.lang.String - table name, java.lang.String - condition.
         * @return java.lang.Integer - the status whether deleted or not.
         * @exception SQLException if a database access error occurs or the given
         */
	public int deleteData(String table, String condition) {
	     String queryString = delete + from + table + where + condition;
	     	status = executeDBQuery(queryString);
		return status;
	}
	
		
	/**
         * construct the select query for the string passed
	 * @param java.lang.String - queryString
	 * @return java.lang.String - queryString
	 */
     	public String constructSelectQuery(String queryString) {
          queryString = select + queryString;
	     return queryString;
	}
	/**
         * execute select query
	 * @param java.lang.String - queryString
	 * @return ResultSet
	 */
	public ResultSet executeSelectQuery(String queryString){
	     ////System.out.println(queryString);
	     try{
		     	//////System.out.println("query : " + queryString);
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		     	resultset = statement.executeQuery(queryString);
	     } catch(SQLException ex){
		     //ex.printStackTrace();
                     //logger.error(" DBOperation | Throws Exception while executing query : "+ex);
	     }
	     
	   return resultset;
	}

       /**
	 * This method executes the query and returns the status as int value.
	 * 0 - execution failed.
	 * 1 - executed successfully.
	 * @return java.lang.Integer.
	 */	
	public int executeDBQuery(String queryString){
	     try
	     {       
		    	//commented on 09-09-2004 because it commits after every DBopration 
		    	//and rollback gives problem. 	
			// conn.setAutoCommit(false); 
		     	statement = conn.createStatement();
		     	status = statement.executeUpdate(queryString);
	             	//conn.commit();
	     } catch(SQLException ex){
	                try {
				conn.rollback();
			}catch (SQLException e) { 
				//logger.error(" DBOperation | Roll Back Completed "+e);
			}
		    // ex.printStackTrace();
                     //logger.error(" DBOperation | Throws Exception while executing query : "+ex);
	     }
	     return status;
	} 
	// 09-09-2004 - Akila
	/**
	 * This method will set autocommit to false mode.
	 * @param conn
	 * @param status -whether true or false.
	 * @return none.
	 */
	public void setAutoCommitDB(Connection conn,boolean status){
             try{		
			conn.setAutoCommit(status);
		 }catch(SQLException err){
			////System.out.println("SQLException in DBOPERATION"+err);
			//err.printStackTrace();
		 }
	}
	
	/**
	 * This method will commit the current Database Operation.
	 * @param conn
	 * @return none.
	 */
	public void commitDB(Connection conn){
		try{
			conn.commit();
		}catch(SQLException err){
			////System.out.println("SQLException in DBOPERATION"+err);
			//err.printStackTrace();
		}
	}
	
	/**
	 * This method will rollback the current Database Operation.
	 * @param conn
	 * @return none.
	 */
	public void rollbackDB(Connection conn){
		try{
			conn.rollback();
		}catch(SQLException err){
			////System.out.println("SQLException in DBOPERATION"+err);
			//err.printStackTrace();
		}
	}

}
