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
         import="com.mobinil.sds.core.system.dcm.function.model.*"
         import="com.mobinil.sds.core.system.dcm.function.dao.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<script>
    
    function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
    
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  FunctionModel functionModel = (FunctionModel)objDataHashMap.get(DCMInterfaceKey.DCM_EDITED_FUNCTION_MODEL); 
  String functionName = "",functionDesc="",functionUnits = "";
  int functionID =0;
  if(functionModel != null)
  {
  Utility.logger.debug("da fady ");
    functionName = functionModel.get_function_name();
    functionDesc = functionModel.get_function_desc();
    functionUnits = functionModel.get_function_units();
    functionID = functionModel.get_function_id();
    
    }
    Utility.logger.debug("da fady 2323");
            
%>   
    <CENTER>
      <H2> POS Function Details </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID+"\""+
                  " value=\""+"\">");                               
%> 
      
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Function Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME%>" id="" value="<%=functionName%>"></td>        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Description</TD>
          <TD class=TableTextNote colSpan=4><TEXTAREA name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_DESCRIPTION%>" id="" cols=50 rows=5 ><%=functionDesc%></TEXTAREA></td>
        </tr>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Units</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_UNIT%>" id="" value="<%=functionUnits%>"></td>
        </tr>
        <!--TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Target Number</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="" id="" value=""></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Target Units</TD>
          <TD class=TableTextNote colSpan=4><select name="" id=""></select></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Target Period</TD>
          <TD class=TableTextNote colSpan=4><select name="" id=""></select></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Target Start Date</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="" id="" value=""></td>
        </tr-->
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SAVE_NEW_FUNCTION+"';"+
                  "document.DCMform."+DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID+".value='"+functionID+"';"+
                  "checkBeforeSubmit();DCMform.submit()\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
