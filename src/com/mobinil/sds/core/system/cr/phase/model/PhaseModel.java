package com.mobinil.sds.core.system.cr.phase.model;

import java.sql.*;
import java.io.Serializable;

public class PhaseModel implements Serializable 
{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
public static final String PHASE_ID = "PHASE_ID";
  public static final String PHASE_NAME = "PHASE_NAME";
  public static final String ORDER_ID = "ORDER_ID";
  
  private String m_strPhaseID;
  private String m_strPhaseName;
  private String m_strPhaseOrder;

  public PhaseModel(ResultSet argPhasesRS)
  {
    try 
    {
      m_strPhaseID = argPhasesRS.getString(PHASE_ID);
      m_strPhaseName = argPhasesRS.getString(PHASE_NAME);
      m_strPhaseOrder = argPhasesRS.getString(ORDER_ID);
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }

  public String getPhaseID()
  {
    return m_strPhaseID;
  }

  public void setPhaseID(String argPhaseID)
  {
    m_strPhaseID = argPhaseID;
  }

  public String getPhaseName()
  {
    return m_strPhaseName;
  }

  public void setPhaseName(String argPhaseName)
  {
    m_strPhaseName = argPhaseName;
  }

  public String getPhaseOrder()
  {
    return m_strPhaseOrder;
  }

  public void setPhaseOrder(String argPhaseOrder)
  {
    m_strPhaseOrder = argPhaseOrder;
  }
}