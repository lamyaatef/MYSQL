<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"
         import = " com.mobinil.sds.core.system.dataMigration.model.*"
         
        
       
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Statistics</title>
</head>
 <body>

    <CENTER>
      <H2>Upload Statistics</H2>
    </CENTER>
  <form  name='DMform' id='DMMform' action='' method='post'>  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecStatistics = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_STATISTICS);
	 Vector vecPosCode = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_POS_CODE);
	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 

                  
   // out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID+"\""+
   //               " value=\""+"\">"); 
            
%>  
     

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
     
  <%
  
  if(vecStatistics.size()!=0)

    %>
    <tr class=TableHeader>
      <td>Number Of Read Lines</td>
      <td>Number Of Inserted Lines</td>
      <td>Number Of Invalid POS Code</td>
      <td>Number Distinct Invalid POS Code</td>
        
     </tr> 
     
     <tr> 
         <%    
    
     for (int i=0;i<vecStatistics.size();i++){
    	zipUploadStatisticsModel model = (zipUploadStatisticsModel) vecStatistics.get(i);
 	     String readLines = model.getNO_READ_LINES();
 	    String insertedLInes = model.getNO_INSERTED_LINES();
 	   String invalidPos = model.getIN_VALID_POS_CODE();
 	  String DistinctinvalidPos = model.getDISTINCT_IN_VALID_POS_CODE();
 	 String file_id = model.getFILE_ID();
     
System.out.println("file id isssssssssss"+file_id); %>
      <td> <%=readLines %></td>  
      <td> <%=insertedLInes %></td>  
      <td> <%=invalidPos %></td>  
      <td> <%=DistinctinvalidPos%></td>  
  
    <%
     }
     %>
       </tr>

     </TABLE>
     
      <br> <br> <br>
     <% if(vecPosCode.size()!=0){
 %>

<center>    <H2>Invalid Pos Code</H2>   </center>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>     	 
   
       <tr class=TableHeader> 
       <td>ID</td>	
     <td>Invalid POS Code</td>
      <td>Repeated Frequency</td>
     </tr>

     
 <%
   int counter=0;
  
for (int j=0;j<vecPosCode.size();j++)
    {
        	inValidPosCodeModel model2 = (inValidPosCodeModel) vecPosCode.get(j);
       	    String posCode = model2.getPOS_CODE();
       	    String repeated=model2.getNO_REPEATED();
       
              counter++;
       	    
       %>
       <tr>
        <td><%=counter %></td> 
        <td><%=posCode %></td> 
        <td><%=repeated%></td>                 
      </tr>
       <% 
        }
       }
        %>
 </TABLE>
 </body>
 </html>

