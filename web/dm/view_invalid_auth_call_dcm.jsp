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
 
  <form  name='DMform' id='DMMform' action='' method='post'>  

  <%

     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	 Vector vecdcmCode = (Vector)objDataHashMap.get(DMInterfaceKey.VECTOR_DCM_CODE);
	 String strUserID     =   (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

	
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 

            
%>  

     <% if(vecdcmCode.size()!=0){
 %>

<center>    <H2>Invalid Pos Code</H2>   </center>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>     	 
   
       <tr class=TableHeader> 
       <td>ID</td>	
     <td>In Valid DCM_CODE</td>
     </tr>

     
 <%
   int counter=0;
  
for (int j=0;j<vecdcmCode.size();j++){
        	InValid_Dcm_CodeModel model2 = (InValid_Dcm_CodeModel) vecdcmCode.get(j);
       	    String DCMCode = model2.getPOS_CODE();
       	  
       	    counter++;
       	    
       %>
       <tr>
        
        <td><%=counter %></td> 
        <td> <%=DCMCode %></td> 
  </tr>
             <% 
        }
   
        %>
 </TABLE>
 
 
 
     <%
     }
 else{
     %> 
     <BR><BR>
	<h3> All the DCM CODE in Authentication Call text file  are valid</h3>
	 
	<% 
     } 
     %>
 </body>
 </html>

