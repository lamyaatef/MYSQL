<%-- 
    Document   : rep_assign_group
    Created on : Nov 8, 2010, 10:52:35 AM
    Author     : AHMED SAFWAT
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="com.mobinil.sds.core.system.dcm.user.model.*"
        import="com.mobinil.sds.core.system.scm.model.DCMUserDetailModel"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            Vector<GroupTypeModel> groupTypes=new Vector();
            Vector<POSGroupModel> posGroups=new Vector();
            String appName = request.getContextPath();
            String formName = "repAssignGroupForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            groupTypes=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_POS_GROUP_TYPES);
            posGroups=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_POS_GROUPS_BY_GROUP_TYPE);
            String repId=(String)dataHashMap.get(SCMInterfaceKey.DCM_USER_ID);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            String posGroupTypeId=(String)dataHashMap.get(SCMInterfaceKey.POS_GROUP_TYPE_ID);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);




%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>Rep Details</title>

        <script language="JavaScript">

            function submitAssignForm(){
                groupId=document.<%=formName%>.<%=SCMInterfaceKey.POS_GROUP_ID%>.value
                groupTypeId=document.<%=formName%>.<%=SCMInterfaceKey.POS_GROUP_TYPE_ID%>.value

            if(groupTypeId==""){
                alert("Please, choose group type.");
                return;
            }

            if(groupId==""){
                alert("Please, choose group.");
                return;
            }

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_REP_POS_GROUP%>";
            document.<%=formName%>.submit();

            }

            function getGroupsByGroupTyep(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_REP_POS_GROUP%>";
            document.<%=formName%>.submit();
            }

            function doBack(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
            document.<%=formName%>.submit();

            }

       </script>
    </head>
    <body>
    <div align="center">
            <br>
            <br>
            <h2>Assign POS Group to Rep</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=repId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=userLevelTypeId%>">


                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="40%" border="1">

                    <tr class=TableTextNote>
                        <td nowrap align=center >Group Type</td>
                    </tr>

                    <tr>
                        <td align="center">

                            <select name="<%=SCMInterfaceKey.POS_GROUP_TYPE_ID%>" onchange="getGroupsByGroupTyep();">
                                <option value="">-----</option>
                            <%
                                        if(groupTypes!=null && groupTypes.size()!=0){
                                        for (int i = 0; i < groupTypes.size(); i++) {
                                            GroupTypeModel groupType = (GroupTypeModel) groupTypes.get(i);
                            %>
                            <option value="<%=groupType.getGroupTypeId()%>"
                                    <%
                                    if(posGroupTypeId!=null){
                                    if(posGroupTypeId.equalsIgnoreCase(groupType.getGroupTypeId()))
                                        out.print("selected");
                                    }
                                    %>
                                    ><%=groupType.getGroupTypeName()%></option>
                            <%
                                        }
                                        }
                            %>
                            </select>

                        </td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align=center>POS Group</td>
                    </tr>

                    <tr class="TableTextNote">
                        <td align="center">
                             <select name="<%=SCMInterfaceKey.POS_GROUP_ID%>">
                                <option value="">-----</option>
                            <%
                                        if(posGroups!=null && posGroups.size()!=0){
                                        for (int i = 0; i < posGroups.size(); i++) {
                                            POSGroupModel posGroup = (POSGroupModel) posGroups.get(i);
                            %>
                            <option value="<%=posGroup.getGroupId()%>"><%=posGroup.getGroupName()%></option>
                            <%
                                        }
                                        }
                            %>
                             </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" ><input type="button" class="button" value="Assign Group" onclick="submitAssignForm();">&nbsp;<input type="button" class="button" value="Back" onclick="doBack();"></td>
                    </tr>
                </table>
            </form>
    </div>

    </body>
</html>
