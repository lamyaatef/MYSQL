<%-- 
    Document   : supervisor_assign_rep
    Created on : Nov 9, 2010, 3:11:20 PM
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
            Vector<DCMUserModel> reps=new Vector();

            String supId=(String)dataHashMap.get(SCMInterfaceKey.DCM_USER_ID);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            reps=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_REPS);
            String regionId=(String)dataHashMap.get(SCMInterfaceKey.REGION_ID);
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
                supId=document.<%=formName%>.<%=SCMInterfaceKey.REP_ID%>.value

            if(supId==""){
                alert("Please, choose rep.")
                return;
            }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ASSIGN_REP_TO_SUPERVISOR%>";
            document.<%=formName%>.submit();

            }

            function doBack(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
            document.<%=formName%>.submit();

            }

       </script>
    </head>
    <body>
    <div align="center">
            <br>
            <br>
            <h2>Assign Rep to Supervisor</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=supId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=userLevelTypeId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.REGION_ID%>" value="<%=regionId%>">


                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="40%" border="1">

                    <tr class=TableTextNote>
                        <td align=center>Rep</td>
                    </tr>

                    <tr class="TableTextNote">
                        <td align="center">
                             <select name="<%=SCMInterfaceKey.REP_ID%>">
                                <option value="">-----</option>
                            <%
                                        if(reps!=null && reps.size()!=0){
                                        for (int i = 0; i < reps.size(); i++) {
                                            DCMUserModel rep= (DCMUserModel) reps.get(i);
                            %>
                            <option value="<%=rep.getDcmUserId()%>"><%=rep.getUserFullName()%></option>
                            <%
                                        }
                                        }
                            %>
                             </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" ><input type="button" class="button" value="Assign Rep" onclick="submitAssignForm();">&nbsp;<input type="button" class="button" value="Back" onclick="doBack();"></td>
                    </tr>
                </table>
            </form>
    </div>
        <div id="confMessage" align="center">
                    <%

                                if (confMessage != null) {
                                    if (confMessage.contains("Invalid")) {
                    %>
                    <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font></div>
                    <%} else {
                    %>      <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font></div>
                    <%
                                    }
                                }%>
                </div>
    </body>
</html>