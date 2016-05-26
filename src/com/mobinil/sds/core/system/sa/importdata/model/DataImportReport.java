package com.mobinil.sds.core.system.sa.importdata.model;
import java.util.Vector;
import java.io.*;

public class DataImportReport  implements Serializable
{
  Vector report;
  int numOfRecordsUpdated;
  String operation;
  private int numOfRecordsInserted;

  public DataImportReport()
  {
  }

  public Vector getReport()
  {
    return report;
  }

  public void setReport(Vector newReport)
  {
    report = newReport;
  }

  public int getNumOfRecordsUpdated()
  {
    return numOfRecordsUpdated;
  }

  public void setNumOfRecordsUpdated(int newNumOfRecordsUpdated)
  {
    numOfRecordsUpdated = newNumOfRecordsUpdated;
  }

  public String getOperation()
  {
    return operation;
  }

  public void setOperation(String newOperation)
  {
    operation = newOperation;
  }

    /**
     * @return the numOfRecordsInserted
     */
    public int getNumOfRecordsInserted() {
        return numOfRecordsInserted;
    }

    /**
     * @param numOfRecordsInserted the numOfRecordsInserted to set
     */
    public void setNumOfRecordsInserted(int numOfRecordsInserted) {
        this.numOfRecordsInserted = numOfRecordsInserted;
    }

}