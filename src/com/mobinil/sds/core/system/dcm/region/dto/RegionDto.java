package com.mobinil.sds.core.system.dcm.region.dto;
import java.io.Serializable;
import java.util.Vector;
import com.mobinil.sds.core.system.dcm.region.model.*;
public class RegionDto implements Serializable
{
  public RegionDto()
  {
  }
  private RegionModel model;
  private Vector childRegions;

  public RegionModel getModel()
  {
    return model;
  }
  public void setModel(RegionModel model)
  {
    this.model = model;
  }
  public Vector getChildRegions()
  {
    return childRegions;
  }
  public void setChildRegions(Vector childRegions)
  {
    this.childRegions = childRegions;
  }
}