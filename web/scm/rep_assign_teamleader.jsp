<%-- 
    Document   : rep_assign_supervisor
    Created on : Nov 9, 2010, 11:05:36 AM
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
            String appName = request.getContextPath();
            String formName = "repAssignTeamleaderForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector<DCMUserModel> teamleaders=new Vector();

            String repId=(String)dataHashMap.get(SCMInterfaceKey.DCM_USER_ID);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            teamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS);

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
                teamleadId=document.<%=formName%>.<%=SCMInterfaceKey.TEAMLEAD_ID%>.value

            if(teamleadId==""){
                alert("Please, choose teamleader.")
                return;
            }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_REP_TO_TEAMLEAD%>";
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
            <h2>Assign Team Leader to Rep</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=repId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=userLevelTypeId%>">


                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="40%" border="1">

                    <tr class=TableTextNote>
                        <td align=center>Team Leader</td>
                    </tr>

                    <tr class="TableTextNote">
                        <td align="center">
                             <select name="<%=SCMInterfaceKey.TEAMLEAD_ID%>">
                                <option value="">-----</option>
                            <%
                                        if(teamleaders!=null && teamleaders.size()!=0){
                                        for (int i = 0; i < teamleaders.size(); i++) {
                                            DCMUserModel teamleader= (DCMUserModel) teamleaders.get(i);
                            %>
                            <option value="<%=teamleader.getDcmUserId()%>"><%=teamleader.getUserFullName()%></option>
                            <%
                                        }
                                        }
                            %>
                             </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" ><input type="button" class="button" value="Assign Teamleader" onclick="submitAssignForm();">&nbsp;<input type="button" class="button" value="Back" onclick="doBack();"></td>
                    </tr>
                </table>
            </form>
    </div>

    </body>
</html>
