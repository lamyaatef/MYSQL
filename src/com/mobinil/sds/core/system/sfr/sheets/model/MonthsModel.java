package com.mobinil.sds.core.system.sfr.sheets.model;

import java.io.Serializable;
import java.sql.ResultSet;

public class MonthsModel implements Serializable
{
	
	private int year ;
    private int month;
    private int statusOne;
    private int statusTwo;
  
    
    public MonthsModel(ResultSet res)
    {
    	try
        {
    		setYear( res.getInt("YEAR"));
    		setMonth (res.getInt("MONTH"));
    		setStatusOne(res.getInt("FIRST_STATUS"));
    		setStatusTwo (res.getInt("SECOND_STATUS"));
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }  
    }
    
    public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getStatusOne() {
		return statusOne;
	}
	public void setStatusOne(int statusOne) {
		this.statusOne = statusOne;
	}
	public int getStatusTwo() {
		return statusTwo;
	}
	public void setStatusTwo(int statusTwo) {
		this.statusTwo = statusTwo;
	}
	
    
    
}
