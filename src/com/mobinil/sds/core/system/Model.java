package com.mobinil.sds.core.system;

import java.io.Serializable;
import java.sql.*;

public abstract class Model implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public abstract void fillInstance(ResultSet res);
	
	public Model()
	{
		
	}
		
	 
}
