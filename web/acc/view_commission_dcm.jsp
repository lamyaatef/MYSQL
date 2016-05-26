<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"
%>

<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector commissionDcm = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  %>
  <CENTER>
      <H2> Commission DCM List</H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID+"\""+
          " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID+"\""+
          " value=\""+"\">"); 
  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<tr class=TableHeader>
      <td align='center'>Old DCM Code</td>
      <td align='center'>Old DCM Name</td>
      <td align='center'>New DCM Code</td>
      <td align='center'>New DCM Name</td>
      <td align='center'>Edit</td>
      <td align='center'>Delete</td>
      </tr>
      <%
      for (int i =0;i<commissionDcm.size();i++)
      {
    	  CommissionDcmFixModel commissionDcmFixModel = (CommissionDcmFixModel)commissionDcm.get(i);
    	  String oldDcmId = commissionDcmFixModel .getOldDcmId();
    	  String oldDcmCode = commissionDcmFixModel.getOldDcmCode();
    	  String oldDcmName = commissionDcmFixModel.getOldDcmName();
    	  String newDcmId = commissionDcmFixModel.getNewDcmId();
    	  String newDcmCode = commissionDcmFixModel.getNewDcmCode();
    	  String newDcmName = commissionDcmFixModel.getNewDcmName();
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=oldDcmCode%></td>
      	<td><%=oldDcmName%></td>
      	<td><%=newDcmCode%></td>
      	<td><%=newDcmName%></td>
      	 <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EDIT_COMMISSION_DCM+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID+".value = '"+oldDcmId+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID+".value = '"+newDcmId+"';"+
                  "ACCform.submit();\">");
          %>
           </td>
          <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_DELETE_COMMISSION_DCM+"';"+
            		  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID+".value = '"+oldDcmId+"';"+
                      "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID+".value = '"+newDcmId+"';"+
                  "ACCform.submit();\">");
          %>
           </td>
      	</tr>
    	  <%
      }
      %>
      
       </TABLE>
       </center>
       <br><br>
       <center>
       <%
      out.print("<INPUT class=button type='button' value=\" Create New Commission DCM \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_CREATE_NEW_COMMISSION_DCM+"';"+
                  "ACCform.submit();\">");
%>
       </center>
       </form>
</body>
</html>