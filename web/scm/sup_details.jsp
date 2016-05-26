<%--
    Document   : rep_details
    Created on : Nov 7, 2010, 10:23:44 AM
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
            Vector<RepSupervisorModel> supervisorReps=new Vector();
            DCMUserDetailModel supDetails=new DCMUserDetailModel();
            String appName = request.getContextPath();
            String formName = "supDetailsForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            supervisorReps=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_SUP_REPS);
            supDetails=(DCMUserDetailModel)dataHashMap.get(SCMInterfaceKey.REP_SUP_DETAILS);
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

            function SubmitToAssignRep(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SUPERVISOR_REP_ASSIGN%>";
            document.<%=formName%>.submit();

            }

            function doBack(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_MANAGEMENT%>";
            document.<%=formName%>.submit();

            }

            function showRepDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            function unAssignRep(repId){
                document.<%=formName%>.<%=SCMInterfaceKey.REP_ID%>.value=repId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_TO_SUPERVISOR%>";
                document.<%=formName%>.submit();
            }

       </script>
    </head>
    <body>
        <div align="center">
            <%
            if(supDetails!=null){

            %>
            <br>
            <br>
            <h2>Supervisor Details</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
                <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=supDetails.getUserId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=supDetails.getUserLevelTypeId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.REGION_ID%>" value="<%=supDetails.getRegionId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.REP_ID%>" value="-1">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border=1>


                    <tr class=TableTextNote>
                        <td align="left">Full Name</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserFullName()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Address</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserAddress()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Email</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserEmail()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Mobile No.</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserMobile()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Region</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getRegionName()%></font>
                        </td>
                    </tr>

                   <tr class=TableTextNote>
                        <td align="left">Reps</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">

                                <ul>
                                    <%
                                    if(supervisorReps!=null&&supervisorReps.size()!=0){
                                        for(int i=0;i<supervisorReps.size();i++){
                                           RepSupervisorModel repSupervisor=new RepSupervisorModel();
                                            repSupervisor=(RepSupervisorModel)supervisorReps.get(i);
                                    %>
                                    <li><a href="javascript:showRepDetail(<%=repSupervisor.getRepId()%>,3);"><%=repSupervisor.getSupName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignRep(<%=repSupervisor.getRepId()%>);">Unassign</a></font></li>
                                    <%
                                    }
                                        %>
                                </ul>
                                    <%
                                    }else{
                                        out.print("No Reps Assigned.");
                                        }
                                    %>


                            </font>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="button" name="submitButton" class="button" value="Assign Rep" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignRep();">&nbsp;<input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
                    </tr>
                </table>

            </form>
  <%
    }
    else{
    out.print("No Data Found.");
    }
    %>
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
