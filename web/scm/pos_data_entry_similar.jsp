<%-- 
    Document   : pos_data_entry_similar
    Created on : Oct 24, 2010, 2:48:50 PM
    Author     : Salma
--%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"
         import="com.mobinil.sds.core.system.scm.model.POSSimilar"
         import="com.mobinil.sds.core.system.request.model.SupervisorModel"
%>

<html>
    <head>
        <head>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
        <title>POS Pre-Request Data Entry</title>
    </head>
<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String tableTitle = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_TABLE_TITLE);
Vector<POSSimilar> similarList = (Vector) objDataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
System.out.println("################### similarList " + similarList.size());
%>
    <body>
        <br>
        <center><h3>POS Data Entry (<%= tableTitle %>) </h3></center>
        <br>
        <center>
        <table align=center style=\"BORDER-COLLAPSE: collapse cellSpacing=2 cellPadding=1 width=\"40%\" border=1>

       <tr class=TableTextNote>
           <td class=TableHeader nowrap align=center >POS Code</td>
           <td class=TableHeader nowrap align=center >POS Name</td>
           <td class=TableHeader nowrap align=center >POS Address</td>
       </tr>
        <%
            if (similarList!=null && !similarList.isEmpty())
           for(POSSimilar posSimilar : similarList)
           {
               if (posSimilar!=null){
               %>
               <tr>
                   <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%= posSimilar.getPOSCode()%></td>
                   <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">&nbsp;<%= (posSimilar.getPOSName()==null ? "" : posSimilar.getPOSName()) %></td>
               <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%= posSimilar.getPOSAddress()%></td>
               </tr>
               <%
           }}
        %>
         </table>
         <br>
         <br>
           <input class=button  name="backButton" id="backButton" value="Back" type=button onclick="history.go(-1);return false;" />
        </center>
    </body>
</html>
