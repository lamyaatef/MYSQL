<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
        import = " com.mobinil.sds.core.system.*"
         
        
       
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.model.* "%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.Sheet"%>

<%@page import="com.mobinil.sds.web.interfaces.*"%>
<%@page import="com.mobinil.sds.core.system.cr.contract.model.lcsProductMappingModel"%><html>


<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search File Data</title>
</head>
 <body>

    <CENTER>
      <H2>Search File Data</H2>
    </CENTER>
  <form  name='Authform' id='Authform' action='' method='post'>  
  
       <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecdata = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_SEARCH_FILES_DATA);
	 
	 
	 if(vecdata==null || vecdata.size()==0)
	 {
		 
		 
		    printToStream("<h3>",out);
		    printToStream("No valid codes in search file :",out);
		    printToStream("</h3>",out);	 
		 
		 
	 }

	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	String  fileId= (String) objDataHashMap.get(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 
     
     
     
         
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  BufferedWriter oout =null;
  
	String file_path = request.getRealPath("/dm/uploadtext");

		 
	

  if(vecdata.size()!=0)
  {
   System.out.println("ddddddddddddddddddd: "+file_path);
		 //FileWriter fstream = new FileWriter("c://invalid_sims.txt");
		 
		 try{
		 file_path  = file_path + "/POS_ELG_Chk-Data"+fileId+".txt";
		
		 FileWriter fstream = new FileWriter(file_path);
	     oout = new BufferedWriter(fstream);
		 }
	
			 
	catch (IOException ex)
	{
			 
		ex.printStackTrace();
		
		
	    printToStream("<h3>",out);
	    printToStream("Operation Completed Not Done:",out);
	    printToStream("</h3>",out);
		
		
	}
 

    %>
    
   <%--   
    
      <tr class=TableHeader>
      <td>Search File ID</td>
      <td>SIM Serial</td>
      <td>Dial</td>
      <td>Activation Date</td>
      <td>POS CODE</td>
      <td>Second POS CODE</td>
      <td>STF BATCH Date</td>
      <td>CIF Batch Date </td>
      <td>Sheet Distributor Code</td>
      <td>Sheet POS CODE</td>
       <td>LCS DCM ID </td>
      <td>LCS Init Date</td>
       <td> LCS_Contract_Typoe_ID </td>
      <td>Second POS Name</td>
      <td>Sheet Super Dealer</td>
      

     
        
     </tr> 
     
    --%>
         <%    

         
         
        
     for (int i=0;i<vecdata.size();i++){
    	 PosElgSearchfileDataModel model = (PosElgSearchfileDataModel) vecdata.get(i);
  
    	
    
    	 
    	 
    	 

    	String SEARCH_ID  =model.getSEARCH_ID();             
    	String   POS_CODE   =model.getPOS_CODE();         
    	String  POS_ENM              =model.getPOS_ENM();  
    	if(POS_ENM==null)
    	{
    		POS_ENM="";
    		
    	}
    	String ENTRY_DT =        model.getENTRY_DT();
    	if(ENTRY_DT==null)
    	{
    		
    		ENTRY_DT="";	
    		
    	}
    	String  STK_DLVR_DT=model.getSTK_DLVR_DT();     
    	
    	if(STK_DLVR_DT==null)
    	{
    		
    		STK_DLVR_DT ="";    		
    	}
    	String IQRAR_DLVR_DT =model.getIQRAR_DLVR_DT();
    	
    	if(IQRAR_DLVR_DT==null)
    	{

    		IQRAR_DLVR_DT="";
        }
    	String   IQRAR_RCV_DT =model.getIQRAR_RCV_DT();
    	
    	if(IQRAR_RCV_DT==null)
    	{
    		IQRAR_RCV_DT = "" ;
         }
    	String  POS_STATUS =model.getPOS_STATUS() ;      
    	if(POS_STATUS==null)
    	{
    		POS_STATUS = "";
        }
    	String STK_STATUS=model.getSTK_STATUS();
    	
    	
    	if(STK_STATUS==null)
    		
    	{
	
    		STK_STATUS ="" ;
    	}
    	
    	
    	String  REGIONAL_NAME  =model.getREGIONAL_NAME();        
    	
    	
    	
    	if(REGIONAL_NAME==null)
    	{
    		
    		REGIONAL_NAME ="" ;
    	}
    	String REP_NAME =model.getREP_NAME()  ; 
    	
    	
    	if (REP_NAME==null){
    		
    		REP_NAME ="" ;
    		
    	}
    	String CHANNEL_CODE  =model.getCHANNEL_CODE();
    	if(CHANNEL_CODE==null){
    		
    		CHANNEL_CODE ="";
    		
    		
    	}
    	String LEVEL_CODE  =model.getLEVEL_CODE();
    	
    	if(LEVEL_CODE==null)
     		
    	{
    		
    		LEVEL_CODE ="" ;
    		
    		
    	}
    	String STK_DIAL_NO  =model.getSTK_DIAL_NO();       
    	if(STK_DIAL_NO==null){
    		
    		
    		STK_DIAL_NO ="";
    		
    	}

   try{
	   

	   
    oout.write(SEARCH_ID);
    oout.write(",");
    oout.write(POS_CODE);
    oout.write(",");
    oout.write(POS_ENM);
    oout.write(",");
    oout.write(ENTRY_DT);
    oout.write(",");
    oout.write(STK_DLVR_DT);
    oout.write(",");
    oout.write(STK_STATUS);
    oout.write(",");
    oout.write(IQRAR_DLVR_DT);
    oout.write(",");
    oout.write(IQRAR_RCV_DT);
    oout.write(",");
    oout.write(POS_STATUS);
    oout.write(",");
    oout.write(STK_STATUS);
    oout.write(",");
    oout.write(REGIONAL_NAME);
    oout.write(",");
    oout.write(REP_NAME);
    oout.write(",");
    oout.write(CHANNEL_CODE);
    oout.write(",");
    oout.write(LEVEL_CODE);
    oout.write(",");
    oout.write(STK_DIAL_NO);
    oout.write(",");
    oout.write("\n");
    oout.flush();

   }
   
  catch(Exception ex){
	//fe record matktbsh 
	ex.printStackTrace();
	
   }
    
    
    
%>

<%--  
            <td> <%=SEARCH_ID %></td>  
             <td> <%=SIM_SERIAL %></td>  
            <td > <%=DIAL %></td>  
            <td> <%=ACTIVATION_DATE %></td>
            <td> <%=POS_CODE %></td>  
            <td> <%=SECOND_POS_CODE %></td>
            <td> <%=STF_BATCH_DATE %></td>  
             <td> <%=CIF_BATCH_DATE %></td>
            <td > <%=SHEET_DISTRIBUTOR_CODE %></td>  
            <td > <%=SHEET_POS_CODE%></td>  
            <td> <%=LCS_DCM_ID %></td>
            <td > <%=LCS_INIT_DATE %></td>  
            <td> <%=LCS_CONTRACT_TYPE_ID %></td>
            <td> <%=SECOND_POS_NAME %></td>  
            <td> <%=SHEET_SUPER_DEALER %></td>
 
   </tr>
   --%>
    <%
    
 

    
     }
         oout.close();
         
         printToStream("<h3>",out);
         printToStream("Operation Completed Successfully: " + file_path,out);
         printToStream("</h3>",out);
         
        
   %>
       

     </TABLE>
     
<% 
  } 
   else
   
   {
	
	
    printToStream("<h3>",out);
    printToStream("Operation Completed Not Done:",out);
    printToStream("</h3>",out);	
	
}




%>

 </body>
 </html>