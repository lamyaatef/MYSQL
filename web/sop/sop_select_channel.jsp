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
		import="com.mobinil.sds.core.system.sop.requests.model.*"
         import="com.mobinil.sds.core.system.sop.schemas.model.*"

%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
Utility.logger.debug(objDataHashMap);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector channels = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  boolean isReportsActions = false;
  isReportsActions = strAction.compareTo(SOPInterfaceKey.ACTION_SHOW_SCRATCH_REPORT)==0 ||
          strAction.compareTo(SOPInterfaceKey.ACTION_SHOW_PRODUCT_REPORT)==0 ||
          strAction.compareTo(SOPInterfaceKey.ACTION_SHOW_TOTAL_REPORT)==0 ? true : false;
  System.out.println("isReportsActions iss "+isReportsActions);
  System.out.println("strAction iss "+strAction);
  
  //String pageHeader = (String)objDataHashMap.get(SOPInterfaceKey.PAGE_HEADER);

 // if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  //{
    //String sysMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    //out.println("<script>alert('"+sysMsg+"');</script>");
 // }

  //String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
 // Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
%>


<script>
     function checkBeforeSubmit()
  {
    <%if (!isReportsActions){%>
 
    var channelValue = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID%>.value;
    if(channelValue == "")
    {
      alert("You must choose Channel.");
    }
    else
    {
        SOPform.submit();
    }
        <%}else { %>
            SOPform.submit();
            <%}%>

  }
  </script>
    <CENTER>
      <H2> Select Channel </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");                  


  //out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
    //              " value=\""+"\">");
       
%>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr>
          <td class=TableHeader>
          Channel
          </td>
          <td>
         
<%
        out.println("<select name='"+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"' id='"+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"' > ");
        out.println("<option value=''></option>");
        String channelId = "";
        
        for (int i = 0 ; i<channels.size();i++)
        {
        	chanelModel  chanelModel = (chanelModel)channels.get(i);       
          channelId = chanelModel.getCHANNEL_ID();
          String channelName =chanelModel.getCHANNEL_NAME();
          out.println("<option value='"+channelId+"'>"+channelName+"</option>");  
          
          
        }
        
        out.println("</select>");
%>
        </td>
        </table>
<br>
<center>
<%
        
              out.print("<INPUT class=button type=button value=\" Next \" name=\"select\" id=\"select\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+strAction+"';"+
                  "checkBeforeSubmit();\">");
%>
</center>
        
</form>    
  </body>
</html>
