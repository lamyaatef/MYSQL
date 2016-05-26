<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.pos.model.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.city.model.*"



%>
<html>
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>


<body>
<form name="genericTableViewForm" action="" method="post">
<%
  
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strGenericTableName = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_GENERIC_TABLE_NAME);
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
  {
    String strJobId = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
    
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_JOB_ID+"\""+
                  " value=\""+strJobId+"\">"); 

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.CONTROL_TEXT_GENERIC_TABLE_NAME+"\""+
                  " value=\""+strGenericTableName+"\">"); 

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_SELECT_GENERIC_TABLE+"\">");

   %>               
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="300" height="100">
           <param name="movie" value="../resources/img/loading.swf">
           <param name="quality" value="high">
           <embed src="../resources/img/loading.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="300" height="100"></embed>
		 </object>
    <script>
    setTimeout("genericTableViewForm.submit();", 5000);
    </script>

    <%
  }
  else
  {
  
  

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String errMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_FIND_GENERIC_TABLE+"\">");

    out.println("<script>alert('"+errMsg+"'); genericTableViewForm.submit();</script>");
  }
  else
  {

  
%>
  
  <input type="hidden" name="<%=DCMInterfaceKey.INPUT_HIDDEN_SELECTED_GENERIC_ITEM_ID%>" value="">
<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
  <tr class="TableHeader">
  <%
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

         
    int columnCount = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.DCM_GENERIC_TABLE_COLUMN_COUNT));
    int rowCount = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.DCM_GENERIC_TABLE_ROW_COUNT));
    Vector columnNames = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_COLUMN_NAMES);
    Vector tableData = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_GENERIC_MODEL_DATA_VECTOR);
    String tableName = (String)objDataHashMap.get(DCMInterfaceKey.GENERIC_TABLE_NAME);
      out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.GENERIC_TABLE_NAME+"\" value=\""+tableName+"\">");
    for(int i = 0 ; i < columnCount ; i++)
    {
      String columnName = (String)columnNames.get(i);
      out.println("<td align='center'>"+columnName+"</td>");
    }
        out.println("<td align='center'>EDIT</td>");    
    %>
  </tr>
  
  <%
  GenericModel genericModel =null;
Utility.logger.debug("COUNTSSS  "+ tableData.size());
  for(int i = 0 ; i < rowCount ; i ++)
  {
  %>
  <tr class="TableTextNote">
  <%
    genericModel = (GenericModel)tableData.get(i);
    for(int j = 0 ; j < columnCount ; j++)
        {
        out.println("<td align='center'>");
          switch(j)
          {
            case 0:
              out.println(genericModel.get_field_1_value());
              break;
            case 1:
              out.println(genericModel.get_field_2_value());
              break;              
            case 2:
              out.println(genericModel.get_field_3_value());
              break;              
            case 3:
              out.println(genericModel.get_field_4_value());
              break;              
            case 4:
              out.println(genericModel.get_field_5_value());
              break;              
          }
              out.println("</td>");
        }
    String itemID = genericModel.get_primary_key_value();
    out.println("<td align='center'><input name=\"edit_"+i+"\" type=\"button\" class=\"button\" value=\"Edit\" onclick=\""+
                  "document.genericTableViewForm."+DCMInterfaceKey.INPUT_HIDDEN_SELECTED_GENERIC_ITEM_ID+".value='"+itemID+"';"+
                  "document.genericTableViewForm."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                  DCMInterfaceKey.ACTION_EDIT_GENERIC_ITEM+"';"+
                  "document.genericTableViewForm.submit();\"/></td>");
   
    out.println("</tr>");
  }
   %>
</table>
<p align="center">
<%
out.println("<input name=\"Submit2\" type=\"button\" class=\"button\" value=\"Add New Record\" onclick=\"document.genericTableViewForm."+InterfaceKey.HASHMAP_KEY_ACTION+
              ".value='"+DCMInterfaceKey.ACTION_ADD_NEW_GENERIC_ITEM+"'; document.genericTableViewForm.submit();\">");
%>
</p>
<%
}
}
%>
</form>
</body>
</html>
