<%-- 
    Document   : import_iqrar_recieving
    Created on : Oct 25, 2010, 9:26:53 AM
    Author     : AHMED SAFWAT
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String appName = request.getContextPath();
            String uploadFormName = "ByExcelForm";
            String oneByOneFormName="OneByOneForm";
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.IQRAR_RECIEVING_USER_CONFIRMATION_MESSAGE);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>IMPORT IQRAR RECIEVING</title>

        <script language="JavaScript">
            function showSelectedMethodForm(selectBox){
                
                var selectedIndex=selectBox.selectedIndex;
                var selectedValue=selectBox.options[selectedIndex].value;

                if(selectedValue=="OneByOne")
                {
                    document.getElementById("OneByOne").style.display="";
                    document.getElementById("ByExcel").style.display="none";

                }else if(selectedValue=="ByExcel"){

                    document.getElementById("OneByOne").style.display="none";
                    document.getElementById("ByExcel").style.display="";
                    document.getElementById("confMessage").style.display="none";

                }else if(selectedValue=="")
                {
                    document.getElementById("confMessage").style.display="none";
                    document.getElementById("OneByOne").style.display="none";
                    document.getElementById("ByExcel").style.display="none";
                }

            }
            function submitOneByOneForm(){

                if(document.<%=oneByOneFormName%>.<%=SCMInterfaceKey.IQRAR_RECIEVING_POS_CODE%>.value=="" ){
                    alert("Please Enter a POS Code");
                    return;
                }
                else{
            document.<%=oneByOneFormName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_IMPORT_SINGLE_IQRAR_RECIEVING%>";
            document.<%=oneByOneFormName%>.submit();
                }
            }

             function submitUploadForm(){

                if(document.<%=uploadFormName%>.importFile.value=="" ){
                    alert("Please Choose a File");
                    return;
                }
                if(document.<%=uploadFormName%>.importFile.value.lastIndexOf(".xls")==-1 && document.<%=uploadFormName%>.importFile.value.lastIndexOf(".xlsx")==-1 ){
                    alert("The Excel File Must be .xls or .xlsx file");
                    return;
                }else{
                    document.<%=uploadFormName%>.action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=<%=SCMInterfaceKey.ACTION_IMPORT_MANY_IQRAR_RECIEVING %>";
                    document.<%=uploadFormName%>.submit();
                }
            }
        </script>

    </head>

    <body>

        <div name="chooseMethod" align="center">
            <br>
            <br>
            <h2>Import Iqrar Recieving</h2>
            <br>
            <br>

            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
                <TR>
                    <td class=TableTextNote nowrap align=center>
                        Type of Request
                    </td>
                    <td align="center">
                        <select name="inputtype" id="inputtype" onchange="showSelectedMethodForm(this);">
                            <option></option>
                            <option value="OneByOne"
                                    <% if(confMessage!=null){
                                    out.print("selected");
                                            }
                                    %>
                                    >One by One</option>
                            <option value="ByExcel">By Excel Sheet</option>
                        </select>
                    </td>
            </TABLE>
            <br>
            <br>
            <br>
            <div name="OneByOne" id="OneByOne" style="<% if(confMessage!=null){
                                            out.print("");
                                            }else{
                                            out.print("display:none");
                                            }
                                    %>">
                <form name="<%=oneByOneFormName%>" id="oneByOneForm" action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post">

                    <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        <tr>
                            <td align="center" class="TableTextNote"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">POS Code</font> </td>

                            <td align="center"> <input type="text" name="<%=SCMInterfaceKey.IQRAR_RECIEVING_POS_CODE%>" id="<%=SCMInterfaceKey.IQRAR_RECIEVING_POS_CODE%>" /><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>

                        </tr>
                        <tr><td colspan="2" align="center"><input class="button"  type="button" name="Submit" value="Save" onclick="submitOneByOneForm();"></td> </tr>
                    </table>
                </form>
             </div>
                            <div id="confMessage">
                                                                           <%
                        
                        if(confMessage!=null)
                            {
                            if(confMessage.contains("Invalid")){
                        %>
                        <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font></div>
                            <%}else{
                        %>      <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font></div>
                        <%
                                }
                            }%>
                            </div>

            <div name="ByExcel" id="ByExcel" style="display: none">

                <form name="<%=uploadFormName%>" method="post" enctype="multipart/form-data">

                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                        <tr>
                            <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:<br>(.xls or .xlsx)</font></td>

                            <td align="center"><input type="file" style="font-size: 11px;font-family: tahoma;line-height: 15px" name="importFile"></td>
                        </tr>

                        <tr>
                            <td colspan="2" align="center"><input class="button" type="button" value="save" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitUploadForm();"></td>

                        </tr>

                    </table>

                </form>

            </div>
        </div>
    </body>
</html>
