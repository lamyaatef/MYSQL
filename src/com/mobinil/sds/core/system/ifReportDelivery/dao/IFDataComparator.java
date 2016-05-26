package com.mobinil.sds.core.system.ifReportDelivery.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.StringTokenizer;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import com.mobinil.sds.core.utilities.Utility;
//import com.sun.org.apache.bcel.internal.generic.RETURN;

public class IFDataComparator extends Thread {
	private int jobId,userId;
	private boolean complete=false,update=false;
	
	public IFDataComparator(int jobId,int flag){
		this.jobId=jobId;
		if(flag==0)
			complete=true;
		else
			update=true;
		
	}
	public void run(){
		System.out.println("thread started");
		if(complete)
		{
			try{
				Connection con=Utility.getConnection();
				
				//complete the data of first,second,third contract names from contracts table
				completeInfofortDataTable(con,jobId);
				System.out.println("data completion finished");
				
				//comparing the names from info fort and from the contracts
				
				//callCompare(con, jobId, userId);
				
				
				long startTime = System.currentTimeMillis();
				System.out.println("Start Time: "+startTime);
				//ResultSet rs=IFReportDeliveyDao.getInfoFortDataForCompare(con, String.valueOf(jobId));
				//compare(rs)
				compare(con, jobId, userId);
				//long t2=c.getTimeInMillis();
				long endTime = System.currentTimeMillis();
				System.out.println("End Time: "+endTime);
				//System.out.println("time took: "+(t2-t1));
				updateJobDetails(con, jobId);
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
		}else if(update){
			System.out.println("update the record type by import");
			updateInfofortDataTable(jobId);
			deleteTempData();
		}
		
		System.out.println("thread ended");
	}
	public void callCompare(Connection con,int jobId,int userId)
	{
		CallableStatement stmt=null;
		//Connection con=null;
		try {
			//con=Utility.getConnection();
			if(con!=null)
				System.out.println("connection OK");
			String procedureName = "Comparator";
			String query = null;
			query = "{call " + procedureName + "(?,?)}";
			stmt = con.prepareCall(query);
			
			stmt.setInt(1, jobId);
			stmt.setInt(2, userId);
			System.out.println("connection OK 2");
			stmt.execute();
			System.out.println("data completion done");
			//Utility.closeConnection(con);

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}catch(Exception ex){
		ex.printStackTrace();	
		}finally
		{	
			System.out.println("connection OK 3");
			Utility.closeCallbaleStatement(stmt);
			
			
		}
	}
	public void completeInfofortDataTable(Connection con,int jobId){
		CallableStatement stmt=null;
		//Connection con=null;
		try {
			//con=Utility.getConnection();
			if(con!=null)
				System.out.println("connection OK");
			String procedureName = "completeInfofortDataTable";
			String query = null;
			query = "{call " + procedureName + "(?)}";
			stmt = con.prepareCall(query);
			
			stmt.setInt(1, jobId);
			System.out.println("connection OK 2");
			stmt.execute();
			System.out.println("data completion done");
			//Utility.closeConnection(con);

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}catch(Exception ex){
		ex.printStackTrace();	
		}finally
		{	
			System.out.println("connection OK 3");
			Utility.closeCallbaleStatement(stmt);
			
			
		}
	}
	
	public  void updateInfofortDataTable(int jobId){
		CallableStatement stmt=null;
		Connection con=null;
		try {
			con=Utility.getConnection();
			if(con!=null)
				System.out.println("connection OK job id is "+jobId+" connnn is "+ con+" ");
			
			Statement stat = con.createStatement();
		    /* 
			String strSql = "update IF_CHANGE_TYPE_TMP set IF_CHANGE_TYPE_TMP.RECORD_TYPE ='INFO_FORT_ERROR' where record_type like 'INFO_FORT_ERROR%' ";

		      //Utility.logger.debug(strSql);
		      //debug("The query is " + strSql);
		      stat.executeQuery(strSql);
		      
		      strSql = "update IF_CHANGE_TYPE_TMP set IF_CHANGE_TYPE_TMP.RECORD_TYPE ='DIST_ERROR' where record_type like 'DIST_ERROR%' ";
		      stat.executeQuery(strSql);
		      
		      
		      strSql = "update IF_CHANGE_TYPE_TMP set IF_CHANGE_TYPE_TMP.RECORD_TYPE ='MATCHED' where record_type like 'MATCHED%' ";
		      stat.executeQuery(strSql);
		      
		      strSql = "update IF_CHANGE_TYPE_TMP set IF_CHANGE_TYPE_TMP.RECORD_TYPE ='NOT_ERROR' where record_type like 'NOT_ERROR%' ";
		      stat.executeQuery(strSql);
		      
		      
		      strSql = "update IF_CHANGE_TYPE_TMP set IF_CHANGE_TYPE_TMP.RECORD_TYPE ='UN_MATCHED' where record_type like 'UN_MATCHED%'";
		      stat.executeQuery(strSql);
		      stat.close();
			*/
			
//			Thread.sleep(300);
			
			
			
			String procedureName = "updateInfofortDataTable";
			String query = null;
			query = "{call " + procedureName + "(?,?)}";
			stmt = con.prepareCall(query);
			
			stmt.setInt(1, jobId);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			
		    		    
		    
		    
			System.out.println("connection OK 2");
			stmt.execute();
			System.out.println(stmt.getString(2)+" ddddddddddd");
			
			System.out.println("data update done");
			//Utility.closeConnection(con);

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}catch(Exception ex){
		ex.printStackTrace();	
		}finally
		{	
			System.out.println("connection OK 3");
			//Utility.closeCallbaleStatement(stmt);
			
			
		}
	}
	
	public void deleteTempData(){
		Connection con=null;
		try
	    {
			con=Utility.getConnection();
	      Statement stat = con.createStatement();
	      String strSql = "delete IF_CHANGE_TYPE_TMP";

	      //Utility.logger.debug(strSql);
	      
	      stat.executeQuery(strSql);
	      
	      
	      
	      //stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	}
	public boolean compare(ResultSet rs){
		String infoFort1stName="",infoFort2ndName="",infoFort3rdName="";
		String contract1stName="",contract2ndName="",contract3rdName="";
		String simSerial="";
		try{
			while(rs.next()){
				infoFort1stName=rs.getString("FIRST_NAME");
				infoFort2ndName=rs.getString("SECOND_NAME");
				infoFort3rdName=rs.getString("THIRD_NAME");
				
				//IF any name is null what to do??????????????
				
				contract1stName=rs.getString("CONTRACT_CUSTOMER_1ST_NAME");
				contract2ndName=rs.getString("CONTRACT_CUSTOMER_2ND_NAME");
				contract3rdName=rs.getString("CONTRACT_CUSTOMER_LST_NAME");
								
				System.out.println("sim serial:  "+rs.getString("SIM_SERIAL"));
				
				simSerial=rs.getString("SIM_SERIAL");
				
				System.out.println("comparing first name: "+compareTwoNames(removeSpaces(infoFort1stName), removeSpaces(contract1stName)));
				
				System.out.println("comparing second name: "+compareTwoNames(removeSpaces(infoFort2ndName), removeSpaces(contract2ndName)));
				
				System.out.println("comparing second name: "+compareTwoNames(removeSpaces(infoFort3rdName), removeSpaces(contract3rdName)));
				
				//if the three are ok update as matched
//				if(compareTwoNames(removeSpaces(infoFort1stName), removeSpaces(contract1stName)) && compareTwoNames(removeSpaces(infoFort2ndName), removeSpaces(contract2ndName)) && compareTwoNames(removeSpaces(infoFort3rdName), removeSpaces(contract3rdName)) )
				//update this sim as matched
//				IFReportDeliveyDao.updateJobDetailsData(String.valueOf(userId), simSerial, "2");
//				else
//				IFReportDeliveyDao.updateJobDetailsData(String.valueOf(userId), simSerial, "7");
				
			}	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
			
		
		
		 
		return true;
	}
	public static char normalize(char c1)
	{
	char cc=c1;
	if( c1=='\u0625' ||c1=='\u0627'||c1=='\u0623'||c1=='\u0622')
	cc='\u0627';
	else if(c1=='\u0624'||c1=='\u0648')
	cc='\u0648';
	else if(c1=='\u0629'||c1=='\u0647')
		cc='\u0647';
	else if(c1=='\u0626'||c1=='\u0649'||c1=='\u064A')
	cc='\u0649';
	return cc;
	}
	
	public static boolean checkContains(String infoFortName,String ContractName){
		//the two names must be without spaces
		
		if(infoFortName.contains(ContractName))
		return true;
	else if(ContractName.contains(infoFortName))
		return true;
	else		
		return false;
	}
	
	
	public static void orclcompareNames(int jobId)
	{
		
		
	//--first step normalize
	//--second step if the one contain the other then true
	//--if they not contain each other compare character by character
		
	Connection con=null;
	ResultSet rs=null;
	String infoFortName="",infoFort1stName="",infoFort2ndName="",infoFort3rdName="";
	String contractName="",contract1stName="",contract2ndName="",contract3rdName="";
	String simSerial="";
	String temp="";
	int firstNameRate=0,secondNameRate=0,thirdNameRate=0,totalRate=0;
	int counter=0;
	boolean check=false;
	
	
	try{
		
		//get the connection
		if(System.getProperty("oracle.jserver.version")!=null){
			con=DriverManager.getConnection("jdbc:default:connection:");
			System.out.println("connection is ok");
		}else{
			System.out.println("connection is not ok");
		}
		
		//excute the query and get the result set
		
		rs=IFReportDeliveyDao.getInfoFortDataForCompare(con, String.valueOf(jobId));

		
		while(rs.next()){
			infoFort1stName=rs.getString("FIRST_NAME");
			infoFort2ndName=rs.getString("SECOND_NAME");
			infoFort3rdName=rs.getString("THIRD_NAME");
			
			infoFortName=infoFort1stName+infoFort2ndName+contract3rdName;
			
			
			
			contract1stName=rs.getString("CONTRACT_CUSTOMER_1ST_NAME");
			contract2ndName=rs.getString("CONTRACT_CUSTOMER_2ND_NAME");
			contract3rdName=rs.getString("CONTRACT_CUSTOMER_LST_NAME");
			
			contractName=contract1stName+contract2ndName+contract3rdName;
			
			//normalizing the two names
			
			for(int i=0;i<infoFortName.length();i++)
			temp+=normalize(infoFortName.charAt(i));
			
			
			infoFortName=temp;
			temp="";
			
			for(int i=0;i<contractName.length();i++)
				temp+=normalize(contractName.charAt(i));
			contractName=temp;
			
			temp="";
			
			//check if one contain the other
			if(checkContains(removeSpaces(infoFortName), removeSpaces(contractName))){}
				//update this sim as matched
//				IFReportDeliveyDao.updateJobDetailsData(String.valueOf(""), simSerial, "2");
				
				else {		//comparing character by character
					
					
					//compare the first names
					for(int i=0;i<infoFort1stName.length();i++)
					{
						if(infoFort1stName.charAt(i)!=contract1stName.charAt(i))
							counter++;
					}
					firstNameRate=counter*6;
					counter=0;
					
					//compare the second names
					for(int i=0;i<infoFort2ndName.length();i++)
					{
						if(infoFort2ndName.charAt(i)!=contract2ndName.charAt(i))
							counter++;
					}
					secondNameRate=counter*4;
					counter=0;
					
					//compare the third names
					for(int i=0;i<infoFort3rdName.length();i++)
					{
						if(infoFort3rdName.charAt(i)!=contract3rdName.charAt(i))
							counter++;
					}
					firstNameRate=counter*2;
					counter=0;
					
					
					totalRate=firstNameRate+secondNameRate+thirdNameRate;
					
//					if(totalRate<30)
						//update this sim as matched
//						IFReportDeliveyDao.updateJobDetailsData(String.valueOf(""), simSerial, "2");
//						else 
//							//update as un matched
//						IFReportDeliveyDao.updateJobDetailsData(String.valueOf(""), simSerial, "7");	
					
				}
			
			totalRate=0;
			firstNameRate=0;
			secondNameRate=0;
			thirdNameRate=0;
			
			
			
		}	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	
	}
	
	public static boolean compareTwoNames(String name,String cName){
		if(name.length()!=cName.length())
			return false;
		
		char c1='a',c2='a';
		for(int i=0;i<name.length();i++){
			c1=name.charAt(i);
			c2=cName.charAt(i);
			
			//if the two characters are any kind of alf 
			if(( c1=='\u0625' ||c1=='\u0627'||c1=='\u0623'||c1=='\u0622') && ( c2=='\u0625' ||c2=='\u0627'||c2=='\u0623'||c2=='\u0622'))
				continue;
			//if the two characters are any kind of waw 
			else if((c1=='\u0624'||c1=='\u0648')&& (c2=='\u0624'||c2=='\u0648'))
			continue;
			//if the two characters are any kind of yeh
			else if((c1=='\u0626'||c1=='\u0649'||c1=='\u064A')&& (c2=='\u0626'||c2=='\u0649'||c2=='\u064A'))
			continue;
			//if the two characters are any kind of teh
			else if((c1=='\u0629'||c1=='\u062A')&& (c1=='\u0629'||c1=='\u062A'))
				continue;
			else if(Character.valueOf(c1).compareTo(Character.valueOf(c2))==0)
				continue;
			else
				return false;
			
			
			
		}
		
		
		return true;
	}
	private static String removeSpaces(String name)
	{
		StringTokenizer st=new StringTokenizer(name," ",false);
		String tempName="";
		while(st.hasMoreElements())
			tempName+=st.nextElement();
		
		return tempName;
	}
	public static void compare(Connection con,int jobId,int userId)
    {
	
        
        
    //--first step normalize
    //--second step if the one contain the other then true
    //--if they not contain each other compare character by character
        
    
    ResultSet rs=null;
    String infoFortName=null,infoFort1stName=null,infoFort2ndName=null,infoFort3rdName=null;
    String contractName=null,contract1stName=null,contract2ndName=null,contract3rdName=null;
    String simSerial=null;
    String temp=null;
    int firstNameRate=0,secondNameRate=0,thirdNameRate=0,totalRate=0;
    int counter=0;
    boolean check=false;
    
    
    try{
        
        //get the connection
      /*  if(System.getProperty("oracle.jserver.version")!=null){
            con=DriverManager.getConnection("jdbc:default:connection:");
            System.out.println("connection is ok");
        }else{
            System.out.println("connection is not ok");
        }*/
        
        //excute the query and get the result set
        
        rs=getInfoFortDataForCompare(con, String.valueOf(jobId));

        
        while(rs.next()){
        	contractName="";
        	infoFortName="";
        	infoFort1stName=rs.getString("FIRST_NAME");
        	System.out.println("infoFort1stName:" +infoFort1stName );
            infoFort2ndName=rs.getString("SECOND_NAME");
            System.out.println("infoFort2ndName:" +infoFort2ndName);
            infoFort3rdName=rs.getString("THIRD_NAME");
            System.out.println("infoFort3rdName:" +infoFort3rdName);
            
            
            infoFortName=infoFort1stName+infoFort2ndName+infoFort3rdName;
            System.out.println("infoFortName:" +infoFortName);
            
            
            contract1stName=rs.getString("CONTRACT_CUSTOMER_1ST_NAME");
            System.out.println("contract1stName:" +contract1stName);
            contract2ndName=rs.getString("CONTRACT_CUSTOMER_2ND_NAME");
            System.out.println("contract2ndName:" +contract2ndName);
            contract3rdName=rs.getString("CONTRACT_CUSTOMER_LST_NAME");
            System.out.println("contract3rdName:" +contract3rdName);
            
            
            contractName=contract1stName+contract2ndName+contract3rdName;
           
            System.out.println("contractName:" +contractName);
            
            simSerial=rs.getString("SIM_SERIAL");
            //
            if(infoFort1stName.charAt(0)=='\u0622'&& infoFort1stName.length()==1 ){
            	System.out.println("aaaaaaaaaaaa");
            	updateJobDetailsData(con,String.valueOf(userId), simSerial,jobId, "7");
            	continue;
            }
            
            //normalizing the two names
            
            for(int i=0;i<infoFortName.length();i++)
            temp+=normalize(infoFortName.charAt(i));
            
            
            infoFortName=temp;
            temp="";
            
            
            
            for(int i=0;i<contractName.length();i++)
                temp+=normalize(contractName.charAt(i));
            contractName=temp;
            
            temp="";
            
            
            System.out.println("infoFortName with no spaces:" +infoFortName.replaceAll(" ", ""));
            
            System.out.println("contractName with no spaces:" +contractName.replaceAll(" " , ""));
            
            //--check if one contain the other
           
            if(infoFortName.replaceAll(" " , "").contains(contractName.replaceAll(" ", ""))||contractName.replaceAll(" ", "").contains(infoFortName.replaceAll(" ", ""))){
                //update this sim as matched
            	System.out.println("update as matched because they contains each others ");
                updateJobDetailsData(con,String.valueOf(userId), simSerial,jobId, "2");
            }else {        //comparing character by character
                    
                    
                    	//compare the first names
                        for(int i=0;i<infoFort1stName.length()&& i<contract1stName.length();i++)
                        {
                            if(infoFort1stName.charAt(i)!=contract1stName.charAt(i))
                                counter++;
                            
                        }
                        if(infoFort1stName.length()>contract1stName.length())
                           	counter+=infoFort1stName.length()-contract1stName.length();
                        else
                        	counter+=contract1stName.length()-infoFort1stName.length();
                        
                        	
                        firstNameRate=counter*6;
                        counter=0;
                        
                        //compare the second names
                        for(int i=0;i<infoFort2ndName.length()&& i<contract2ndName.length();i++)
                        {
                            if(infoFort2ndName.charAt(i)!=contract2ndName.charAt(i))
                                counter++;
                        }
                        
                        /*if(infoFort2ndName.length()>contract2ndName.length())
                           	counter+=infoFort2ndName.length()-contract2ndName.length();
                        else
                        	counter+=contract2ndName.length()-infoFort2ndName.length();*/
                        
                        secondNameRate=counter*4;
    					counter=0;
    					
    					//compare the third names
    					for(int i=0;i<infoFort3rdName.length() && i<contract3rdName.length();i++)
    					{
    						if(infoFort3rdName.charAt(i)!=contract3rdName.charAt(i))
    							counter++;
    					}
    					
    					/*if(infoFort3rdName.length()>contract3rdName.length())
                           	counter+=infoFort3rdName.length()-contract3rdName.length();
                        else
                        	counter+=contract3rdName.length()-infoFort3rdName.length();*/
                        
    					thirdNameRate=counter*2;
    					counter=0;
    					
    					
    					totalRate=firstNameRate+secondNameRate+thirdNameRate;
    					if(totalRate<24){
    						//update this sim as matched
    						System.out.println("update as matched because the rate is less than 24 ");
    						updateJobDetailsData(con,String.valueOf(userId), simSerial,jobId, "2");
    						
    					}
    					else{
    							//update as un matched
    						System.out.println("update as un matched because the rate is more than 24 ");
    						updateJobDetailsData(con,String.valueOf(userId), simSerial,jobId, "7");
    						
    					}
                    
                    
					
					
					
				}
            System.out.println("sim serial: "+simSerial);            
            System.out.println("1st rate: "+firstNameRate);
            System.out.println("2nd rate: "+secondNameRate);
            System.out.println("3rd rate: "+thirdNameRate);
            System.out.println("total rate: "+totalRate);
            System.out.println("////////////////////////////////////////////////////////////////////////");
            
			totalRate=0;
			firstNameRate=0;
			secondNameRate=0;
			thirdNameRate=0;
			
			
			
		}	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	
	}
	
	public static ResultSet getInfoFortDataForCompare(Connection con,String jobID){
		ResultSet rs=null;
		try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from " +
	      		"(SELECT IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_1ST_NAME," +
	      		"IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_2ND_NAME," +
	      		"IF_INFO_FORT_DATA.CONTRACT_CUSTOMER_LST_NAME" +
	      		",IF_INFO_FORT_DATA.FIRST_NAME" +
	      		",IF_INFO_FORT_DATA.FOURTH_NAME" +
	      		",IF_INFO_FORT_DATA.SECOND_NAME" +
	      		",sim_serial" +
	      		",IF_INFO_FORT_DATA.THIRD_NAME " +
	      		"from IF_INFO_FORT_DATA" +
	      		" where DATA_RECORD_TYPE_ID!=3 and DATA_RECORD_TYPE_ID!=2 and JOB_ID= " +
	      		jobID +	      		
	      		") " +
	      		" order by sim_serial";
	      
	       
	      //Utility.logger.debug(strSql);
	      System.out.println("The query is " + strSql);
	      rs = stat.executeQuery(strSql);
	      
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return rs;
}

public static void updateJobDetailsData(Connection con,String userId,String simSerial,int jobId,String typeId){
		try
	    {
	    	
	      Statement stat = con.createStatement();
	      
	      	  
	    	  
	    	 String strSql = "update IF_INFO_FORT_DATA set DATA_RECORD_TYPE_ID= "+typeId+" where job_id= " +
	    	 jobId+
	    	 		" and SIM_SERIAL= "+simSerial;
		      System.out.println("The query is " + strSql);
		      stat.executeUpdate(strSql);
		      
		      
		      
		     /* strSql ="select DATA_RECORD_TYPE_NAME from IF_DATA_RECORD_TYPE where DATA_RECORD_TYPE_ID = "+typeId;
		      System.out.println("The query is " + strSql);
		      ResultSet rs=stat.executeQuery(strSql);
		      String typeName=null;
		      while(rs.next()){
		    	  typeName=rs.getString(1);
		    	  
		      }
		      strSql ="insert into IF_DATA_ACTIONS_HISTORY(DATA_ACTIONS_HISTORY_ID,SIM_SERIAL,USER_ID,TIME_STAMP,ACTION_UPDATE_TO) values(IF_ACTION_HISTORY_ID.nextval," +
		  		"'"+ simSerial + "'"+
		  		" ," +
		  		userId +
		  		",sysdate," +
		  		"'"+typeName +"'"+
		  		")";
		      System.out.println("The query is " + strSql);
		      stat.executeUpdate(strSql);*/
	    	  
	      
	      stat.close();
	      
	      
	      
	      
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
}
public static void updateJobDetails(Connection con,int jobId){
	try
    {
    	
      Statement stat = con.createStatement();
      
      	  
    	  
    	 String strSql = "update IF_JOB_DETAILS set MATCHED_AUTOMATICALLY_NO=( select count(*) from if_INFO_FORT_DATA where DATA_RECORD_TYPE_ID = '2' and job_id = " +
    	 		jobId +
    	 		") where job_id = " +
    	 		jobId;
	      System.out.println("The query is " + strSql);
	      stat.executeUpdate(strSql);
	      
	      
	      
	     /* strSql ="select DATA_RECORD_TYPE_NAME from IF_DATA_RECORD_TYPE where DATA_RECORD_TYPE_ID = "+typeId;
	      System.out.println("The query is " + strSql);
	      ResultSet rs=stat.executeQuery(strSql);
	      String typeName=null;
	      while(rs.next()){
	    	  typeName=rs.getString(1);
	    	  
	      }
	      strSql ="insert into IF_DATA_ACTIONS_HISTORY(DATA_ACTIONS_HISTORY_ID,SIM_SERIAL,USER_ID,TIME_STAMP,ACTION_UPDATE_TO) values(IF_ACTION_HISTORY_ID.nextval," +
	  		"'"+ simSerial + "'"+
	  		" ," +
	  		userId +
	  		",sysdate," +
	  		"'"+typeName +"'"+
	  		")";
	      System.out.println("The query is " + strSql);
	      stat.executeUpdate(strSql);*/
    	  
      
      stat.close();
      
      
      
      
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
}
}
