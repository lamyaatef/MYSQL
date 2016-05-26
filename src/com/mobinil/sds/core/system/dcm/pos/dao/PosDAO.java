package com.mobinil.sds.core.system.dcm.pos.dao;
import com.mobinil.sds.core.system.dcm.pos.model.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.Utility;
import java.util.Vector;

public class PosDAO 
{ 
  public static Vector getPOSByAgentID(Connection con , int agentID)throws Exception
  {
    Statement stmt = con.createStatement();
    POSDetailModel posModel = null;
    Vector POSList = new Vector();
    String sqlString = "SELECT * FROM VW_DCM_POS_DETAIL WHERE POS_ID IN (SELECT POS_ID "+
                       "FROM DCM_POS_DCM_USER_ACCESS WHERE DCM_USER_ID = "+agentID+
                       ") AND POS_STATUS_TYPE_ID =2 AND POS_STATE_ID = 1";
    ResultSet rs = stmt.executeQuery(sqlString);
    while(rs.next())
    {
      posModel = new POSDetailModel(con, rs);
      POSList.add(posModel);
    }
    rs.close();
    stmt.close();
    return POSList;
  }
  public static POSDetailModel getAcceptedPOSByID(Connection con , int posID)
  {
    POSDetailModel acceptedModel = new POSDetailModel();
     try
      {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID = 6 AND POS_DETAIL_ID = "+posID;
        Utility.logger.debug("SQLLLLLLLLLL: "+sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          acceptedModel = new POSDetailModel(con , rs);
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return acceptedModel;
  }
  
    public static Vector getPOSByIDandStatus(Connection con , int posID,String posStatusId)
  {
    Vector pendingModelVector = new Vector();
    POSDetailModel pendingModel = new POSDetailModel();
     try
      {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID = "+posStatusId+" AND POS_ID IN ( "+
                           "SELECT POS_ID FROM VW_DCM_POS_DETAIL WHERE POS_DETAIL_ID = "+posID+")";
        Utility.logger.debug("SQLLLLLLLLLL: "+sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          pendingModel = new POSDetailModel(con , rs);
          pendingModelVector.add(pendingModel);
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return pendingModelVector;
  }

  public void addNewPOS(Connection con , POSDetailModel posModel , String superAdminFlag)
  {
    try
      {
        String statusDcmPosDetail = "";
        String statusGenDcm = "";
        if(superAdminFlag.compareTo("-1")==0)
        {
          statusDcmPosDetail = "1";
          statusGenDcm = "18";
        }
        else if(superAdminFlag.compareTo("0")==0)
        {
          statusDcmPosDetail = "2";
          statusGenDcm = "18";
        }
        else if(superAdminFlag.compareTo("1")==0)
        {
          statusDcmPosDetail = "6";
          statusGenDcm = "1";
        }
        Long lDcmId = insertGenDcm(con,posModel,statusGenDcm);
        Long pos_detail_id = insertPosDetail(con,posModel,lDcmId+"",statusDcmPosDetail,"1","");
      }
      catch(Exception e)
      {  
      e.printStackTrace();
      }

  }
  public static Vector getLegalForms(Connection con)
  {
    Vector legalFormVector = new Vector();
    try
      { 
        
        Statement stmt = con.createStatement();
        String sqlString = "SELECT LEGAL_FORM_NAME FROM DCM_LEGAL_FORM";
        ResultSet legalFormRS = stmt.executeQuery(sqlString);
        while (legalFormRS.next())
        {
          legalFormVector.add(legalFormRS.getString("LEGAL_FORM_NAME"));
        }
        legalFormRS.close();
        stmt.close();
        
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
  return legalFormVector;
  }
  public static Vector getRegions(Connection con)
  {
    Vector regionVector = new Vector();
      try
      { 
        Statement stmt = con.createStatement();
        String sqlString = "SELECT REGION_NAME FROM DCM_REGION";
        ResultSet legalFormRS = stmt.executeQuery(sqlString);
        while (legalFormRS.next())
        {
          regionVector.add(legalFormRS.getString("REGION_NAME"));
        }
        legalFormRS.close();
        stmt.close();
        
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
  return regionVector;
  }
  public static Vector getPlaceType(Connection con)
  {
    Vector placeTypeVector = new Vector();
    try
      { 
        Statement stmt = con.createStatement();
        String sqlString = "SELECT POS_PLACE_TYPE_NAME FROM DCM_POS_PLACE_TYPE";
        ResultSet legalFormRS = stmt.executeQuery(sqlString);
        while (legalFormRS.next())
        {
          placeTypeVector.add(legalFormRS.getString("POS_PLACE_TYPE_NAME"));
        }
        legalFormRS.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
    return placeTypeVector;
  }
  public Vector getPOSByFilter(Connection con , String POSName , String POSCode ,String POSPhone ,
                                String POSCommercialNumber , String POSCity, String POSStatus)
  {
    boolean andFlag = false ;
    POSDetailModel searchModel =  new POSDetailModel();
    Vector POSVector = new Vector();
    Utility.logger.debug("Status: "  +POSStatus);
    int emptyFlag =5;
    String sqlString = "SELECT POS_DETAIL_ID,POS_NAME,REGION_NAME ,  POS_CODE , POS_EMAIL "+
                        ",POS_STATUS_TYPE_ID, POS_STATUS_TYPE_NAME FROM VW_DCM_POS_DETAIL ";
                        
    if(!POSName.equalsIgnoreCase(""))
    {
        emptyFlag--;
      if(andFlag == true)
        sqlString += " AND POS_NAME LIKE '%"+POSName+"%'";
      else{
        sqlString += " WHERE POS_NAME LIKE '%"+POSName+"%'";
        andFlag = true;
    }
    }
   if(!POSCode.equalsIgnoreCase(""))
    {
        emptyFlag--;
      if(andFlag == true)
        sqlString += "AND POS_CODE ='"+POSCode+"'";
      else{
        sqlString += "WHERE POS_CODE ='"+POSCode+"'";
        andFlag = true;
      }
    }
    if(!POSCommercialNumber.equalsIgnoreCase(""))
    {
    emptyFlag--;
      if(andFlag == true)
        sqlString += "AND POS_COMMERCIAL_NUMBER ='"+POSCommercialNumber+"'";
      else{
        sqlString += "WERE POS_COMMERCIAL_NUMBER ='"+POSCommercialNumber+"'";
        andFlag = true;
      }
    }    
    
    if(!POSCity.equalsIgnoreCase(""))
    {
    emptyFlag--;
       
      if(andFlag == true)
        sqlString += "AND CITY_CODE ='"+1+"'";
      else{
        sqlString += "WHERE CITY_CODE ='"+1+"'";
        andFlag = true;
      }

    }  
    if(!POSStatus.equalsIgnoreCase("")){
       emptyFlag--;
    int POSStatusID = Integer.parseInt(POSStatus);
     if(andFlag == true){
     
            sqlString += "AND POS_STATUS_TYPE_ID ="+POSStatusID+"";
          }
          else{
            sqlString += "WHERE POS_STATUS_TYPE_ID ="+POSStatusID+"";
            andFlag = true;
          }
      }

    Utility.logger.debug("EMPTY FLAAAAAAAAAG: "+emptyFlag);  
      if(emptyFlag == 5)
      {
        sqlString += " WHERE POS_STATE_ID ='1'";
      }
    try{
        Statement stmt = con.createStatement();
        

        Utility.logger.debug("sqlString : "+sqlString);
        ResultSet rs   = stmt.executeQuery(sqlString);
    while (rs.next())
    {
      searchModel = new POSDetailModel();
      searchModel.setPosName(rs.getString("POS_NAME"));
      searchModel.setPOSCode(rs.getString("POS_CODE"));
      searchModel.setPosEmail(rs.getString("POS_EMAIL"));
      searchModel.setPosRegionName(rs.getString("REGION_NAME"));
      searchModel.setPosStatusID(rs.getInt("POS_STATUS_TYPE_ID"));
      searchModel.setPosStatusName(rs.getString("POS_STATUS_TYPE_NAME"));
      searchModel.setPosID(rs.getInt("POS_DETAIL_ID"));
      POSVector.add(searchModel);
    }
    rs.close();
    stmt.close();
    }
    catch(Exception Ex)
    {
      Ex.printStackTrace();
    }
    System.out.print("SIZE:   "+POSVector.size());
    return POSVector;
  }
  public static Vector getPOSPhones(Connection con , int posID)
  {
    Vector POSPhones = new Vector();
    try
      { 
        Statement stmt = con.createStatement();
        
        String sqlString = "SELECT POS_PHONE_NUMBER FROM DCM_POS_PHONE WHERE POS_DETAIL_ID =" + posID;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          Utility.logger.debug("POS PHONES : "+rs.getString("POS_PHONE_NUMBER"));
          POSPhones.add(rs.getString("POS_PHONE_NUMBER"));
        }
        rs.close();
        stmt.close();

      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return POSPhones;
  }
  public static Vector getPOSPartners(Connection con , int posID)
  {
    Vector POSPartners = new Vector();
    try
      { 
        Statement stmt = con.createStatement();
        String sqlString = "SELECT POS_OWNER_NAME FROM DCM_POS_OWNER WHERE POS_DETAIL_ID ="+ posID + " AND POS_OWNER_FLAG  = 0";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          Utility.logger.debug("POSPartners: "+rs.getString("POS_OWNER_NAME"));
          POSPartners.add(rs.getString("POS_OWNER_NAME"));
        }
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return POSPartners;
  }

  public static POSDetailModel getPOSByID(Connection con , int posID)
  {
      POSDetailModel posDetails = null;
      try
      { 
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM VW_DCM_POS_DETAIL WHERE POS_DETAIL_ID = " + posID; 
        Utility.logger.debug("SQLLLZZZZZZZ: "+sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
        {
            posDetails  = new POSDetailModel(con , rs);
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    return posDetails;
  }
  public static void updatePosStatus(Connection con , int posID , int posStatusID , String superAdminFlag)
  {
      try
      { 
        Statement stmt = con.createStatement();
        
        int  prevStatus = 0;
        int  prevID = 0;
        int POS_ID = 0;
        String POS_NAME = "",POS_ADDRESS ="" ,POS_EMAIL="";
        String sqlString = "SELECT POS_ID,POS_STATUS_TYPE_ID,POS_DETAIL_ID,POS_NAME , POS_EMAIL ,POS_ADDRESS FROM DCM_POS_DETAIL WHERE POS_ID IN ( "+
                           "SELECT POS_ID FROM DCM_POS_DETAIL WHERE POS_DETAIL_ID= "+posID+")  AND POS_STATE_ID = 1";
                           Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);                   
        while(rs.next())
        {
          prevStatus = rs.getInt("POS_STATUS_TYPE_ID");
          prevID = rs.getInt("POS_DETAIL_ID");
          POS_ID =rs.getInt("POS_ID");
          POS_NAME = rs.getString("POS_NAME");
          POS_ADDRESS = rs.getString("POS_ADDRESS");
          POS_EMAIL = rs.getString("POS_EMAIL");
        }

        String genDcmNextStatus = "";
        if(posStatusID == 3 || posStatusID == 4)
        {
          genDcmNextStatus = "3";
        }
        else if(posStatusID == 6)
        {
          genDcmNextStatus = "1";

          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 1 WHERE POS_DETAIL_ID = "+posID;
          stmt.executeUpdate(sqlString);

          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0 "+
                        " WHERE POS_DETAIL_ID = "+ prevID+" ";
          stmt.executeUpdate(sqlString);
        } 

        sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATUS_TYPE_ID = "+posStatusID+" WHERE POS_DETAIL_ID = "+posID;
          stmt.executeUpdate(sqlString);

        if(posStatusID == 6 && prevStatus == 6)
        {
          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATUS_TYPE_ID = 5 WHERE POS_DETAIL_ID = "+ prevID+" ";
          stmt.executeUpdate(sqlString);
        }

        

        if(genDcmNextStatus.compareTo("1")==0)
        {
          sqlString = "SELECT POS_ID,POS_STATUS_TYPE_ID,POS_DETAIL_ID,POS_NAME , POS_EMAIL ,POS_ADDRESS FROM DCM_POS_DETAIL WHERE POS_DETAIL_ID= "+
                          +posID+" ";
                        
          Statement stmt3 = con.createStatement();
          ResultSet res = stmt3.executeQuery(sqlString);                   
          String POS_IDX = "";
          String POS_NAMEX = "";
          String POS_ADDRESSX = "";
          String POS_EMAILX = "";
          
          if(res.next())
          {
            POS_IDX = res.getString("POS_ID");
            POS_NAMEX = res.getString("POS_NAME");
            POS_ADDRESSX = res.getString("POS_ADDRESS");
            POS_EMAILX = res.getString("POS_EMAIL");
          }

          res.close();
          stmt3.close();
          
          POSDetailModel posModel = new POSDetailModel();
          posModel.setPosName(POS_NAMEX);
          posModel.setPosEmail(POS_EMAILX);
          posModel.setPosAddress(POS_ADDRESSX);
          updateGenDcm(con , POS_IDX ,posModel ,genDcmNextStatus);
        }
        else if(genDcmNextStatus.compareTo("3")==0)
        {
          sqlString = "UPDATE GEN_DCM SET DCM_STATUS_ID  = "+genDcmNextStatus+" where dcm_id = "+POS_ID+" ";
          stmt.executeUpdate(sqlString);
        }
        /*if((prevStatus == 2 || prevStatus == 6) && (posStatusID == 2 || posStatusID == 4 || posStatusID == 6))
        {
          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0,"+
                      " POS_STATUS_TYPE_ID = 5 WHERE POS_DETAIL_ID = "+ prevID+
                      " AND POS_STATUS_TYPE_ID = 2";
          Utility.logger.debug(sqlString);
          int rows =  stmt.executeUpdate(sqlString);
          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 1 , POS_STATUS_TYPE_ID = "+posStatusID+" WHERE POS_DETAIL_ID = "+posID;
          Utility.logger.debug(sqlString);            
          rows = stmt.executeUpdate(sqlString);
          if(posStatusID == 6)
          {
            sqlString = "SELECT POS_ID,POS_STATUS_TYPE_ID,POS_DETAIL_ID,POS_NAME , POS_EMAIL ,POS_ADDRESS FROM DCM_POS_DETAIL WHERE POS_DETAIL_ID= "+
                          +posID+"  AND POS_STATE_ID = 1";
                         Utility.logger.debug(sqlString);                           
            Statement stmt3 = con.createStatement();
            ResultSet res = stmt3.executeQuery(sqlString);                   
            if(res.next())
            {
              POS_ID = res.getInt("POS_ID");
              POS_NAME = res.getString("POS_NAME");
              POS_ADDRESS = res.getString("POS_ADDRESS");
              POS_EMAIL = res.getString("POS_EMAIL");
            } 
            res.close();
            stmt3.close();
            sqlString = "UPDATE GEN_DCM SET DCM_STATUS_ID  = 1,DCM_NAME ='"+POS_NAME+"',DCM_ADDRESS = '"+
                        POS_ADDRESS+"',DCM_EMAIL ='"+POS_EMAIL+"' WHERE DCM_ID = "+POS_ID;
                        Utility.logger.debug(sqlString);
            rows = stmt.executeUpdate(sqlString);
          }
        }
        else if((prevStatus == 2 || prevStatus == 6) && (posStatusID == 1 || posStatusID == 3))
        {
          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0 , POS_STATUS_TYPE_ID = "+posStatusID+" WHERE POS_DETAIL_ID = "+posID;
          Utility.logger.debug(sqlString);            
          int rows = stmt.executeUpdate(sqlString);

          String nextStatusGenDcm = "";
          if(posStatusID == 1) nextStatusGenDcm = "18";
          else if(posStatusID == 3) nextStatusGenDcm = "3";
          String sqlString2 = "update gen_dcm set dcm_status_id = "+nextStatusGenDcm+" where DCM_ID = "+POS_ID;
          stmt.executeUpdate(sqlString2);
        }
        else if(prevStatus == 1)
        {
          if(posStatusID != 3)
          {
            sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0,"+
                        " POS_STATUS_TYPE_ID = 5 WHERE POS_DETAIL_ID = "+ prevID+
                        " AND POS_STATUS_TYPE_ID = 1";
            Utility.logger.debug(sqlString);
            int rows =  stmt.executeUpdate(sqlString);
            sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 1 , POS_STATUS_TYPE_ID = "+posStatusID+" WHERE POS_DETAIL_ID = "+posID;
            Utility.logger.debug(sqlString);            
            rows = stmt.executeUpdate(sqlString);
            //Assumption will never happen but just in case
            if(posStatusID == 6)
            {
              sqlString = "SELECT POS_ID,POS_STATUS_TYPE_ID,POS_DETAIL_ID,POS_NAME , POS_EMAIL ,POS_ADDRESS FROM DCM_POS_DETAIL WHERE POS_DETAIL_ID= "+
                            +posID+"  AND POS_STATE_ID = 1";
                           Utility.logger.debug(sqlString);                           
              Statement stmt2 = con.createStatement();
              ResultSet res = stmt2.executeQuery(sqlString);                   
              if(res.next())
              {
                POS_ID = res.getInt("POS_ID");
                POS_NAME = res.getString("POS_NAME");
                POS_ADDRESS = res.getString("POS_ADDRESS");
                POS_EMAIL = res.getString("POS_EMAIL");
              } 
              res.close();
              sqlString = "UPDATE GEN_DCM SET DCM_NAME ='"+POS_NAME+"',DCM_ADDRESS = '"+
                          POS_ADDRESS+"',DCM_EMAIL ='"+POS_EMAIL+"' WHERE DCM_ID = "+POS_ID;
                          Utility.logger.debug(sqlString);                          
              rows = stmt2.executeUpdate(sqlString);

            }
          }
          if(posStatusID == 3)
          {
            sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0 , POS_STATUS_TYPE_ID = "+posStatusID+" WHERE POS_DETAIL_ID = "+posID;
            Utility.logger.debug(sqlString);            
            int rows = stmt.executeUpdate(sqlString);
              
          }
        }*/
        
        rs.close();
        stmt.close();
      }catch(Exception ex)
      {
        ex.printStackTrace();
      }
  }

  
  public int editExistingPOS(Connection con , POSDetailModel posModel , String superAdminFlag)
  {
    int pos_detail_id = 0;
    try
      {
        Statement stmt = con.createStatement();

        String statusDcmPosDetail = "";
        String statusGenDcm = "";
        if(superAdminFlag.compareTo("-1")==0)
        {
          statusDcmPosDetail = "1";
          statusGenDcm = "18";
        }
        else if(superAdminFlag.compareTo("0")==0)
        {
          statusDcmPosDetail = "2";
          statusGenDcm = "18";
        }
        else if(superAdminFlag.compareTo("1")==0)
        {
          statusDcmPosDetail = "6";
          statusGenDcm = "1";
        }
        
        int posLastPOSID = posModel.getPoslastPOSID();
        int last_pos_status_id = 0;
        int pos_state_id = 0;
        String sqlString="";
        
        sqlString = "SELECT POS_ID,POS_STATUS_TYPE_ID,POS_DETAIL_ID FROM DCM_POS_DETAIL WHERE POS_DETAIL_ID ="+posLastPOSID;
        ResultSet rs = stmt.executeQuery(sqlString);

        int POS_ID = 0;  
        String posDetailId = "";
        
        if(rs.next())
        {
          POS_ID = rs.getInt("POS_ID");
          last_pos_status_id = rs.getInt("POS_STATUS_TYPE_ID");
          posDetailId = rs.getString("POS_DETAIL_ID");
          Utility.logger.debug("POS_STATUS_TYPE_ID"+pos_state_id);
        }

        boolean changeState  = false;
        if(last_pos_status_id != 2 && last_pos_status_id != 6)
        {
          pos_state_id = 1;
          changeState = true;
        }
        else
        {
          if(last_pos_status_id == 2 && (superAdminFlag.compareTo("0")==0 || superAdminFlag.compareTo("1")==0))
          {
            pos_state_id = 1;
            changeState = true;  
          }
          else if(last_pos_status_id == 6 && superAdminFlag.compareTo("1")==0)
          {
            pos_state_id = 1;
            changeState = true;  
          }
          else
          {
            pos_state_id = 0;
          }
        }
        
        Long lpos_detail_id = insertPosDetail(con,posModel,POS_ID+"",statusDcmPosDetail,pos_state_id+"",posDetailId);
        
        pos_detail_id = Integer.parseInt(lpos_detail_id+"");
        if(changeState)
        {
          updateGenDcm(con,POS_ID+"",posModel,statusGenDcm);
          sqlString = "UPDATE DCM_POS_DETAIL SET POS_STATE_ID = 0"+" WHERE POS_ID="+POS_ID+" AND POS_DETAIL_ID <> "+pos_detail_id; 
          stmt.executeUpdate(sqlString);
        }
        rs.close();
        stmt.close();        
      }
      catch(Exception e)
      {  
        e.printStackTrace();
      }
      return pos_detail_id;
  }
  public static POSDetailModel getPOSByPOSID(Connection con , int posID)
  {
    POSDetailModel acceptedModel = new POSDetailModel();
     try
      {
        Statement stmt = con.createStatement();
        String sqlString = "SELECT * FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID = 2 AND POS_ID ="+posID;

        Utility.logger.debug("SQLLLLLLLLLL: "+sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        while(rs.next())
        {
          acceptedModel = new POSDetailModel(con , rs);
        }
        rs.close();
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return acceptedModel;
  }

  public static Long insertPosDetail(Connection con , POSDetailModel posModel , String dcmId,String statusTypeId,String stateId,String lastPosDetailId)
  {
     Long posDetailId = null;  
     try
      {
        Statement stmt = con.createStatement();      
        String posName = posModel.getPosName();
        String posCode = posModel.getPOSCode();
        String posEmail = posModel.getPosEmail();
        String posAddress = posModel.getPosAddress();
        int posRegion = posModel.getPosRegionID();
        Vector posPhones = posModel.getPosPhones();
        int posCommercialNumber = posModel.getPosCommercialNumber();
        int posTaxID = posModel.getPosTaxID();
        int posLegalForm = posModel.getPosLegalFormID();
        Vector posPartners = posModel.getPosPartners();
        int posPlaceType = posModel.getPosPlaceTypeID();
        String posOwnerName = posModel.getPosOwnerName();
        int posOnwerIDType = posModel.getPosOwnerIDTypeID();
        String posOwnerIDNumber = posModel.getPosOwnerIDNumber();
        Vector posOwnerPhones = posModel.getPosOwnerPhones();
        String posOwnerBirthDate = posModel.getPosOwnerBirthDate();
        String posManagerName = posModel.getPosManagerName();
        int  posManagerIDtype = posModel.getPosManagerIDTypeID();
        String posManagerIDNumber = posModel.getPosManagerIDNumber();
        Vector posManagerPhones = posModel.getPosManagerPhones();
        String posManagerBirthDate = posModel.getPosManagerBirthDate();
        int UserID = posModel.getUserID();
        
        posDetailId = Utility.getSequenceNextVal(con , "SEQ_DCM_POS_DETAIL");
        
        String sqlString = "INSERT INTO DCM_POS_DETAIL (POS_DETAIL_ID,POS_ID ,POS_CODE,"+
                    "POS_NAME,POS_EMAIL,POS_COMMERCIAL_NUMBER,POS_TAX_ID,"+
                    "POS_ADDRESS,POS_STATUS_TYPE_ID,REGION_ID,LEGAL_FORM_ID,"+
                    "POS_PLACE_TYPE_ID,TIMESTAMP,USER_ID,POS_STATE_ID,LAST_POS_DETAIL_ID)values('"+posDetailId+
                    "','"+dcmId+"','"+posCode+"','"+posName+"','"+posEmail+"','"+posCommercialNumber+
                    "','"+posTaxID+"','"+posAddress+"','"+statusTypeId+"','"+posRegion+"','"+posLegalForm+
                    "','"+posPlaceType+"',sysdate,'"+UserID+"','"+stateId+"','"+lastPosDetailId+"')";

        Utility.logger.debug(sqlString);
        stmt.executeUpdate(sqlString);                            
        
        for (int i = 0 ; i < posPhones.size() ; i++ )
        {
          sqlString = "INSERT INTO DCM_POS_PHONE (POS_DETAIL_ID,POS_PHONE_NUMBER) VALUES ("+posDetailId+",'"+posPhones.get(i)+"')";
          stmt.executeUpdate(sqlString);
        }

        insertDcmPosPartners(con,posPartners,posDetailId+"");
        insertDcmPosManager(con,posDetailId+"",posManagerName,posManagerBirthDate,posManagerIDtype+"",posManagerIDNumber,posManagerPhones);
        insertDcmPosOwner(con,posDetailId+"",posOwnerName,posOwnerBirthDate,posOnwerIDType+"",posOwnerIDNumber,posOwnerPhones);

        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return posDetailId;
  }


  public static Long insertGenDcm(Connection con , POSDetailModel posModel ,String statusId)
  {
     Long genDcmId = null;
     try
      {
        Statement stmt = con.createStatement();      

        String posName = posModel.getPosName();
        String posCode = posModel.getPOSCode();
        String posEmail = posModel.getPosEmail();
        String posAddress = posModel.getPosAddress();
        
        genDcmId = Utility.getSequenceNextVal(con , "SEQ_GEN_DCM_ID" );
        
        String sqlString = "INSERT INTO GEN_DCM (DCM_ID ,DCM_NAME, DCM_EMAIL , DCM_STATUS_ID, DCM_LEVEL_ID , DCM_ADDRESS , DCM_CODE)"+
                    "VALUES("+genDcmId+",'"+posName+"','"+posEmail+"',"+statusId+",3,'"+posAddress+"','"+posCode+"')";
        stmt.executeUpdate(sqlString);
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
      return genDcmId;
  }

  public static void updateGenDcm(Connection con , String dcmId ,POSDetailModel posModel ,String statusId)
  {
     try
      {
        Statement stmt = con.createStatement();      

        String posName = posModel.getPosName();
        String posEmail = posModel.getPosEmail();
        String posAddress = posModel.getPosAddress();
        
        String sqlString = "UPDATE GEN_DCM set DCM_NAME = '"+posName+"', DCM_EMAIL = '"+posEmail+"', DCM_STATUS_ID = "+statusId+", DCM_ADDRESS = '"+posAddress+"' "+
                    " where dcm_id = "+dcmId+" " ; 
                    
        stmt.executeUpdate(sqlString);
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
  }

  public static void insertDcmPosPartners(Connection con ,Vector posPartners,String posDetailId)
  {
     try
      {
        Statement stmt = con.createStatement();      

        for(int i = 0 ; i < posPartners.size() ; i++)
        {
          Long partner_id = Utility.getSequenceNextVal(con , "SEQ_DCM_POS_OWNER");
          String sqlString = "INSERT INTO DCM_POS_OWNER (POS_OWNER_ID , POS_OWNER_NAME , POS_DETAIL_ID , POS_OWNER_FLAG) VALUES"+
                      "('" + partner_id + "','" + posPartners.get(i) + "','" + posDetailId + "','0')";
          stmt.executeUpdate(sqlString);
        }
    
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
  }

  public static void insertDcmPosOwner(Connection con ,String posDetailId,String ownerName,String ownerBirthDate,String ownerTypeId,String ownerIdNumber,Vector posOwnerPhones)
  {
     try
      {
        Statement stmt = con.createStatement();      

        Long lOwnerId = Utility.getSequenceNextVal(con ,"SEQ_DCM_POS_OWNER");
        String sqlString = "INSERT INTO DCM_POS_OWNER (POS_OWNER_ID , POS_OWNER_NAME , POS_OWNER_BIRTHDATE ,"+
                    "POS_OWNER_ID_TYPE_ID ,POS_OWNER_ID_NUMBER , POS_DETAIL_ID , POS_OWNER_FLAG) values ('"+lOwnerId+"','"+
                    ownerName+"',TO_DATE('"+ownerBirthDate+"','yyyy-mm-dd'),'"+ownerTypeId+"','"+ownerIdNumber+"','"+
                    posDetailId+"','1')";
                    
        stmt.executeUpdate(sqlString);
        
        for(int j = 0 ; j < posOwnerPhones.size() ; j++)
        {
          sqlString = "INSERT INTO DCM_POS_OWNER_PHONE (POS_OWNER_ID,POS_OWNER_PHONE_NUMBER) VALUES ("+lOwnerId+","+posOwnerPhones.get(j)+")";
          stmt.executeUpdate(sqlString);
        }

        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
  }

  public static void insertDcmPosManager(Connection con ,String posDetailId,String managerName,String managerBirthDate,String managerTypeId,String managerIdNumber,Vector posManagerPhones)
  {
     try
      {
        Statement stmt = con.createStatement();      

        Long lManagerId = Utility.getSequenceNextVal(con ,"SEQ_DCM_POS_MANAGER");
        String sqlString = "INSERT INTO DCM_POS_MANAGER (POS_MANAGER_ID , POS_MANAGER_NAME , POS_MANAGER_BIRTHDATE ,"+
                    "POS_MANAGER_ID_TYPE_ID ,POS_MANAGER_ID_NUMBER , POS_DETAIL_ID) values ('"+lManagerId+"','"+
                    managerName+"',TO_DATE('"+managerBirthDate+"','yyyy-mm-dd'),'"+managerTypeId+"','"+managerIdNumber+"','"+
                    posDetailId+"')";
        stmt.executeUpdate(sqlString);
        
        for(int k = 0 ; k < posManagerPhones.size() ; k++)
        {
          sqlString = "INSERT INTO DCM_POS_MANAGER_PHONE (POS_MANAGER_ID,POS_MANAGER_PHONE_NUMBER) VALUES ("+lManagerId+","+posManagerPhones.get(k)+")";
          stmt.executeUpdate(sqlString);
        }
        stmt.close();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
  }
  /////////////////////////////////////////// Mohamed Youssef
  
  public PosDAO()
  {
  }
}