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
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <script>
  function returnToPrevious()
  {
  
  
    document.SOPform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SOPInterfaceKey.ACTION_IMPORT_LCS_PRODUCTS_PYHSICAL_AMOUNT_BY_CHANNEL_WAREHOUSE%>';
    SOPform.submit();
  
  
  }
  </script>

<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
%>  
    <CENTER>
      <H2> Import LCS Products Physical Amount </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                                
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
    out.println("<script>returnToPrevious();</script>");
  }
  else
  {
    out.println("<script>alert('Imported Successfully.');</script>");
    out.println("<script>returnToPrevious();</script>");
  }
%>
</body>
</html>
