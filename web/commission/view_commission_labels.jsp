<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"  
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"
         
%>

<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector labelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  Utility.logger.debug("The size is " + labelVec.size());

%>

  <CENTER>
      <H2> Commission Labels </H2>
    </CENTER>

    <form name="COMform" action="" method="post">
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+"\""+
                  " value=\""+"\">");  
%>
<center>
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<%
   if(labelVec.size()!=0)
   {
%>

<tr class=TableHeader>
<td align='center'>Name</td>
<td align='center'>Description</td>
<td align='center'>Edit</td>
<td align='center'>Label Details</td>
<td align='center'>Upload Excel</td>
<td align='center'>Delete All</td>
<td align='center'>Export List</td>
<td align='center'>Delete Upload File</td>
</tr>
<%
   }
   for(int i = 0;i<labelVec.size();i++)
   {
    LabelModel labelModel = (LabelModel)labelVec.get(i);
    String labelId = labelModel.getLabelId();
    String labelName = labelModel.getLabelName();
    String labelDescription = labelModel.getLabelDescription();
    if(labelDescription == null)labelDescription = "";
%>

<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td><%=labelName%></td>
<td><%=labelDescription%></td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_SHOW_LABEL_DATA+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Label Details \" name=\"labelDetails\" id=\"labelDetails\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_VIEW_LABEL_DETAILS_DATA+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Upload Excel \" name=\"uploadExcel\" id=\"uploadExcel\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_UPLOAD_COMMISSION_DATA+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Delete All \" name=\"delete\" id=\"delete\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_DELETE_ALL_LABEL_DETAILS+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>

<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Export List \" name=\"export\" id=\"export\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_EXPORT_LABEL_DETAILS_DATA+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>

<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Delete Upload File \" name=\"deleteFile\" id=\"deleteFile\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_DELETE_UPLOAD_FILE+"';"+
          "document.COMform."+CommissionInterfaceKey.INPUT_HIDDEN_LABEL_ID+".value = '"+labelId+"';"+
          "COMform.submit();\">");
   %>
</td>
</tr>
<%
   }
%>
</table>
</center>
<br><br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Create New Label \" name=\"new\" id=\"new\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CommissionInterfaceKey.ACTION_ADD_NEW_LABEL+"';"+
                  "COMform.submit();\">");
%>
</center>
</form>
</body>
</html>