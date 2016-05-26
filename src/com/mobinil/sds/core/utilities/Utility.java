package com.mobinil.sds.core.utilities;

/**
 * Utility Class holds all the global operations:
 * 1- Get the home interface for a bean
 * 2- Get a pooled connection object 
 * 3- Close a connection object
 * For example:
 * <pre>
 *      com.mobinil.sds.core.utilities.Utility.getEJBHome(argJndi,"BeanLookUpName");
 * </pre>
 *
 * @version	1.01 Feb 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.sql.*;
import java.text.SimpleDateFormat;


import java.io.*;
import java.util.*;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*; 
import org.apache.log4j.Logger;

//import com.mobinil.sds.core.utilities.jaspertest.*;
import com.mobinil.sds.core.system.sop.requests.model.RequestDetailModel;
import com.mobinil.sds.core.utilities.jaspertest.ReportColumn;
//import java.util.logging.Logger;

public class Utility 
{
private static int conCount = 0;
private static boolean forjboss=true;
private static boolean forProduction=false;


public static Logger logger = Logger.getLogger(Utility.class);

  private static String strCon = "";
  private static String userName = "";
  private static String password = "";
  

  private static String strLcsCon = "";
  private static String lcsUserName = "";
  private static String lcspassword = "";
  
 
  
  

/**
 * Establish the connection and return the connection object
 *
 * @param	
 * @return Connection object  
 * @throws  Exception if the datasource lookup failed
 * @see    
*/	
static {
	IniEditor dbParams = new IniEditor();

	  try
	  {  
		  String x = "";
		  x = new File("sds//DB_Config.ini").getAbsolutePath();
		  System.out.println("file loading from:"+x);
		 
	      dbParams.load(x);
	      
	         
	      
	      	  strCon = dbParams.get("dbSDS-prod", "Connection"); 
		      userName = dbParams.get("dbSDS-prod", "userName");
		      password = dbParams.get("dbSDS-prod", "password");
		      
		     
		      strLcsCon = dbParams.get("dbSDS-prod", "LcsConnection");
		      lcsUserName = dbParams.get("dbSDS-prod", "lcsUserName");
		      lcspassword = dbParams.get("dbSDS-prod", "lcsPassword");
		      
	      
		      
		      System.out.println("init of strCon:"+strCon);
		      System.out.println("init of strLcsCon:"+strLcsCon);

	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		System.out.println("Here in exception "+ e.getMessage());
	  }	
}
    
  public static void initData()
  {
		IniEditor dbParams = new IniEditor();

		  try
		  {  
			  String x = "";
			  x = new File("sds//DB_Config.ini").getAbsolutePath();
			  System.out.println("file loading from:"+x);
			 
		      dbParams.load(x);
		      
		         
		      
                              strCon = dbParams.get("dbSDS-prod", "Connection");
			      userName = dbParams.get("dbSDS-prod", "userName");
			      password = dbParams.get("dbSDS-prod", "password");
			      
			     
			      strLcsCon = dbParams.get("dbSDS-prod", "LcsConnection");
			      lcsUserName = dbParams.get("dbSDS-prod", "lcsUserName");
			      lcspassword = dbParams.get("dbSDS-prod", "lcsPassword");
			      
		      
			      
			      System.out.println("init of strCon:"+strCon);
			      System.out.println("init of strLcsCon:"+strLcsCon);

		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			System.out.println("Here in exception "+ e.getMessage());
		  }
  }

  
  private static synchronized void incrementCounter() 
  {
      conCount++;
      System.out.println("connection inc ="+ conCount);
  }

  public static synchronized void decrementCounter() 
  {
      conCount--;
      System.out.println("connection dec ="+ conCount);  
  }


  public static  Connection getConnection() throws SQLException
  {
    Connection objConnection  = null;
  
    
    try
    {    
        
        System.out.println("*******************");
     System.out.println("strCon="+strCon);
     
     System.out.println("UserName="+userName);
     System.out.println("Password="+password);
    	Class.forName("oracle.jdbc.driver.OracleDriver");
    	String tmpCon = strCon+"";
    	String uName = userName+"";
    	String uPassword  = password+"";
        objConnection=DriverManager.getConnection(tmpCon,uName,uPassword);
        if (tmpCon==null)
        {
        	System.out.println("***********************");
        	System.out.println("strange problem");
        	System.out.println("strCon= "+strCon);
        	System.out.println("tmpCon="+tmpCon);
        }
        
        incrementCounter();
    }
    catch(Exception objExp)
    {
     System.out.println("*******************");
     System.out.println("strCon="+strCon);
     
     System.out.println("UserName="+userName);
     System.out.println("Password="+password);
     
     objExp.printStackTrace();
//     Utility.logger.debug("Error in getConnection:  "+ objExp.toString());
    }
    
    
    return objConnection;
  }//end of getConnection()

  public static  Connection getLCSConnection() throws SQLException
  {
    Connection objConnection                                 = null;

    try
    {  
         Class.forName("oracle.jdbc.OracleDriver");             
         objConnection=DriverManager.getConnection(strLcsCon,lcsUserName,lcspassword);                  
                  
    }
    catch(Exception objExp)
    {
/*
 * Throw new Exception("Error in getting Database Connection <br>" + objExp.toString()); 
 */
      //Utility.logger.debug("Error in getConnection:  "+ objExp.toString());
    	
    	objExp.printStackTrace();
    }
    return objConnection;
  }//end of getConnection()

