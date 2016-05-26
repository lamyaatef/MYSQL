package com.mobinil.sds.core.system.ccm.addAndWin.DAO;


import java.sql.*;
import java.util.Vector;


import com.mobinil.sds.core.system.ccm.addAndWin.model.AddAndWinShopAssignmentModel;
import com.mobinil.sds.core.system.ccm.addAndWin.model.shopModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityMandatoryDataModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityTypesModel;
import com.mobinil.sds.core.utilities.*;


public class addAndWinDao {
	
	public static Vector getShops(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			//String strSql = "select dcm_id,dcm_name from gen_dcm where dcm_code like '%Van%' or dcm_code like '%DCC%'";
			
			// Wassim requirement 
			String strSql = "select dcm_id,dcm_name from gen_dcm where dcm_payment_Level_id in (10,15,18)";
			System.out.println("GET SHOPS QUERY ISSSSSSSS"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new shopModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	public static Vector getAddAndWin(Connection con,int row_num) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			//String strSql = "select dcm_id,dcm_code,dcm_name,addandwin_id,shop_id,(select dcm_name from gen_dcm where shop_id = dcm_id)as shop_name "+
                           // "from gen_dcm,ccm_addandwin_shop where dcm_id = addandwin_id(+)and dcm_code like '%AW%' ";
			
			String strSql=   " select *from (select rownum row_num,dcm_id,dcm_code,dcm_name,addandwin_id,shop_id,(select dcm_name from gen_dcm where shop_id = dcm_id)as shop_name"+
					" from gen_dcm,ccm_addandwin_shop where dcm_id = addandwin_id(+) and dcm_code like '%AW%' ) ";
			
			strSql +="  where row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
		     
			System.out.println("the query isssssssss"+strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new AddAndWinShopAssignmentModel (res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	
	public static void insertAddandWin_shop (Connection con,String AddAndWinId,String shopId)
	{
	
		try {
			Statement stat = con.createStatement();
	
			
			String strSql = "INSERT INTO CCM_ADDANDWIN_SHOP(ADDANDWIN_ID,SHOP_ID) values("+AddAndWinId+","+shopId+")";
			
			System.out.println("the insert to add  and win query is query isssssssss"+strSql);
			ResultSet res = stat.executeQuery(strSql);
		
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static void updateAddandWin_shop (Connection con,String AddAndWinId,String shopId)
	{
	
		try {
			Statement stat = con.createStatement();
	
			
			
			
			
			String strSql = "update CCM_ADDANDWIN_SHOP set SHOP_ID = '"+shopId+"' where ADDANDWIN_ID= '"+AddAndWinId+"' ";  
			System.out.println("the update  to add  and win query is query isssssssss"+strSql);
			ResultSet res = stat.executeQuery(strSql);
		
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	  public static Vector getAddAndWinByFilter(Connection con,String Name,String Code,int row_num)
	  {
	    Vector vec = new Vector();
	    boolean andFlag = false;
	    try
	    {
	    	
			Statement stat = con.createStatement();
			
			String strSql=   " select *from (select rownum row_num,dcm_id,dcm_code,dcm_name,addandwin_id,shop_id,(select dcm_name from gen_dcm where shop_id = dcm_id)as shop_name"+
					" from gen_dcm,ccm_addandwin_shop where dcm_id = addandwin_id(+) and dcm_code like '%AW%' ) ";
		 
	     
	      //Utility.logger.debug(strSql);

	      if(Name!=null && Name.compareTo("")!=0)
	       {
	          if(!andFlag){strSql += "where ";
	          Utility.logger.debug(strSql);}
	          else{strSql += " and ";
	          Utility.logger.debug(strSql);}
	          strSql += "dcm_name  like '%"+Name+"%'";
	          
	      		strSql +=" and row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	          System.out.println("The get by filter query  Query isssssss " + strSql);
	          
	       
	          andFlag = true;
	       }

	      if(Code!=null && Code.compareTo("")!=0)
	      {
	        if(!andFlag)
	        {
	          strSql += " where ";
	        }
	        else
	        {
	          strSql += " and ";
	        }
	        strSql += "dcm_code like '%"+Code+"%'";
	        
	    	strSql +="  and row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	        System.out.println("The get by filter query  Query isssssss " + strSql);
	        andFlag = true;
	      }
	      else if(andFlag==false)
	      {
	    	 	strSql +="  where row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	      }
    
	      System.out.println("The get by filter query  Query isssssss " + strSql);

	    //  strSql += " order by INVOICE_NUMBER,DCM_ID,PAYMENT_DATE";
	      Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new AddAndWinShopAssignmentModel (res));
			}
	      stat.close();
	    }
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return vec;
	  }
	
		
	  public static Vector getAddAndWinByFilterSearch(Connection con,String Name,String Code,int row_num)
	  {
	    Vector vec = new Vector();
	    boolean andFlag = false;
	    try
	    {
	    //	int row_num;
	    //row_num=0;
			Statement stat = con.createStatement();
			
			String strSql=   " select *from (select rownum row_num,dcm_id,dcm_code,dcm_name,addandwin_id,shop_id,(select dcm_name from gen_dcm where shop_id = dcm_id)as shop_name"+
					" from gen_dcm,ccm_addandwin_shop where dcm_id = addandwin_id(+) and dcm_code like '%AW%' )  ";
		 
	     
	      //Utility.logger.debug(strSql);

	      if(Name!=null && Name.compareTo("")!=0)
	       {
	          if(!andFlag){strSql += "where ";
	          Utility.logger.debug(strSql);}
	          else{strSql += " and ";
	          Utility.logger.debug(strSql);}
	          strSql += "dcm_name  like '"+Name+"%'";
	          strSql += "order by rownum ";
	          
	   //   strSql +=" and row_num > "+ row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	          System.out.println("The get by filter query  Query isssssss " + strSql);
	     //     row_num++;
	          
	       
	          andFlag = true;
	       }

	      if(Code!=null && Code.compareTo("")!=0)
	      {
	        if(!andFlag)
	        {
	          strSql += " where ";
	        }
	        else
	        {
	          strSql += " and ";
	        }
	        strSql += "dcm_code like '"+Code+"%'";
	        strSql += "order by rownum ";
	        
	// 	strSql +="  and row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	        System.out.println("The get search query  Query isssssss " + strSql);
	        andFlag = true;
	      }
	      else if(andFlag==false)
	      {
	  	 // strSql +="  where row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
//	  	 	row_num++;
	    	  strSql += "order by rownum ";
	      }
    
	      System.out.println("The search  query  Query isssssss " + strSql);

	    //  strSql += " order by INVOICE_NUMBER,DCM_ID,PAYMENT_DATE";
	      Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	     // strSql +="  and row_num > "+row_num+"*49 and row_num < = ("+row_num+"+1)*49" ;
	   
	      int min_row = row_num*50;
	      int max_row = min_row + 50;
	      int counter = 0;
	      System.out.println("ro3  number issssssssss"+row_num);
	       
			while (res.next()) {
				counter++;
				if(counter <= max_row &&   counter>= min_row )
				{
					System.out.println("EZZ  "+counter);
					vec.add(new AddAndWinShopAssignmentModel (res));
					System.out.println("min row  iissssss:"+min_row);
					System.out.println("max_row isssssss"+max_row);
						
				}
				else if(counter > max_row){
					break;
				}else continue;
	      }
			stat.close();
	
	    }
			
	    catch(Exception e)
	    {
	    e.printStackTrace();
	    }
	    return vec;
	  }
 }
