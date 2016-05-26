  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
     

       
%>


<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.SimInfoModel"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">      
    </head>
  <body>
  <form  name='AUTHform' id='AUTHform' action='' method='post'>  
<%
 
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    Vector<SimInfoModel> files  = (Vector<SimInfoModel>) objDataHashMap.get(AuthResInterfaceKey.VECTOR_FILES);
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
 " value=\""+request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID)+"\">");  
out.println("<input type=\"hidden\" name=fieldId id=fieldId value=\""+"\">");

%>  
    <CENTER>
      <H2>SIM Information File </H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="12%" nowrap align=middle>File Id</td>        
         <td width="12%" nowrap align=middle>Label</td>
         <td width="12%" nowrap align=middle>Status</td>
         <td width="12%" nowrap align=middle>Duration (h)</td>
         <td width="12%" nowrap align=middle>Row count</td>
          <td width="20%" nowrap align=middle>Delete</td>
          <td width="20%" nowrap align=middle>Download</td>


      </TR>
      <%
      
        String fileId="";    	
    	String status="";
    	String label="";        
    	String duration="";        
    	String rowCount="";        
    	for (SimInfoModel simInfoModel : files)
        {
         fileId = simInfoModel.getFileId();   
         status = simInfoModel.getStatus();   
         label = simInfoModel.getLabelId();
         duration = simInfoModel.getDuration();
         duration = duration!=null ? duration.length()>5 ? duration.substring(0, 4) : duration  : "";
         rowCount = simInfoModel.getRowCount();
         rowCount = rowCount!=null ? rowCount : "";
      %>
      <TR class=TableTextNote>
        <td width="12%" nowrap align=middle><%=fileId %></td>        
         <td width="12%" nowrap align=middle><%=label%></td>
        <td width="12%" nowrap align=middle><%=status%></td>
        <td width="12%" nowrap align=middle><%=duration%></td>
        <td width="12%" nowrap align=middle><%=rowCount%></td>
     
        <TD width="15%" noWrap align=middle>
            <INPUT class=button type=button value="Delete" name="Delete" onclick="loadDeleteField('<%=fileId %>');">
        
        </TD>
        
        
        
         <TD width="15%" noWrap align=middle>
             <INPUT class=button type=button  <%=status.equalsIgnoreCase("processing") || status.equalsIgnoreCase("Deleted") ? "disabled" : ""%>  value= "Download"  name="Download" onclick="loadField('<%=fileId %>');">
        
        </TD>
    <%
      }
    	
      %>
  </TR>
       
  
    </table>
  <script type="text/javascript">
      function loadField(id)
      {
          AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=AuthResInterfaceKey.ACTION_DOWNLOAD_SIM_INFO_RESULT_FILE%>';
          document.AUTHform.fieldId.value=id;          
          AUTHform.submit();
      }
      function loadDeleteField(id)
      {   
          AUTHform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%= AuthResInterfaceKey.ACTION_DELETE_AUTH_SIM_INFO_FILE%>';
          document.AUTHform.fieldId.value=id;          
          AUTHform.submit();
      }

      </script>
 </form>

  </body>
</html>