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

         import="com.mobinil.sds.core.system.sop.schemas.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
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
    
	function checkUncheckAll(theElement) 
	{
		var theForm = theElement, z = 0;
		for(z=0; z<theForm.length;z++)
		{
			if(theForm[z].type == 'checkbox' && theForm[z].disabled == false )
			{
 				if(theForm[z].checked == true){
          return true;        
          }
			}
		}
    return  false;
	}

  function checkBeforeSubmit()
  {
    var dcmQuota = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>.value;
    var channel = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID%>.value;
    var statement = "";
    if(channel == 1)
    {
      statement = "DCM";
    }
    else
    {
      statement = "Franchise";
    }
    if(dcmQuota == "")
    {
      alert("You must enter Quota value.");
    }
    else if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>.value")))
      {
            alert("Quota value must be a number.");
            return; 
      }
    
    else if(!checkUncheckAll(SOPform))
    {
      
      alert("You must select at least one "+statement);
    }
    else
    { 
      SOPform.submit();
    }
  }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  String statement = "";
  String statement2 = "";
  if (strChannelId.equals("1"))
  {
    statement = "To DCM (select dcm to set fixed quota values):";
    statement2 = "DCM";
  }
  else
  {
    statement = "To Franchise (select franchise to set fixed quota values):";
    statement2 = "Franchise";
  }
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  
  DCMDto dcmDto = (DCMDto)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String sysMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+sysMsg+"');</script>");
  }
%>  
    <CENTER>
      <H2> Fixed Quota Values </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
%>
        <table border=0>
        <tr>
          <td>
            Set quota value equals                
          </td>
          <td>
            <input type='text' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>'>
          </td>
        </tr>
        <tr>
          <td colspan=2><%=statement%></td>
        </tr>
        </table>

        <br>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr class=TableHeader>
          <td>
          <%=statement2%> Name 
          </td>
          <td></td>
        </tr>
<%
        for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
        {
          DCMModel model = dcmDto.getDcm(index);       
          int dcmID = model.getDcmId();
          String dcmName = model.getDcmName();
          %>
          <tr>
          <td><%=dcmName%></td>
          <td width=3%><input type='checkbox' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>_<%=dcmID%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_ID%>_<%=dcmID%>'></td>
          </tr>
          <%
        }
%>
        </table>
<br>
<center>
<%
        
              out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_FIXED_QUOTA_VALUES+"';"+
                  "checkBeforeSubmit();\">");
%>
</center>
        
</form>    
  </body>
</html>
