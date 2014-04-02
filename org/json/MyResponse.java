/*
 * Created on Apr 1, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.json;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author pp41387
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MyResponse {
	
	private boolean success = false;
	private String reason = "Login failed. Try again.";
	
	public MyResponse(boolean s){
		this.success = s;
		
	}
	
	public class Employee{
		
		private String id;
		private String name;
		private String occupation;
		
		public Employee(String id, String name, String occupation) {
			this.id = id;
			this.name = name;
			this.occupation = occupation;
		}
			/**
		 * @return Returns the id.
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id The id to set.
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return Returns the occupation.
		 */
		public String getOccupation() {
			return occupation;
		}
		/**
		 * @param occupation The occupation to set.
		 */
		public void setOccupation(String occupation) {
			this.occupation = occupation;
		}
}
	
	public String getEmps() throws Exception{
		
		Employee emp1 = new Employee("1","Alex","CEO");
		Employee emp2 = new Employee("2","Yoshi","Substitution Janitor");
		JSONObject[] empArr= { new JSONObject(emp1),new JSONObject(emp2)};
		return (new JSONArray(empArr).toString());
	}
	
	
	public static String validate(String username, String password){
		
		if("1".equals(username) && "1".equals(password)){
			return (new JSONObject(new MyResponse(true))).toString();
		}else{
			return (new JSONObject(new MyResponse(false))).toString();
		}
	}
	/**
	 * @return Returns the success.
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success The success to set.
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
