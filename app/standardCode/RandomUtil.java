/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.standardCode;

import java.util.Random;

/**
 *
 * @author niteshwar
 */
// Java program generate a random AlphaNumeric String 
// using Math.random() method 

public class RandomUtil { 

	// function to generate a random string of length n 
	public static String getAlphaNumericString(int n) 
	{ 

		// chose a Character random from this String 
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                            + "0123456789"
                                            + "abcdefghijklmnopqrstuvxyz"; 

		// create StringBuffer size of AlphaNumericString 
		StringBuilder sb = new StringBuilder(n); 

		for (int i = 0; i < n; i++) { 

			// generate a random number between 
			// 0 to AlphaNumericString variable length 
			int index = (int)(AlphaNumericString.length() * Math.random()); 

			// add Character one by one in end of sb 
			sb.append(AlphaNumericString 
						.charAt(index)); 
		} 

		return sb.toString(); 
	} 
        
        public static String getAlphaNumericString() 
	{
            return getAlphaNumericString(getRandomNo()); 
        }
        
        public static int getRandomNo(){
            Random r = new Random();
            int low = 10;
            int high = 20;
            return getRandomNo(low, high);
        }
        
        public static int getRandomNo( int low, int high){        
            Random r = new Random();
            return r.nextInt(high-low) + low;
        }

	public static void main(String[] args) 
	{ 
		// Get the size n 
		int n = 20; 
		// Get and display the alphanumeric string 
		System.out.println(RandomUtil.getAlphaNumericString(n)); 
	} 
} 
