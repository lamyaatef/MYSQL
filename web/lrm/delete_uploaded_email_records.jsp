<%@page import="com.mobinil.mcss.level_relation_management.LevelRelationManagementDAO"%>
<%@page import="com.mobinil.mcss.level_relation_management.LevelRelationManagementInterfaceKey"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
         import="javax.servlet.*" import="javax.servlet.http.*"
         import="java.io.PrintWriter" import="java.io.IOException"
         import="java.util.*" import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.mcss.level_relation_management.model.*"
         
         %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

    <link href="../resources/css/Template2.css" rel="stylesheet"
          type="text/css" />
    <SCRIPT language=JavaScript src="../resources/js/calendar.js"
    type=text/javascript></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript"
    src="../resources/js/jquery_tablesorter_pack.js"></script>
    <SCRIPT language=JavaScript src="../resources/js/tree.js"
    type=text/javascript></SCRIPT>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css"
          type="text/css" />
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    <script language="javascript">



        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(Emailform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

      
        function clearValues()
        {
            document.Emailform.<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_SENDER_EMAIL%>.value="";
            document.Emailform.<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_POS_CODE%>.value="";
            document.Emailform.<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_FROM_DATE%>.value="";
            document.Emailform.<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_TO_DATE%>.value="";
             }
    
    </script>
    <script>
        function checkForSubmit()
        {	
            Emailform.submit()
        }
    </script>
</head>



<html>
  <body>

    <%
        HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector SearchResults = null;
        SearchResults = (Vector) objDataHashMap.get(LevelRelationManagementInterfaceKey.VECTOR_EMAIL_SEARCH_RESULT);
        String emailId = "";
        String emailFromDate = "";
        String emailToDate = "";
        String senderEmail="";
        String posCode="";

        
    
    %>

<center>
    <H2>Email Search</H2>
</center>
<form name="Emailform" action="" method="post">
    <%
        out.println("<input type=\"hidden\" name=\""
                + InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
                + "\">");

        out.println("<input type=\"hidden\" name=\""
                + InterfaceKey.HASHMAP_KEY_USER_ID + "\"" + " value=\""
                + strUserID + "\">");

        
    %> 

    <table style="BORDER-COLLAPSE: collapse" width="100%" border="1"
           align="center" cellpadding="0" cellspacing="1">
        <tr>
            <td colspan=6 class="TableHeader">Search</td>
        </tr>
        <tr>
            
            <td class="TableTextNote" align="center">Sender Email</td>
            <td class="TableTextNote" align="center"><input type="text"
                                                            name="<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_SENDER_EMAIL%>"
                                                            value="<%=senderEmail%>" /></td>
        </tr>
        <tr>
            <td class="TableTextNote" align="center">POS CODE</td>
             <td class="TableTextNote" align="center"><input type="text"
                                                            name="<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_POS_CODE%>"
                                                            value="<%=posCode%>" /></td>
        </tr> 
        
        <tr>
            <td height="27" class="TableTextNote" align="center">From Date
               </td>
            <td class="TableTextNote" align="center"><script>drawCalender('<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_FROM_DATE%>' , "<%=emailFromDate%>");</script></td>
           </tr>
        <tr>
            <td height="27" class="TableTextNote" align="center">To Date
                </td>
            <td class="TableTextNote" align="center"><script>drawCalender('<%=LevelRelationManagementInterfaceKey.CONTROL_TEXT_EMAIL_TO_DATE%>' , "<%=emailToDate%>");</script></td>
         
        </tr>

        <tr>
            <td colspan="6" align="center">
                <%
                    out.println("<input name=\"Button\" type=\"button\" class=\"button\" value=\"Search\" onclick=\"document.Emailform."
                            + InterfaceKey.HASHMAP_KEY_ACTION
                            + ".value='"
                            + LevelRelationManagementInterfaceKey.ACTION_EMAIL_SEARCH
                            + "'; checkForSubmit();\"/>");
                %> <input name="Submit2" type="button" class="button"
                       value="Clear Values" onclick="clearValues();" />
                </div>
            </td>
        </tr>
    </table>
    <p>&nbsp;</p>

    <%
        if (SearchResults != null && SearchResults.size() != 0) {

            out.println("<input type=\"hidden\" name=\""
                    + LevelRelationManagementInterfaceKey.INPUT_HIDDEN_EMAIL_ID
                    + "\" value=\"\">");
    %> 
      
      <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <%System.out.println("<table>"); %>
          <%
            for (int i = 0; i < SearchResults.size(); i++) {
                XYZBulkEmail xyzBulkEmail = (XYZBulkEmail) SearchResults.get(i);
               
        %>
        
        <TR>
            <%System.out.println("<tr>"); %>
            <td class="<% out.print(InterfaceKey.STYLE[i % 2]);%>">
                <%System.out.println("<td>"); %>
                 
                        <%
                                    
                                    //out.println("<tbody>");

                                    ////////////////////

                                    out.println("<tr>");
                                    System.out.println("<tr>");
                                    System.out.println("<td>");
                                    out.println("<td class=" + InterfaceKey.STYLE[i % 2] + ">"
                                            + xyzBulkEmail.getSenderEmail()
                                            + "</td>");
                                    System.out.println("</td>");
                                    System.out.println("<td>");
                                    out.println("<td  class=" + InterfaceKey.STYLE[i % 2] + ">"
                                            + xyzBulkEmail.getSubject()
                                            + "</td>");
                                    System.out.println("</td>");
                                    System.out.println("<td>");
                                    out.println("<td  nowrap class=" + InterfaceKey.STYLE[i % 2] + ">"
                                            + xyzBulkEmail.getCreationDate()
                                            + "</td>");
                                    System.out.println("</td>");
                                    System.out.println("<td>");
                                    out.println("<td  nowrap class=" + InterfaceKey.STYLE[i % 2] + ">"
                                            + xyzBulkEmail.getPosCode()
                                            + "</td>");
                                    System.out.println("</td>");
                                    System.out.println("<td>");
                                    out.println("<td  nowrap class=" + InterfaceKey.STYLE[i % 2] + ">"
                                            
                                            + "</td>");
                                    System.out.println("</td>");

                               

                                    out.println("</tr>");
                                    System.out.println("</tr>");
                                   

                                        }
                                    }
                                    
                                    //////////////////////////////////////////   
                              

                        %>

                    </table>
                   <%     System.out.println("</table>"); %>

    </body>
</html>
