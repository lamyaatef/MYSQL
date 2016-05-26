package com.mobinil.sds.core.utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;



import com.mobinil.sds.core.system.sa.importdata.*;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.system.aacm.dao.AuthAgentDAO;
import com.mobinil.sds.core.system.aacm.model.*;

public class uploadMigrationDataThread extends Thread{
	
Connection con=null;
	
	private String filepath;
	private String user_id;
	private List items;
	private String operaion;
	private String tableId;
	private String month;
	private String year;
	
	public uploadMigrationDataThread()
	{
		
	}
	
public uploadMigrationDataThread(String month,String year,String operation,String tableId,List items,String user_id,String filepath){
		
		this.user_id=user_id;
		this.filepath=filepath;
		this.items = items;
		this.operaion = operation;
		this.tableId = tableId;
		this.month = month;
		this.year = year;
		System.out.println("The file path issssssssssss " + filepath);
	
	}


public void run()
{
	
	
	int count = 0;
	int actualCount = 0;
	int batchCount = 0;
	StringBuilder contents = new StringBuilder();
	File file = new File(filepath);
	try {

		// use buffering, reading one line at a time
		// FileReader always assumes default encoding is OK!
		 java.sql.Connection con=null;
			con = Utility.getConnection();
Statement   stat = con.createStatement();

		BufferedReader input = new BufferedReader(new FileReader(file));
		try {

			String line = null; // not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line
			 * MINUS the newline. it returns null only for the END of the
			 * stream. it returns an empty String if two newlines appear in
			 * a row.
			 */
			

			while ((line = input.readLine()) != null) {
				
				if (count > 0) {
					//System.out.println("The line isssssss " + line);
					String[] fields = line.split(",");
					
					String[] fields_apr = new String[fields.length];
					for (int i = 0; i < fields_apr.length; i++) {
						fields_apr[i] = "";
					}
					//System.out.println("The array length isssss "
							//+ fields.length);
					if (fields != null && fields.length != 0&&fields.length!=1) {
						
						for (int i = 0; i < fields.length; i++) {
							if (fields[i] != null && !fields[i].equals(""))
								{fields_apr[i] = fields[i];
							}
							else
								fields_apr[i] = "";
							// fields[i]= "";
							// System.out.println("The array isssss " +
							// fields[i]);

						}
						//System.out.println("Medhat");
						//actualCount += UploadRevenueFile.insertRevenueData(
							//	fields_apr[1], fields_apr[2],
								//fields_apr[3], fields_apr[4],fields_apr[5],fields_apr[6],fields_apr[7]);
						AuthAgentDAO.insertAuthAgentMigration(con,stat,fields_apr[0], fields_apr[1]);
						batchCount ++;
						//System.out.println("the batch count issssssssss " + batchCount);
						if(batchCount==250)
						{	
							System.out.println( "the batch nis executed");
							stat.executeBatch();
							
							stat.clearBatch();
							batchCount=0;
						}
					}

					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
				count++;
			}
			stat.executeBatch();
       		stat.close();
			Long fileId = AuthAgentDAO.updateAuthAgentFileID();
			 System.out.println("The file Id issssssssssssssssss " +fileId);
			 if(fileId !=null)
			 {
				 AuthAgentDAO.insertAuthAgentMigrationFile(fileId,month, year,user_id);
				 AuthAgentDAO.updateAuthAgentMigrationSimMigrationDate(fileId);
				 AuthAgentDAO.updateMigrationDataChecked();
				 AuthAgentDAO.updateAuthAgentMigrationBSCSCode(fileId);
			}	
		} finally {
			input.close();
		}
	} catch (Exception e) {
		//logger.fatal(e);
		e.printStackTrace();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*Iterator itr = items.iterator();

	


	HashMap dataHashMap = null;

	//String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


	//ExcelImport importEngine = new ExcelImport(userID);
	Vector errorVector = new Vector();
	Vector statusVector = new Vector();
	

	DataImportEngine importEngine = new DataImportEngine();

	DataImportReport importReport =importEngine.ImportFileWithRowNumber(filepath , operaion, tableId); 

	Vector reportImport = importReport.getReport();

	Vector report = new Vector();

	String operationName = importReport.getOperation();
	int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

	//importEngine = null;

	

	java.util.Date currentDate = new java.util.Date();

	int currentYear = currentDate.getYear()+1900;
	int currentMonth = currentDate.getMonth()+1;
	int currentDay = currentDate.getDate();

	String strCurrentDate = currentYear+"/"+currentMonth+"/"+currentDay;
	ErrorInfo contractError = null;
	Vector authAgentData = AuthAgentDAO.getTmpAuthAgent();
	
	
	  for(int i = 0;i<authAgentData.size();i++)
	  {
	    AuthAgentImportModel authAgentImportModel = (AuthAgentImportModel)authAgentData.get(i);
	    String tmRowNum = authAgentImportModel.getRowNum();
	    String tmBscsCode = authAgentImportModel.getBscsCode();
	    String tmDialNumber = authAgentImportModel.getDialNumber();
	    //
	   
	    //AuthAgentDAO.insertAuthAgentMigration(tmBscsCode,tmDialNumber);
	    
	  } 
	 //Long fileId = AuthAgentDAO.updateAuthAgentFileID();
	 //System.out.println("The file Id issssssssssssssssss " +fileId);
	 //if(fileId !=null)
	 //{
		 //AuthAgentDAO.insertAuthAgentMigrationFile(fileId,month, year,user_id);
		// AuthAgentDAO.updateAuthAgentMigrationSimMigrationDate();
		 //AuthAgentDAO.updateMigrationDataChecked();
		 //AuthAgentDAO.updateAuthAgentMigrationBSCSCode(fileId);
	//}
	//AuthAgentDAO.deleteTmpAuthAgent();*/
	
	System.out.println("The insertion completed");
}


}
