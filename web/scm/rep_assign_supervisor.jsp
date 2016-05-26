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
            String formName = "repAssignSupervisorForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector<DCMUserModel> supervisors=new Vector();

            String repId=(String)dataHashMap.get(SCMInterfaceKey.DCM_USER_ID);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            supervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS);

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
                supId=document.<%=formName%>.<%=SCMInterfaceKey.SUP_ID%>.value

            if(supId==""){
                alert("Please, choose supervisor.")
                return;
            }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_REP_TO_SUP%>";
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
            <h2>Assign Supervisor to Rep</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=repId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=userLevelTypeId%>">


                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="40%" border="1">

                    <tr class=TableTextNote>
                        <td align=center>Supervisor</td>
                    </tr>

                    <tr class="TableTextNote">
                        <td align="center">
                             <select name="<%=SCMInterfaceKey.SUP_ID%>">
                                <option value="">-----</option>
                            <%
                                        if(supervisors!=null && supervisors.size()!=0){
                                        for (int i = 0; i < supervisors.size(); i++) {
                                            DCMUserModel supervisor= (DCMUserModel) supervisors.get(i);
                            %>
                            <option value="<%=supervisor.getDcmUserId()%>"><%=supervisor.getUserFullName()%></option>
                            <%
                                        }
                                        }
                            %>
                             </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" ><input type="button" class="button" value="Assign Supervisor" onclick="submitAssignForm();">&nbsp;<input type="button" class="button" value="Back" onclick="doBack();"></td>
                    </tr>
                </table>
            </form>
    </div>

    </body>
</html>
