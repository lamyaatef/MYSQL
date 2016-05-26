<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.equations.model.*"
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

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss " + strChannelId);
  
  Vector vecEquations = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector vecEquationStatusList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%>
    <CENTER>
      <H2> Equations List </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID+"\""+
                  " value=\""+"\">");                 
%>                  
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
      <td>Equation Name</td>
      <td>Description</td>
      <td align='center'>Equation Status</td>
      <td align='center'>Edit</td>
    </tr> 
<%
    for(int i =0;i<vecEquations.size();i++)
    {
    EquationModel equationModel = (EquationModel)vecEquations.get(i);
    int equationId = equationModel.getEquationId();
    String equationTitle = equationModel.getEquationTitle();
    String equationDescription = equationModel.getEquationDescription();
    if(equationDescription == null)
    equationDescription = "";
    int lastStatusId = equationModel.getLastStatusId();
    int equationStatusId = equationModel.getEquationStatusId();
    String equationStatusName = equationModel.getEquationStatusName();
%>
    <tr class=TableTextNote>
      <td><%=equationTitle%></td>
      <td><%=equationDescription%></td>
      <td align='center'>
            <select name='<%=SOPInterfaceKey.INPUT_SELECT_EQUATION_STATUS%>_<%=equationId%>' id='<%=SOPInterfaceKey.INPUT_SELECT_EQUATION_STATUS%>_<%=equationId%>'>
            <%
            for(int j=0;j<vecEquationStatusList.size();j++)
            {
              EquationStatusModel equationStatusModel = (EquationStatusModel)vecEquationStatusList.get(j);          
              int equationStatusIdX = equationStatusModel.getEquationStatusId();
              String equationStatusNameX = equationStatusModel.getEquationStatusName();
              String equationStatusSelected = "";
              if(equationStatusIdX == equationStatusId)
              {
                equationStatusSelected = "selected";
              }
              %>
              <option value='<%=equationStatusIdX%>' <%=equationStatusSelected%>><%=equationStatusNameX%></option>
              <%
            }
            %>
          </select>
      </td>

      <td align='center'>
<%
      out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_EDIT_EQUATION+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_EQUATION_ID+".value = '"+equationId+"';"+
                  "SOPform.submit();\">");
%>    
      </td>
    </tr>   
<%
    }
%>    
  </table>

    <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Add New \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_ADD_EQUATION+"';"+
                  "SOPform.submit();\">");

      out.print("<INPUT class=button type='button' value=\" Update Eqautions Status \" name=\"update\" id=\"update\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_UPDATE_EQUATION_STATUS+"';"+
                  "SOPform.submit();\">");

      out.print("<INPUT class=button type='button' value=\" Assign To Products \" name=\"products\" id=\"products\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_ASSIGN_EQUATION_TO_PRODUCTS+"';"+
                  "SOPform.submit();\">");
                      
%>
    </center>
  </form>
  </body>
</html>
