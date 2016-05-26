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
 <%@page import="com.mobinil.sds.web.interfaces.dm.*"%>
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResSearchDataModel"%>

<%@page import="com.mobinil.sds.core.system.dataMigration.model.*"%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.Sheet"%>
<%@page import="com.mobinil.sds.core.system.cr.contract.model.lcsProductMappingModel"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResInvalidsimSerialModel"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search File Data</title>
</head>
 <body>

    <CENTER>
      <H2>Invalid DCM code</H2>
    </CENTER>
  <form  name='Authform' id='Authform' action='' method='post'>  
  
     <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecdata = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_SEARCH_FILES_INVALID_POS);

	 String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	 String fileId = (String)objDataHashMap.get(DMInterfaceKey.CONTROL_HIDDEN_FILE_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 


            
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
     BufferedWriter oout =null;
  	String file_path = request.getRealPath("/dm/uploadtext");
	System.out.println("ddddddddddddddddddd: "+file_path);
	 //FileWriter fstream = new FileWriter("c://invalid_sims.txt");
	 file_path  = file_path + "/invalid_dcm"+fileId+".txt";
	
 
     if(vecdata.size()!=0)
     {
    	 
    	 try{
    		 FileWriter fstream = new FileWriter(file_path);
    	     oout = new BufferedWriter(fstream);
    		 }
    		 catch(IOException  ex)
    		 {
    			 
    			 ex.printStackTrace();
    		     printToStream("<h3>",out);
    		     printToStream("Operation Not Done Cannot Write File",out);
    		     printToStream("</h3>",out);
    			 
    		 }
    %>
    
    <%--  
    <tr class=TableHeader>
      <td>Search File ID</td>
      <td>Invalid SIM Serial</td>

      

     
        
     </tr> 
     
    --%>
         <%    
    	
        
     for (int i=0;i<vecdata.size();i++){
    	 PosDelInvalidPosCode model = (PosDelInvalidPosCode) vecdata.get(i);
    	%>
    	<%--   <tr>  --%>  
    	<%
    	
    	

    	String SEARCH_ID =model.getSEARCH_ID();             
    	String  pos_code =model.getSIM_SERIAL();         
    	

    	

try{

    oout.write(SEARCH_ID);
    oout.write(",");
    oout.write(pos_code);
    oout.write("\n");
  
   }
   
  catch(Exception ex){
	
	ex.printStackTrace();
	
}
    
    
    
   %>
   
   <%--  
             <td> <%=SEARCH_ID%></td>  
             <td> <%=SIM_SERIAL%></td>  
 </tr>
  --%>
    <%
    
 

    
     }
         oout.close();
         
   %>
       

     </TABLE>
     <%  
     
     printToStream("<h3>",out);
     printToStream("Operation Completed Successfully: " + file_path,out);
     printToStream("</h3>",out);
     
     }
     
     else{
    	 
    	 
         printToStream("<h3>",out);
         printToStream("No Invalid DCM code:",out);
         printToStream("</h3>",out);	 
    	 
     }
     
     
  
     %>
     

 </body>
  </html>
