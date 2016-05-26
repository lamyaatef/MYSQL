package com.mobinil.sds.core.system.sa.importdata.model;
import java.sql.*;

public class DataImportCellDefModel 
{
  public static final String CELL_ID = "CELL_ID";
  public static final String TABLE_ID ="TABLE_ID";
  public static final String CELL_NAME = "CELL_NAME";
  public static final String POSITION_NUM ="POSITION_NUM";
  public static final String CELL_TYPE_ID = "CELL_TYPE_ID";
  public static final String CELL_MANDATORY_FLAG ="CELL_MANDATORY_FLAG";
  public static final String CELL_SQL_NAME = "CELL_SQL_NAME";
  public static final String CELL_PRIMARY_KEY_FLAG ="CELL_PRIMARY_KEY_FLAG";
  public static final String CELL_TYPE_NAME ="TYPE_NAME";
  

  String cellId;
  String tableId;
  String cellName;
  int positionNum;
  String cellTypeId;
  boolean cellMandatoryFlag;
  String cellSQLName;
  boolean cellPrimaryKeyFlag;
  String cellTypeName;

  public DataImportCellDefModel(ResultSet res)
  {
  try{
    this.cellId = res.getString(CELL_ID);
    this.tableId = res.getString(TABLE_ID);
    this.cellName = res.getString(CELL_NAME);
    this.positionNum = res.getInt(POSITION_NUM);
    this.cellTypeId = res.getString(CELL_TYPE_ID);
    this.cellTypeName = res.getString(CELL_TYPE_NAME);
    this.cellMandatoryFlag = res.getBoolean(CELL_MANDATORY_FLAG);
    this.cellSQLName = res.getString(this.CELL_SQL_NAME);
    this.cellPrimaryKeyFlag = res.getBoolean(this.CELL_PRIMARY_KEY_FLAG);    
    
  }
  catch(Exception e)
  {
    e.printStackTrace();
  }
  
  }

  public String getCellId()
  {
    return cellId;
  }

  public void setCellId(String newCellId)
  {
    cellId = newCellId;
  }

  public String getTableId()
  {
    return tableId;
  }

  public void setTableId(String newTableId)
  {
    tableId = newTableId;
  }

  public String getCellName()
  {
    return cellName;
  }

  public void setCellName(String newCellName)
  {
    cellName = newCellName;
  }

  public int getPositionNum()
  {
    return positionNum;
  }

  public void setPositionNum(int newPositionNum)
  {
    positionNum = newPositionNum;
  }

  public String getCellTypeId()
  {
    return cellTypeId;
  }

  public void setCellTypeId(String newCellTypeId)
  {
    cellTypeId = newCellTypeId;
  }

  public boolean isCellMandatoryFlag()
  {
    return cellMandatoryFlag;
  }

  public void setCellMandatoryFlag(boolean newCellMandatoryFlag)
  {
    cellMandatoryFlag = newCellMandatoryFlag;
  }

  public String getCellSQLName()
  {
    return cellSQLName;
  }

  public void setCellSQLName(String newCellSQLName)
  {
    cellSQLName = newCellSQLName;
  }

  public boolean isCellPrimaryKeyFlag()
  {
    return cellPrimaryKeyFlag;
  }

  public void setCellPrimaryKeyFlag(boolean newCellPrimaryKeyFlag)
  {
    cellPrimaryKeyFlag = newCellPrimaryKeyFlag;
  }

  public String getCellTypeName()
  {
    return cellTypeName;
  }

  public void setCellTypeName(String newCellTypeName)
  {
    cellTypeName = newCellTypeName;
  }
}