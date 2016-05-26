package com.mobinil.sds.core.utilities.DMZIP;

import java.sql.Connection;
import java.sql.SQLException;
import com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao;
import com.mobinil.sds.core.utilities.*;


public class ZipThread extends Thread  {
	
	Connection con=null;
	
	private String year;
	private String month;
	private String Status;
	private String file_name;

	private String user_id;

	public ZipThread(){
    }
	
	public ZipThread(String year,String month,String Status,String file_name,String user_id){
		
		this.year=year;
		this.month=month;
		this.Status=Status;
		this.file_name=file_name;
		this.user_id=user_id;
		
		
		
	}
	
	  public void run() { 
		  
		  try {
			con = Utility.getConnection();
		    System.out.println("ZipThread starting.");
		    Long file_id = DataMigrationDao.insertNewFile(con, year, month, "Processing",user_id);
	
		    long stime= System.currentTimeMillis();
		    ReadZip rz = new ReadZip();
	
		 int nooflines=   rz.getDMZIPData(con, file_name, file_id);
         
	    	System.out.println("sssssssssadasds:   "+nooflines);
	    	long etime = System.currentTimeMillis();
	    	System.out.println("data loading took"+ ((etime-stime)/1000) + " seconds");
            DataMigrationDao.updateStatus(con, file_id.toString(),"Active");
            
            DataMigrationDao.insertStastics(file_id.intValue(),nooflines);
            
           // this.stop();  //not needed at all  it will make u not close the connection even     
			con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
 }
	  
  }


