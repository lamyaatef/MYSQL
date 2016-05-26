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
         
         import="com.mobinil.sds.core.system.sop.equations.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
  function checkBeforeSubmit()
  { 
      var dcmInputs = document.SOPform.getElementsByTagName("input");
      var dcmchecked = 0;
      for(var i = 0;i<dcmInputs.length;i++)
      {
        var dcmInputsValue = dcmInputs[i].checked;
        if(dcmInputs[i].type == "checkbox")
        {
          if(dcmInputsValue == true)
          {
            dcmchecked = 1;
          }
        }
      }
      if(dcmchecked == 0)
      {
        alert("You must choose DCM.");
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

  DCMDto dcmDto = (DCMDto)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String sysMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+sysMsg+"');</script>");
  }
%>  
    <CENTER>
      <H2> DCM Product Limits By Equation </H2>
    </CENTER>
    
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");      

 out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME+"\""+
                  " value=\""+"\">");                  
       
%>
<h4>Please select DCM from the DCM list : </h4>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr class=TableHeader>
          <td align='center'>DCM Name</td>
          <td align='center'></td>
        </tr>
<%
        for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
        {
%>
        <tr>
          <td align='center'>
<%
        
          DCMModel model = dcmDto.getDcm(index);       
          int dcmID = model.getDcmId();
          String dcmName = model.getDcmName();
          out.println(dcmName);  
%>
          </td>
          <td align='center'>
<%
        out.println("<input type=\"checkbox\" name='"+SOPInterfaceKey.INPUT_HIDDEN_DCM_ID+"_"+dcmID+"' id='"+SOPInterfaceKey.INPUT_HIDDEN_DCM_ID+"_"+dcmID+"' > ");
%>
          </td>
        </tr>
<%
    }
%>
        </table>
<br>
<center>
<%
        
              out.print("<INPUT class=button type=button value=\" Next \" name=\"select\" id=\"select\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DCM_PRODUCT_LIMITS_BY_EQUATION_SELECT_PRODUCTS+"';"+
                  "checkBeforeSubmit();\">");
%>
</center>
        
</form>    
  </body>
</html>