/**
 * Close the connection 
 *
 * @param	
 * @return 
 * @throws  Exception if closing the connection failed
 * @see    
*/	
  
  public static  void closeConnection(Connection argConnection) 
  {
    try
    {
         argConnection.close();
         //Utility.logger.debug( " Connection is closed");
         decrementCounter();
         //Utility.logger.debug( "Connection: " + argConnection + " has been closed,count : "+conCount);       
    }
    catch(Exception objExp)
    {
    //    Utility.logger.debug("Error in closeConnection:  "+ objExp.toString());
        objExp.printStackTrace();
    }
    
  }//end of closeConnection()

/**
 * Get next number from the given database sequence.
 *
 * @param	Connection argConnection, String argSequenceName
 * @return Long
 * @throws SQLException
 * @see    
*/	
  
  public static  Long getSequenceNextVal(Connection con, String argSequenceName)  
  {
    Long lNextVal = 0L;
    Statement newStatement =null;
    try
    {
      String strGetNextValQuery = "select "+argSequenceName+".nextval from dual";
      newStatement = con.createStatement();
      ResultSet newResultSet = newStatement.executeQuery(strGetNextValQuery);
      newResultSet.next();
      lNextVal = newResultSet.getLong(1);
      newResultSet.close();
        
    }
    catch(Exception objExp)
    {
       objExp.printStackTrace();     
    }        
    finally
    {
        DBUtil.close(newStatement);
    }
    return lNextVal;
  }//end of getSequenceNextVal

  public static void sendPasswordByMail (String serverName, String serverPort, 
                                                         String appName, String recipientMail, 
                                                         String  strUserPersonFullName,String password) throws IOException, MessagingException
  {
      // The SMTP host
       String smtpHost = "mob-conn-01.mobinil.corp";
   //   String smtpHost = "webmail.sandcti.com";
      // The sender mail. 
      // N.B. : The sender mail can not have spaces. 
      String message_from = "SDS_Administrator";
      // The message recipient.
      String message_recip = recipientMail;
      // The message subject.
      String message_subject = "Your SDS login information";
      // The text/plain message body
      String message_body =
        "Dear  "+strUserPersonFullName+
        "\n     Welcome to SDS."+
        "\n     Use your email account and this password\""+password+"\" to login to SDS."+
        "\n     Do not forget to change your password the first time you login." +
        "\n     For any question contact the system administrator through this mail SDS_Administrator@mobinil.com";
      // The text/html data.
      String html_data = 
        "<HTML><HEAD><TITLE>Your SDS login Information</TITLE></HEAD>" +
        "<BODY>Dear  <b>"+strUserPersonFullName+"<b>"+
        "<br>Welcome to SDS."+
        "<br>Use your email account and this password <b>\""+password+"\"</b> to login to SDS."+
        "<br>Do not forget to change your password the first time you login." +
        "<br>"+
        "<center><A href=\"http://"+serverName+":"+serverPort+appName+"/gn/ua/User_Login.jsp\">"+
        "<IMG height=146 alt=\"SDS\" src=\"http://"+serverName+":"+serverPort+appName+"/resources/img/SDS.gif\" width=141 border=0></A>"+
        "http://"+serverName+":"+serverPort+appName+"/resources/img/SDS.gif" +
        "</center></BODY></HTML>";

      // The JavaMail session object
      Session session;
      // The JavaMail message object
      Message mesg;
      // Get system properties
      Properties props = System.getProperties();
      // Setup mail server
      props.put("mail.smtp.host", smtpHost);
      // Create the Session object
      session = Session.getDefaultInstance(props, null);
      session.setDebug(true);		// Verbose!
      try 
      {
        // create a message
        mesg = new MimeMessage(session);
        // From Address - this should come from a Properties...
        mesg.setFrom(new InternetAddress(message_from));
        // TO Address 
        InternetAddress toAddress = new InternetAddress(message_recip);
        mesg.addRecipient(Message.RecipientType.TO, toAddress);
        // The Subject
        mesg.setSubject(message_subject);
        // Now the message body.
        /*Multipart mp = new MimeMultipart();
        BodyPart textPart = new MimeBodyPart();
        textPart.setText(message_body);	// sets type to "text/plain"
        BodyPart pixPart = new MimeBodyPart();
        pixPart.setContent(html_data, "text/html");
        // Collect the Parts into the MultiPart
        mp.addBodyPart(textPart);
        mp.addBodyPart(pixPart);
        // Put the MultiPart into the Message
        mesg.setContent(mp);*/
        mesg.setContent(html_data, "text/html");
        // Finally, send the message!
        Transport.send(mesg);
      } 
      catch (MessagingException ex) 
      {
        System.err.println(ex);
        ex.printStackTrace(System.err);
      }
  }

  public static void sendSOPRequestNotificationByMail (String serverName, String serverPort, 
                                                         String appName, String recipientMail, 
                                                         String  strUserPersonFullName,String dcmName,String dcmCode,String requestCode,Vector requestDetails) throws IOException, MessagingException
  {
      // The SMTP host
      //String smtpHost = "mob-conn-01.mobinil.corp";
      String smtpHost = "10.1.132.114";
      // The sender mail. 
      // N.B. : The sender mail can not have spaces. 
      String message_from = "SDS_Administrator";
      // The message recipient.
      String message_recip = recipientMail;
      // The message subject.
      String message_subject = "Request Notification";
      // The text/plain message body
      String message_body =
        "Dear  "+strUserPersonFullName+
        "\n     This a notification message." +
        "\n     A request with request code : \""+requestCode+"\" has been made to distributer name :\""+dcmName+"\" code : \""+dcmCode+"\".";
      // The text/html data.
      String html_data = 
        "<HTML><HEAD><TITLE>Request Notification</TITLE></HEAD>" +
        "<BODY>Dear  <b>"+strUserPersonFullName+"<b>"+
        "<br>This a notification message."+
        "<br>A request with request code : <b>\""+requestCode+"\"</b> has been made to distributer name : <b>\""+dcmName+"\"</b> code : <b>\""+dcmCode+"\"</b> ."+

        "<br><br><TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1> "+
           "<tr class=TableHeader>"+
           " <td>Product Name English</td>"+
           " <td>Product Name Arabic</td>"+
           " <td>Amount</td>"+
          "</tr>";
    
    float totalPayment = 0;
    for(int i=0;i<requestDetails.size();i++)
    {
      RequestDetailModel requestDetailModel = (RequestDetailModel)requestDetails.get(i);
      String requestDetailId = requestDetailModel.getRequestDetailId();
      String requestId = requestDetailModel.getRequestId();
      String schemaProductId = requestDetailModel.getSchemaProductId();
      String productAmount = requestDetailModel.getProductAmount();
      String productNameEnglish = requestDetailModel.getProductNameEnglish();
      String productNameArabic = requestDetailModel.getProductNameArabic();
      String productPrice = requestDetailModel.getProductPrice();
      float intProductPrice = Float.parseFloat(productPrice);
      float intProductAmount = Float.parseFloat(productAmount);
      float total = intProductPrice * intProductAmount;
      totalPayment = total + totalPayment;
      if(productAmount.compareTo("0")!=0)
      {

    html_data += "<tr> "+
          "<td width=35%>"+productNameEnglish+"</td> "+
          "<td width=35%>"+productNameArabic+"</td>"+
          "<td width=30%>"+productAmount+"</td>"+
        "</tr>";
    
      }
    }
    
    html_data += "</table>" +
                        "</BODY></HTML>";

      // The JavaMail session object
      Session session;
      // The JavaMail message object
      Message mesg;
      // Get system properties
      Properties props = System.getProperties();
      // Setup mail server
      props.put("mail.smtp.host", smtpHost);
      // Create the Session object
      session = Session.getDefaultInstance(props, null);
      session.setDebug(true);		// Verbose!
      try 
      {
        // create a message
        mesg = new MimeMessage(session);
        // From Address - this should come from a Properties...
        mesg.setFrom(new InternetAddress(message_from));
        // TO Address 
        InternetAddress toAddress = new InternetAddress(message_recip);
        mesg.addRecipient(Message.RecipientType.TO, toAddress);
        // The Subject
        mesg.setSubject(message_subject);
        // Now the message body.
        /*Multipart mp = new MimeMultipart();
        BodyPart textPart = new MimeBodyPart();
        textPart.setText(message_body);	// sets type to "text/plain"
        BodyPart pixPart = new MimeBodyPart();
        pixPart.setContent(html_data, "text/html");
        // Collect the Parts into the MultiPart
        mp.addBodyPart(textPart);
        mp.addBodyPart(pixPart);
        // Put the MultiPart into the Message
        mesg.setContent(mp);*/
        mesg.setContent(html_data, "text/html");
        // Finally, send the message!
        Transport.send(mesg);
      } 
      catch (MessagingException ex) 
      {
        System.err.println(ex);
        ex.printStackTrace(System.err);
      }
  }

  public static HashMap getMap(Connection con,String strQuery)
  {
    
    return DBUtil.getMap(con,strQuery);
  }

  public static String runLinuxCommand(String strCommand)
  {
    String strResult = "";
    try
    {
     // logger.debug("Linux Command : "+ strCommand);
      Process process = Runtime.getRuntime().exec(strCommand);
      OutputStream output = process.getOutputStream();
      if(output != null)
      {
        strResult = output.toString();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      //strResult = e.toString();
    }
    return strResult;
  }
public static String replaceAll(String aInput, String aOldPattern, String aNewPattern) {
    
         if ( aOldPattern.equals("") ) {
            throw new IllegalArgumentException("Old pattern must have content.");
         }

         final StringBuffer result = new StringBuffer();
         //startIdx and idxOld delimit various chunks of aInput; these
         //chunks always end where aOldPattern begins
         int startIdx = 0;
         int idxOld = 0;
         while ((idxOld = aInput.indexOf(aOldPattern, startIdx)) >= 0) {
           //grab a part of aInput which does not include aOldPattern
           result.append( aInput.substring(startIdx, idxOld) );
           //add aNewPattern to take place of aOldPattern
           result.append( aNewPattern );

           //reset the startIdx to just after the current match, to see
           //if there are any further matches
           startIdx = idxOld + aOldPattern.length();
         }
         //the final chunk will go to the end of aInput
         result.append( aInput.substring(startIdx) );
         return result.toString();
    }

    public static Vector getResultSetFields(ResultSet result_set)
 {
     Vector all_report_columns = new Vector();
        try {
            ResultSetMetaData meta_data = result_set.getMetaData();

            for(int i=0;i<meta_data.getColumnCount();i++)
            {
                ReportColumn column = new ReportColumn(meta_data.getColumnLabel(i+1),meta_data.getColumnName(i+1), meta_data.getColumnClassName(i+1));
                all_report_columns.add(column);
            }
        } catch (SQLException ex) {
          //  Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
     return all_report_columns;
 }
public static String getFormattedDate(Date date)
{
	if(date !=null)
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	return "";
}
 public static ResultSet executeSelect(Connection con , String query)
 {
        try {
           // Connection con = getConnection();
            Statement stm = con.createStatement();
            System.out.println("query ="+ query);
            return stm.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
 }
 
 
 // convert file stream to byte array
 public static byte[] getBytesFromFile(File file) throws IOException {
     InputStream is = new FileInputStream(file);
 
     // Get the size of the file
     long length = file.length();
 
     // You cannot create an array using a long type.
     // It needs to be an int type.
     // Before converting to an int type, check
     // to ensure that file is not larger than Integer.MAX_VALUE.
     if (length > Integer.MAX_VALUE) {
         // File is too large
     }
 
     // Create the byte array to hold the data
     byte[] bytes = new byte[(int)length];
 
     // Read in the bytes
     int offset = 0;
     int numRead = 0;
     while (offset < bytes.length
            && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
         offset += numRead;
     }
 
     // Ensure all the bytes have been read in
     if (offset < bytes.length) {
         throw new IOException("Could not completely read file "+file.getName());
     }
 
     // Close the input stream and return bytes
     is.close();
     return bytes;
 }
 
 public static void closeResultSet(ResultSet res){
	 try 
		{
			if (res !=null)
				res.close();
			
		}catch (Exception e)
		{				
		}
 }

 public static void closeCallbaleStatement(CallableStatement stmt){
	 try 
		{
			if (stmt !=null)
				stmt.close();
			
		}catch (Exception e)
		{				
		}
 }
 public static void closeStatement(Statement stmt){
	 try 
		{
			if (stmt !=null)
				stmt.close();
			
		}catch (Exception e)
		{				
		}
 }
 
}