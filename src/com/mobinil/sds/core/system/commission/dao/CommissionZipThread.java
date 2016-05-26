/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.commission.dao;


import com.mobinil.sds.core.utilities.DMZIP.ReadZip;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Ahmed Adel
 */
public class CommissionZipThread extends Thread{

  
  private String fileId;
  private String fileName;

public CommissionZipThread(String fileId,String fileName)
{
 
    this.fileId=fileId;
    this.fileName=fileName;
}

    @Override
public void run()
    {

        try {
			Connection con = Utility.getConnection();
		    System.out.println("ZipThread starting.");

		    long stime= System.currentTimeMillis();
		    ReadZipFile rz = new ReadZipFile();

		 rz.readDataFromZipFile(fileName, fileId,con);
        
	    	long etime = System.currentTimeMillis();
	    	System.out.println("data loading took"+ ((etime-stime)/1000) + " seconds");
                
           // this.stop();  //not needed at all  it will make u not close the connection even
		CommissionDAO.updatefileStatus(fileId,con);
                con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


    }


}
