package com.mobinil.sds.core.system.cr.phase.dto;

import java.util.Vector;
import com.mobinil.sds.core.system.cr.phase.model.*;
import java.io.Serializable;

public class PhaseDTO implements Serializable 
{
  private Vector m_colPhases = new Vector();

  public Vector getPhases()
  {
    return m_colPhases;
  }

  public void setPhases(Vector argPhases)
  {
    m_colPhases = argPhases;
  }

  public void AddPhaseModel(PhaseModel argPhaseModel)
  {
    m_colPhases.add(argPhaseModel);
  }
}