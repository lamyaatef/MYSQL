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

         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <script>
  function checkBeforeSubmit()
  {
    //var dcmValue = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>.value;
    //if(dcmValue == "")
    //{
    //  alert("You must choose DCM.");
    //}
    //else
    //{
      SOPform.submit();
    //}
  }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector vecDataViewsList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String sysMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+sysMsg+"');</script>");
  }
%>  
    <CENTER>
      <H2> Quota Calculation By Dataview </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");                                   
       
%>
        <br>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr class=TableHeader>
          <td colspan=2>
          Select Dataview
          </td>
        </tr>
        <tr>
          <td colspan=2 align='center'>
          <select name='' id=''>
            <option value=""></option>
<%
        for (int i = 0; i < vecDataViewsList.size(); i++) 
        {
            BriefDataViewDTO dtoBriefDataView = (BriefDataViewDTO)vecDataViewsList.elementAt(i);
            if (dtoBriefDataView.getOverRideSQL() !=null)
            {
            Utility.logger.debug(" not going to display "+ dtoBriefDataView.getDataViewName());
            Utility.logger.debug(" the over ride sql is  "+ dtoBriefDataView.getOverRideSQL());
            }
            else
            {            
            %>
              <option value="<%=dtoBriefDataView.getDataViewID()%>"><%=dtoBriefDataView.getDataViewName()%></option> ");
            <%
            }
        
        }
%>
          </select>
          </td>
        </tr>
        </table>
<br>
<center>
<%
        
              out.print("<INPUT class=button type=button value=\" Calculate Quota \" name=\"calculate\" id=\"calculate\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_QUOTA_CALCULATION_BY_DATAVIEW+"';"+
                  "checkBeforeSubmit();\">");
%>
</center>
        
</form>    
  </body>
</html>
