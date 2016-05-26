<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.commission.model.*"
%>

<html>
<head>
</head>
<body>
<form name='COMform' action='' method='POST'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strTypeID = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID);
  String strCategoryID = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
  GenericModel categoryModel=(GenericModel)objDataHashMap.get(CommissionInterfaceKey.MODEL_EDIT_CATEGORY_MODEL);
  String categoryName = "";
  String categoryDesc = "";
  if(categoryModel != null){
   categoryName = categoryModel.get_field_2_value();
   categoryDesc = categoryModel.get_field_4_value();
  }
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
  if(strCategoryID != null)
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+"\""+
                  " value=\""+strCategoryID+"\">");                  
  else
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+"\""+
                  " value=\""+""+"\">"); 
                  Utility.logger.debug("aaaaaaaaaaaaa:  "+strTypeID);
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_TYPE_ID+"\""+
                  " value=\""+strTypeID+"\">");                  

%>
<link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1" style="gray3"> 
<tr>
  <td height="14" colspan="2" class="TableHeader">Commission Type Category Details</td>
</tr>
<tr>
<td class='gray3'> Category Name</td><td class='gray3'><input type="text" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY_NAME%>" id="" value="<%=categoryName%>"/></td>
</tr>
<tr>
<td class='gray3'> Category Description</td><td class='gray3'><textarea name='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CATEGORY_DESC%>' cols="70" rows="5"><%=categoryDesc%></textarea></td>

</tr>
<tr><td colspan='2' align='middle'>
<%
out.println("<input type='button' name='save' value='Save' class='button' onclick=\"document.COMform."+
            InterfaceKey.HASHMAP_KEY_ACTION+".value='"+CommissionInterfaceKey.ACTION_COMMISSION_SAVE_COMMISSION_CATEGORY+"';"+
            "COMform.submit();\">");
%>
</td></tr>
</form>
</body>
</html>
