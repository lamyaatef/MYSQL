<%-- 
    Document   : IqrarPrintingChoice
    Created on : Oct 27, 2010, 11:01:30 AM
    Author     : Salma
--%>

<%@page contentType="text/html" pageEncoding="windows-1256"%>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"
         import="com.mobinil.sds.core.system.request.model.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%

            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String fileFullPath = (String) objDataHashMap.get(SCMInterfaceKey.FILE_PATH);
            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String alert = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
            String file_path = request.getRealPath("downloadItems");
            String imagePath = request.getRealPath("resources");
            String appName = request.getContextPath();
            
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <title>Iqrar Printing Data Entry</title>
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
    <body>
        <br>
        <center>
            <br>
            <h2>Iqrar Printting Data Entry</h2>
            <br>
        </center>
        <br>


        <form id="formIqrarPrint" name="formIqrarPrint"  method=post  action="">

            <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> >
            <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> value="<%=strUserID%>" >
            <input type=hidden name=<%=SCMInterfaceKey.FILE_PATH%> id=<%=SCMInterfaceKey.FILE_PATH%> value="<%=file_path%>" >
            <input type=hidden name=<%=SCMInterfaceKey.PDF_IMAGE_PATH %> id=<%=SCMInterfaceKey.PDF_IMAGE_PATH%> value="<%=imagePath %>" >
            <input type=hidden name=<%=AdministrationInterfaceKey.QUERY_STRING_TABLES%> value="47" >
            <input type=hidden name=<%=AdministrationInterfaceKey.QUERY_STRING_OPERATION%> id=<%=AdministrationInterfaceKey.QUERY_STRING_OPERATION%> value="INSERT" >

            <center>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr class=TableTextNote>
                        <td>
                          Request Type :
                        </td>
                        <td align="left">
                              <select name="datachoice" id="datachoice">
                                  <option value="1">Iqrar For One POS</option>
                                  <option value="2">Iqrar For More Than One POS</option>
                              </select>
                        </td>
                    </tr>
                   
                </table>

                <BR>

                <input class=button  type="button"  value="OK" onclick="showDiv()">
                <BR>
                <BR>



                <div name="onePosDiv" id="onePosDiv" style="visibility:hidden">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        <tr class=TableTextNote>
                            <td>
                                POS Code :
                            </td>
                            <td align="left">
                                <input type="text" name="<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>" id="<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>">
                            </td>
                        </tr>
                    </table>
                    <br>
                    <input class=button  type="button"  value="Submit" onclick="saveOneIqrarRequest()">
                </div>
                

            </center>
        </form>

     <div name="downloadDiv" id="downloadDiv">

        <%
           if (alert == "2") {
        %>

        <center>
            Iqrar Created Successfully ....
        </center>
        <br>
        <center>
            <input type=hidden name=<%=InterfaceKey.EXPORT_FILE_PATH%> id=<%=InterfaceKey.EXPORT_FILE_PATH%> value="<%=fileFullPath%>" >

            <%
                                objDataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, fileFullPath);
                                
                                objDataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "downloadItems//");

                                session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, objDataHashMap);
                                out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
            %>
            <%
                                out.println("<input class=\"button\" id=\"download\" type=\"submit\" name=\"Submit\" value=\"Download Iqrar\" onclick=\"Download();\">");

                                out.println("</form>");
            %>

        </center>

        <%
           } 
            else if (alert != "2" && alert != "")
           {
        %>

        <center>
            <%= alert%>
        </center>


        <%
           }
        %>

     </div>

    </body>
</html>

<script>
    function showDiv()
    {
         document.getElementById("downloadDiv").style.visibility = 'hidden';

        if(document.getElementById("datachoice").value == '1')
        {
            document.getElementById("onePosDiv").style.visibility = 'visible';
           
        }
        else if(document.getElementById("datachoice").value == '2')
        {
            
           document.formIqrarPrint.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_MANY_POS_IQRAR_VIEW%>';
           formIqrarPrint.submit();
        }
    }

    function saveOneIqrarRequest()
    {
        document.getElementById("downloadDiv").style.visibility = 'visible';
        document.formIqrarPrint.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_ONE_POS_IQRAR_PRINT%>';
        formIqrarPrint.submit();
    }


 function showManyIqrarRequest()
    {
        document.formIqrarPrint.<%=InterfaceKey.HASHMAP_KEY_ACTION%> = '<%=SCMInterfaceKey.ACTION_SEARCH_POS_DATA_MANAGEMENT%>';
        formIqrarPrint.submit();
    }


function Download()
{
    
    document.getElementById("download").disabled=true;
    document.GenerateSheet.submit();

}


</script>