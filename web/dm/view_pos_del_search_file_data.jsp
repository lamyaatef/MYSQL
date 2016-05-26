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
		 file_path  = file_path + "/POS_DEL_Chk-Data"+fileId+".txt";
		
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
    	 PosDelSearchfileDataModel model = (PosDelSearchfileDataModel) vecdata.get(i);
  
    	
    
    	 
    	 
    	 

    	String SEARCH_ID  =model.getSEARCH_ID();             
    	String   DCM_CODE   =model.getDCM_CODE();         
    

    	
    	String  DELIVERED              =model.getDELIVERED();  
    	if(DELIVERED==null)
    	{
    		DELIVERED="";
    		
    	}
    	String NI =  model.getNI();
    	if(NI==null)
    	{
    		
    		NI="";	
    		
    	}
    	String   PROBL=model.getPROBL();     
    	
    	if(PROBL==null)
    	{
    		
    		PROBL ="";    		
    	}
    	String NOTD =model.getNOTD();
    	
    	if(NOTD==null)
    	{

    		NOTD="";
        }
    	String   ACC =model.getACC();
    	
    	if(ACC==null)
    	{
    		ACC= "" ;
         }
    	
    		
    	

   try{
	   

	   
    oout.write(SEARCH_ID);
    oout.write(",");
    oout.write(DCM_CODE);
    oout.write(",");
    oout.write(DELIVERED);
    oout.write(",");
    oout.write(NI);
    oout.write(",");
    oout.write(PROBL);
    oout.write(",");
    oout.write(NOTD);
    oout.write(",");
    oout.write(ACC);
   
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