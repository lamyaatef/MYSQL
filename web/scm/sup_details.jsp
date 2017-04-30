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
            Vector<SupervisorRepsModel> supervisorReps=new Vector();
            Vector<SupervisorTeamleadersModel> supervisorTeamleaders=new Vector();
            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            DCMUserDetailModel supDetails=new DCMUserDetailModel();
            //Vector<DCMUserDetailModel> supDetails=new Vector<DCMUserDetailModel>();
            String appName = request.getContextPath();
            String formName = "supDetailsForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            supervisorReps=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_SUP_REPS);
            supervisorTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_SUP_TEAMLEADERS);
            supDetails=(DCMUserDetailModel)dataHashMap.get(SCMInterfaceKey.REP_SUP_DETAILS);
            //supDetails=(Vector<DCMUserDetailModel>)dataHashMap.get(SCMInterfaceKey.REP_SUP_DETAILS);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);
            repTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS);


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>Supervisor Details</title>

        <script language="JavaScript">

            function SubmitToAssignRep(userLevelTypeId){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SUPERVISOR_REP_ASSIGN%>";
            document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
            document.<%=formName%>.submit();

            }
            function SubmitToAssignTeamlead(userLevelTypeId){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SUPERVISOR_TEAMLEAD_ASSIGN%>";
            document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
            document.<%=formName%>.submit();

            }

            function doBack(/*userLevelTypeId*/){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_MANAGEMENT%>";
            //document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
            document.<%=formName%>.submit();

            }

            function showTeamleadDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_TEAMLEAD_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            function showRepDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            function unAssignRep(repId,userLevelTypeId){
                document.<%=formName%>.<%=SCMInterfaceKey.REP_ID%>.value=repId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_TO_SUPERVISOR%>";
                document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                document.<%=formName%>.submit();
            }
            function submitToAssignTeamleader(userLevelTypeId){
                if(<%=repTeamleaders==null?0:repTeamleaders.size()%><2){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_REP_TEAMLEAD_ASSIGN%>";
                    document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                    document.<%=formName%>.submit();
                }
                else{
                    alert("Rep already has 2 teamleaders.");
                }

            }
            function unAssignTeamlead(teamleadId,userLevelTypeId){
               document.<%=formName%>.<%=SCMInterfaceKey.TEAMLEAD_ID%>.value=teamleadId;
               //document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_TEAMLEAD%>";
               document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_TEAMLEAD_TO_SUPERVISOR%>";//ACTION_UNASSIGN_SUP_FROM_TEAMLEAD 
               document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
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
                <input type="hidden" name="<%=SCMInterfaceKey.TEAMLEAD_ID%>" value="-1">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border=1>


                    <tr class=TableTextNote>
                        <td align="left">Full Name</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserFullName()%></font></td>
                    </tr>
                    <%--
                    <tr class=TableTextNote>
                        <td align="left">Address</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserAddress()%></font></td>
                    </tr>
                    --%>
                    <tr class=TableTextNote>
                        <td align="left">Email</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserEmail()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Mobile No.</td>
                        <td align="left">
                            <%if (supDetails.getUserMobile()==null || (supDetails.getUserMobile()!=null && supDetails.getUserMobile().compareToIgnoreCase("null")==0 ))
                            {
                            %>
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">None</font>
                            <%
                            } else{
                            %>
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.getUserMobile()%></font>
                            <%
                                    }
                            %>
                        </td>
                    </tr>
                    <%--
                    <tr class=TableTextNote>
                        <td align="left">Regions</td>
                        <td align="left">
                            <% if (supDetails!=null && supDetails.size()!=0){
                                for(int i=0;i<supDetails.size();i++){
                             
                            %>
                            <b><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=i%>-</font></b>&nbsp;<font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=supDetails.get(i).getRegionName()%></font><br>
                            <%
                               }
                            }
                            %>
                        </td>
                    </tr>
--%>
                   <tr class=TableTextNote>
                        <td align="left">Reps</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">

                                <ul>
                                    <%
                                    if(supervisorReps!=null&&supervisorReps.size()!=0){
                                        for(int i=0;i<supervisorReps.size();i++){
                                           SupervisorRepsModel supervisorRep=new SupervisorRepsModel();
                                            supervisorRep=(SupervisorRepsModel)supervisorReps.get(i);
                                    %>
                                    <li><a href="javascript:showRepDetail(<%=supervisorRep.getRepId()%>,6);"><%=supervisorRep.getRepName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignRep(<%=supervisorRep.getRepId()%>,4);">Unassign</a></font></li>
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
                    
                    <tr class=TableTextNote>
                        <td align="left">Team Leaders</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">


                                    <%
                                    if(supervisorTeamleaders!=null&&supervisorTeamleaders.size()!=0){
                                        for(int i=0;i<supervisorTeamleaders.size();i++){
                                           SupervisorTeamleadersModel supervisorTeamlead=new SupervisorTeamleadersModel();
                                            supervisorTeamlead=(SupervisorTeamleadersModel)supervisorTeamleaders.get(i);
                                    %>
                                    <b>-</b>&nbsp;<a href="javascript:showTeamleadDetail(<%=supervisorTeamlead.getTeamleadId()%>,5);"><%=supervisorTeamlead.getTeamleadName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignTeamlead(<%=supervisorTeamlead.getTeamleadId()%>,4);">Unassign</a></font><br>

                                    <%
                                    }
                                        %>


                                        
                                    <%
                                    }else{
                                        %>
                                        
                                        <b>No Team Leaders Assigned.</b>

                                    <%
                                        }
                                        %>


                            </font>
                        </td>
                    </tr>
                    <tr>
                        
                        <td colspan="2" align="center">
                            <input type="button" name="submitButton" class="button" value="Assign Teamleader" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignTeamlead(5);">
                            &nbsp;
                            <input type="button" name="submitButton" class="button" value="Assign Rep" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignRep(6);">
                            &nbsp;<input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
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
