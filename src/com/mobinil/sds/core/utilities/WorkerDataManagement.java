package com.mobinil.sds.core.utilities;
import java.util.*;
import java.rmi.server.UID;

public class WorkerDataManagement 
{
//this in cr.worker
  private WorkerDataManagement()
  {
  }
  
  private static Hashtable dataTable = new Hashtable();

  public static String addWorker(AbstractWorker worker)
  {
    Buffer b= new Buffer();
    String id = new UID().toString();
    worker.setBuffer(b);
    dataTable.put(id,b);
    worker.start();
    return id;
  }

  public static HashMap getWorkerData (String id)
  {
    HashMap data=null;
    try
    {
      Object o = dataTable.get(id);
      if (o != null)
      {
          data = ((Buffer)o).getData();
      }
      else
          data = new HashMap();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return data;
  }

  public static boolean checkWorkerDataFinished (String id)
  {
    boolean finished = false ;
    try
    {
      Object o = dataTable.get(id);
      HashMap data = ((Buffer)o).getData();
      if(data != null)
      finished = true;

      Utility.logger.debug("Process Finished : "+finished);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return finished;
  }
  
  public static void removeWorkerData (String id)
  {
    try
    {
      dataTable.remove(id); 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String argsp[])
  {
      UID userId = new UID();
        Utility.logger.debug("User Id: " + userId);
  }
}