package com.mobinil.sds.core.system.dcm.worker;

import java.util.*;
import java.sql.*;
import java.rmi.server.UID;
import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.*;
import com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.core.utilities.*;

public class GenericModelWorker extends AbstractWorker
{
  private String strUserId;
  private String genericTableName = null;

  public GenericModelWorker(String strUserId,String genericTableName)
  {
    this.strUserId = strUserId;
    this.genericTableName = genericTableName;
  }

  public void run ()
  {
    HashMap data=null;
    try
    {
      Connection con = Utility.getConnection();

      GenericModel genericModel = new GenericModel();
      GenericModelDAO genericDAO = new GenericModelDAO();
      genericModel = genericDAO.getColumns(con , genericTableName);
      data = new HashMap();
      if(genericModel == null)
      {
         data.put(InterfaceKey.HASHMAP_KEY_MESSAGE ,"There is no table with the name "+genericTableName+".");
      }  
      else
      {
        Vector genericModelVector = genericDAO.getModels(con , genericModel);
        Utility.logger.debug("t1 = "+ (genericModelVector ==null));
        int columnCount = genericDAO.get_column_count(con , genericTableName);
        int rowCount = genericDAO.get_row_count(con , genericTableName);
        Vector columnNames = genericDAO.get_column_Names(con , genericTableName);
        data.put(DCMInterfaceKey.DCM_GENERIC_MODEL_DATA_VECTOR,genericModelVector);
        data.put(DCMInterfaceKey.DCM_GENERIC_TABLE_COLUMN_COUNT,columnCount+"");
        data.put(DCMInterfaceKey.VECTOR_COLUMN_NAMES , columnNames);      
        data.put(DCMInterfaceKey.DCM_GENERIC_TABLE_ROW_COUNT,rowCount+"");
      }
      Utility.closeConnection(con);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
   this.myBuffer.put(data);
  }


}