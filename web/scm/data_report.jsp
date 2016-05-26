<%-- 
    Document   : data_report
    Created on : Jan 18, 2012, 1:31:37 PM
    Author     : mabdelaal
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"
           import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"              
               import="java.util.*"%>
<%
            String appName = request.getContextPath();            
            String action=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_PARAM_REPORT_NEXT;
%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css" />
        <title>Data Report</title>
    </head>
    <body>
        <center>
        <br>
        <h2>Data Report</h2>
        <br>
        <form name="datarepform" id="datarepform" action="<%=action%>" method="post">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
                <tr>
                    <td class=TableHeader nowrap align=center style="width: 30%" ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Report Parameter</font></td>             
            <td class=TableHeader nowrap align=center style="width: 70%"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Parameters</font></td>             
                </tr>
                <tr>
                    <td class=TableHeader nowrap align=center style="width: 30%" ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Select</font></td>             
            <td class=TableHeader nowrap align=center style="width: 70%">
                <select name="<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>" id="<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>" >                    
                    <option label="POS" value="1" />POS</option>
                    <option label="Rep" value="3" />Rep</option>
                    <option label="SuperVisor" value="4" />SuperVisor</option>
                </select>
            </td>             
                </tr>
        </table>
        <input id="submitButn" name="submitButn" type="submit" value="Next" />
        </form>
        </center>
    </body>
</html>
