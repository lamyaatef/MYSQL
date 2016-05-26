package com.mobinil.sds.core.system.scm.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mobinil.sds.core.system.Model;
import com.mobinil.sds.core.system.scm.dao.GenericDAO;

public class CaseModel extends Model
{
	private int rowNumber ;
	private int stkCase ;
	private int posCase;
        private String phoneNumber;
	
	public void setrowNumber(int rowNumber)
	{
		
		this.rowNumber=rowNumber;
		
	}
	public void setstkCase(int stkCase)
	{
		this.stkCase=stkCase;
		
	}
	public void setposCase(int posCase)
	{
		this.posCase=posCase;
		
	}
	public int getposCase()
	{
		return this.posCase;
	}
	public int getstkCase()
	{
		return this.stkCase;
	}
	public int getrowNumber()
	{
		return this.rowNumber;
	}
	@Override
	public void fillInstance(ResultSet res)
	{
		// TODO Auto-generated method stub
		
		try
		{
			if( GenericDAO.checkColumnName("ROW_NUM", res))
                        this.setrowNumber(res.getInt("ROW_NUM"));
			if( GenericDAO.checkColumnName("STKCASE", res))
                        this.setstkCase(res.getInt("STKCASE"));
			if( GenericDAO.checkColumnName("POSCASE", res))
                        this.setposCase(res.getInt("POSCASE"));
			if( GenericDAO.checkColumnName("STK_DIAL", res))
                        this.setPhoneNumber(res.getString("STK_DIAL"));
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
