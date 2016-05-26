package com.mobinil.sds.core.system.cr.phase.dao;

import com.mobinil.sds.core.system.cr.phase.model.*;
import com.mobinil.sds.core.system.cr.phase.dto.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;

public class PhaseDAO 
{
	private static PhaseDTO pDTO = null;


	public static void loadPhases()
	{
		Connection con = null;
		Statement stat = null;
		ResultSet res =null;

		try
		{
			con = Utility.getConnection();
			stat = con.createStatement();
			res = stat.executeQuery("select * from VW_CR_PHASE order by ORDER_ID");
			pDTO = new PhaseDTO();
			while (res.next())
			{				
				pDTO.AddPhaseModel(new PhaseModel(res));
			}	  
			res.close(); 
		}
		catch(Exception e)
		{  
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(stat);
			DBUtil.close(con);
		}

	}


	public static PhaseDTO getPhases()
	{

		if (pDTO == null)
		{						
				PhaseDAO.loadPhases();
		}
		
		return pDTO;
	}

}