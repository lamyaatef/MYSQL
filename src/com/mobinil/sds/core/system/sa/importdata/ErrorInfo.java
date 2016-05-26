package com.mobinil.sds.core.system.sa.importdata;

public class ErrorInfo 
{
  int rowNum=-1;
  String cellName="";
  String errorMsg="";

  public ErrorInfo()
  {
  }

  public int getRowNum()
  {
    return rowNum;
  }

  public void setRowNum(int newRowNum)
  {
    rowNum = newRowNum;
  }

  public String getCellName()
  {
    return cellName;
  }

  public void setCellName(String newCellName)
  {
    cellName = newCellName;
  }

  public String getErrorMsg()
  {
    return errorMsg;
  }

  public void setErrorMsg(String newErrorMsg)
  {
    errorMsg = newErrorMsg;
  }

    @Override
    public String toString() {
        return "ErrorInfo{" + "rowNum=" + rowNum + ", cellName=" + cellName + ", errorMsg=" + errorMsg + '}';
    }
  
}