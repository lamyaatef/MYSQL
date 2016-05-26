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
  Vector vecWithHoldTax = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  %>
  <CENTER>
      <H2> With Hold Tax List</H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_DCM_CODE+"\""+
          " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX+"\""+
          " value=\""+"\">"); 
  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>
<tr class=TableHeader>
      <td align='center'>DCM Code</td>
      <td align='center'>With Hold Tax</td>
      <td align='center'>Edit</td>
        <td align='center'>Delete</td>
      </tr>
      <%
      float factor = 100.0f;
      for (int i =0;i<vecWithHoldTax.size();i++)
      {
    	  WithHoldTaxModel withHoldTaxModel = (WithHoldTaxModel)vecWithHoldTax.get(i);
    	  String dcmCode = withHoldTaxModel.getDcmCode();
    	  float withHoldTax = withHoldTaxModel.getWithHoldTax();
    	  float netTax= (withHoldTax * factor);
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=dcmCode%></td>
      	<td><%=netTax%>%</td>
      	 <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EDIT_WITH_HOLD_TAX+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_DCM_CODE+".value = '"+dcmCode+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX+".value = '"+withHoldTax+"';"+
                  "ACCform.submit();\">");
          %>
           </td>
          <td align='center'>
          <%
          //System.out.println("beforeeeeeeeeeeeee " + withHoldTax);
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_DELETE_WITH_HOLD_TAX+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_DCM_CODE+".value = '"+dcmCode+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX+".value = '"+withHoldTax+"';"+
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
      out.print("<INPUT class=button type='button' value=\" Create New With Hold Tax \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_ADD_WITH_HOLD_TAX+"';"+
                  "ACCform.submit();\">");
%>
       </center>
       </form>
</body>
</html>