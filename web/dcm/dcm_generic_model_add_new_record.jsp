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
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"


%>
<html>
<head>

 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
  <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>

<body>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  GenericModel genericModel = (GenericModel)objDataHashMap.get(DCMInterfaceKey.DCM_GENERIC_MODEL_EDIT_VALUES);
  Vector columnNames = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_COLUMN_NAMES);
  String tableName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME);
  String field_1 ="";
  String field_2 ="";
  String field_3 ="";
  String field_4 ="";
  String field_5 ="";
  String pk_value   = "";
  String pk_name = "";
  
  if(genericModel!=null ){
   field_1 = genericModel.get_field_1_value();
   field_2 = genericModel.get_field_2_value();
   field_3 = genericModel.get_field_3_value();
   field_4 = genericModel.get_field_4_value();
   field_5 = genericModel.get_field_5_value();
    pk_value = genericModel.get_primary_key_value();
    pk_name = genericModel.get_primary_key_name();
  }
      pk_name = genericModel.get_primary_key_name();
      Utility.logger.debug("PKKKKKKKKKKK  "+pk_name);
      Utility.logger.debug(pk_value);
%>
<table align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
<form name='saveForm' action='' method='post'> 
  <%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.DCM_GENERIC_TABLE_COLUMN_COUNT+"\""+
                  " value=\""+columnNames.size()+"\">");                    
 out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_NAME+"\""+
                  " value=\""+pk_name+"\">");                    
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_PK_VALUE+"\""+
                  " value=\""+pk_value+"\">");                    
 out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_GENERIC_MODEL_TABLE_NAME+"\""+
                  " value=\""+tableName+"\">");                    
                  

       String columnName ="";
  for(int i = 0 ; i < columnNames.size() ; i++){
   
    columnName = (String)columnNames.get(i);
       out.println("<tr>");
       out.println("<td height=\"29\" class=\"TableHeader\">"+columnName+"</td>");
       switch(i){
       case 0:
       out.println("<td class=\"TableTextNote\"><input type=\"text\" name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i+"' value=\""+field_1+"\"></td>");
       break;
       case 1:
       out.println("<td class='TableTextNote'><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i+"' value='"+field_2+"'></td>");
       break;
       case 2:
       out.println("<td class=\"TableTextNote\"><input type=\"text\" name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i+"' value=\""+field_3+"\"/></td>");
       break;
       case 3:
       out.println("<td class=\"TableTextNote\"><input type=\"text\" name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i+"' value=\""+field_4+"\"/></td>");
       break;
       case 4:       
       out.println("<td class=\"TableTextNote\"><input type=\"text\" name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GENERIC_MODEL_FIELD+"_"+i+"' value=\""+field_5+"\"/></td>");
       break;
       }
     out.println("</tr>");
    }
  %>
</table>
</form>
<p align="center">
<%
  out.println("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"Save\" onclick=\""+
              "document.saveForm."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DCMInterfaceKey.ACTION_SAVE_NEW_GENERIC_ITEM+"';"+
              "document.saveForm.submit();"+"\"/>");
  %>
</p>
</body>
</html>
