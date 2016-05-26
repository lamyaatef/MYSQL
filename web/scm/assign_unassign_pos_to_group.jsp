<%-- 
    Document   : assign_unassign_pos_to_group
    Created on : Nov 1, 2010, 10:41:33 AM
    Author     : AHMED SAFWAT
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
	import="com.mobinil.sds.core.system.scm.dao.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            int groupId=(Integer)dataHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID);
            String groupName=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME);
            Vector<POSModel> groupAssignedPOSs=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_GROUP_ASSIGNED_POS);
            String appName = request.getContextPath();
            String formName = "posAssign";
            String uploadFormName = "ByExcelForm";
            String oneByOneFormName="OneByOneForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);
            String Slach=System.getProperty("file.separator");
            String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>POS Groups Management</title>

        <script language="JavaScript">

             function submitUploadForm(){

                if(document.<%=uploadFormName%>.importFile.value=="" ){
                    alert("Please Choose a File");
                    return;
                }
                if(document.<%=uploadFormName%>.importFile.value.lastIndexOf(".xls")==-1 && document.<%=uploadFormName%>.importFile.value.lastIndexOf(".xlsx")==-1 ){
                    alert("The Excel File Must be .xls or .xlsx file");
                    return;
                }else{
                    document.<%=uploadFormName%>.action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=<%=SCMInterfaceKey.ACTION_ASSIGN_MANY_POS_TO_GROUP%>&<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>=<%=groupId%>&<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>=<%=groupName%>";
                    document.<%=uploadFormName%>.submit();
                }
            }


            function unAssign(posId){
                        document.<%=formName%>.action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=<%=SCMInterfaceKey.ACTION_UNASIGN_POS_FROM_GROUP%>";
                        document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_POS_ID%>.value=posId;
                        document.<%=formName%>.submit();
            }
            function showAssignNewPOS(){

                if(document.getElementById("assignNewPOS").style.display=="none"){
                            document.getElementById("assignNewPOS").style.display="";
                }
                else if(document.getElementById("assignNewPOS").style.display==""){
                            document.getElementById("assignNewPOS").style.display="none";
                }
                document.getElementById("confMessage").style.display="none";
                document.getElementById("OneByOne").style.display="none";
                document.getElementById("ByExcel").style.display="none";
                document.getElementById("confMessage").style.display="none";

            }

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

                if(document.<%=oneByOneFormName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_POS_ID%>.value=="" ){
                    alert("Please Enter a POS Code");
                    return;
                }
                else{
            document.<%=oneByOneFormName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_POS_TO_GROUP%>";
            document.<%=oneByOneFormName%>.submit();
                }
            }

        function doBack(){
            document.<%=formName%>.action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=<%=SCMInterfaceKey.ACTION_VIEW_POS_GROUPS%>";
            document.<%=formName%>.submit();
      }

        function Sheet()
        {
            document.getElementById("download").disabled=true;
            document.<%=formName%>.action="com.mobinil.sds.web.controller.UploadingFileServlet";
            document.<%=formName%>.submit();


        }

</script>

    </head>

    <body>

        <div align="center">
            <br>
            <br>
            <h2>Assign to Group:<%=groupName%></h2>
            <br>
            <br>




        <form action="" name="<%=formName%>" method="post">


            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_POS_ID%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="<%=groupId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>" value="<%=groupName%>">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">

            <%
       if(groupAssignedPOSs!=null&&groupAssignedPOSs.size()!=0){
                                                %>
                                                
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr>
                        <td class=TableHeader nowrap align=center >POS Code</td>
                        <td  class=TableHeader nowrap align=center>POS Name</td>
                        <td  class=TableHeader nowrap align=center>Delete</td>
                    </tr>


                    <%

                                for (int i = 0; i < groupAssignedPOSs.size(); i++) {
                                    POSModel posAssignedToGroup = (POSModel) groupAssignedPOSs.get(i);
                    %>


                    <tr>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posAssignedToGroup.getPOS_Code()%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posAssignedToGroup.getPOS_NAME()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Delete" onclick="unAssign(<%=posAssignedToGroup.getPOS_ID()%>);"></td>
                    </tr>

                    <%
                                }

                    %>
                    <tr>
                    <td colspan="3" align="center"><input class="button"  type="submit" name="Submit" id="download" value="Download Excel Sheet" onclick="Sheet();"/>                <input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>

                    </tr>

        </table>

                <%}else{
                    out.print("No Assigned POS. \n");
                    %>
                    <br>
                    <div align="center"><input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></div>
                    <%
                }

                %>


                <br>
                <a  style="font-size: 11px;font-family: tahoma;line-height: 15px" href="javascript:showAssignNewPOS();" >Assign New POS</a>
                </form>
                <br>


                <%
                            if(groupAssignedPOSs!=null&&groupAssignedPOSs.size()!=0){

                            String distLink=PoiWriteExcelFile.exportPOSGroupPOSs(groupAssignedPOSs,baseDirectory);

                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,distLink);

                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"scm"+Slach+"upload"+Slach);

                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);

}
%>


            <br>

            <div id="assignNewPOS" style="<% if(confMessage!=null){
                                            out.print("");
                                            }else{
                                            out.print("display:none");
                                            }
                                    %>">

            <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
                <tr>
                    <td class=TableTextNote nowrap align=center>
                        Type of Assign
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
                </tr>
            </table>
            </div>



            <br>    
            <div name="OneByOne" id="OneByOne" style="<% if(confMessage!=null){
                                            out.print("");
                                            }else{
                                            out.print("display:none");
                                            }
                                    %>">
                <form name="<%=oneByOneFormName%>" id="oneByOneForm" action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post">

                    <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                    <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="<%=groupId%>">
                    <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME %>" value="<%=groupName%>">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        <tr>
                            <td align="center" class="TableTextNote"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">POS Code</font> </td>

                            <td align="center"> <input type="text" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_POS_ID%>" id="<%=SCMInterfaceKey.CONTROL_HIDDEN_POS_ID%>" /><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>

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
                    <font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font>
                        <%}else{
                    %>      <font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font>
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
