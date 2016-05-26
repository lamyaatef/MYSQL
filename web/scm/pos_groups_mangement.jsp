<%-- 
    Document   : pos_groups_mangement
    Created on : Oct 31, 2010, 9:55:03 AM
    Author     : AHMED SAFWAT
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            Vector<POSGroupModel> posGroups=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_POS_GROUPS);
            String appName = request.getContextPath();
            String formName = "posGroupManagement";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String distinationPage=(String)dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
            String totalPageNumbers=(String)dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>

        <title>POS Groups Management</title>

        <script language="JavaScript">
            function submitEditForm(groupId){
            document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>.value=groupId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_EDIT_POS_GROUP%>";
            document.<%=formName%>.submit();

            }

            function viewAssign(groupId,groupName){
            document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>.value=groupId;
            document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>.value=groupName;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_UNASSIGN_POS_TO_GROUP%>";
            document.<%=formName%>.submit();
            }
            function submitNewForm(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_NEW_POS_GROUP%>";
            document.<%=formName%>.submit();

            }
            function DevChangePageActionWithSubmit(action){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value=action;
            document.<%=formName%>.submit();
               
            }
            function confirmDelete(groupId){
                if(confirm('Are you sure you want to delete the group?'))
                    {
                        document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>.value=groupId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_DELETE_GROUP%>";
                        document.<%=formName%>.submit();
                    }
                else
                return;
            }

       </script>

    </head>

    <body>

                <div align="center">
            <br>
            <br>
            <h2>POS Group Management</h2>
            <br>
            <br>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
           <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            
            <%
       if(posGroups!=null&&posGroups.size()!=0){
                                                %>


            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_TEXT_GROUP_NAME%>" value="-1">



           <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr>
                        <td class=TableHeader nowrap align=center >Group ID</td>
                        <td  class=TableHeader nowrap align=center>Group Name</td>
                        <td class=TableHeader nowrap align=center>Description</td>
                        <td class=TableHeader nowrap align=center>No Of POS</td>
                        <td class=TableHeader nowrap align=center>Group Type</td>
                        <td class=TableHeader nowrap align=center>Creation Date</td>
                        <td class=TableHeader nowrap align=center>Created By</td>
                        <td class=TableHeader nowrap align=center>Edit</td>
                        <td class=TableHeader nowrap align=center>Delete</td>
                        <td class=TableHeader nowrap align=center>Assign / UnAssign</td>
                    </tr>


                    <%

                                for (int i = 0; i < posGroups.size(); i++) {
                                    POSGroupModel posGroup = (POSGroupModel) posGroups.get(i);
                    %>


                    <tr>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getGroupId() %></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getGroupName() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getDescription() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getNoOfPOSs()==-1?0:posGroup.getNoOfPOSs() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getGroupTypeName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getCreationDate().getDate()%>/<%=posGroup.getCreationDate().getMonth()+1 %>/<%=posGroup.getCreationDate().getYear()+1900 %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getCreatedBy() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Edit" onclick="submitEditForm(<%=posGroup.getGroupId() %>);"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Delete" onclick="confirmDelete(<%=posGroup.getGroupId() %>);"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Assign / UnAssign" onclick="viewAssign(<%=posGroup.getGroupId() %>,'<%=posGroup.getGroupName()%>');"></td>
                    </tr>

                    <%
                                }

                    %>

                </table>
           

 <jsp:include page="pagingTable.jsp"  flush="true">
                                      <jsp:param   name="action_name_when_click_enter" value="<%=SCMInterfaceKey.ACTION_VIEW_POS_GROUPS%>"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


</jsp:include>
                <%}else{
                    out.print("No POS Groups.");
                }

                %>
                <input type="button" class="button" value="Add New Group" onclick="submitNewForm();">
                </form>
                </div>
    </body>
</html>
