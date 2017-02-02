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
            Vector<TeamleaderRepsModel> teamleaderReps=new Vector();
            Vector<TeamleaderSupervisorsModel> teamleaderSupers=new Vector();
            DCMUserDetailModel teamleadDetails=new DCMUserDetailModel();
            String appName = request.getContextPath();
            String formName = "teamleadDetailsForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            teamleaderReps=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_REPS);
            teamleaderSupers=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_SUPERVISORS);
            System.out.println("jsp: teamleaderSupers "+teamleaderSupers.size());
            teamleadDetails=(DCMUserDetailModel)dataHashMap.get(SCMInterfaceKey.REP_TEAMLEAD_DETAILS);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);



%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>Team Leader Details</title>

        <script language="JavaScript">

            function SubmitToAssignRep(userLevelTypeId){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_TEAMLEADER_REP_ASSIGN%>";
            document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
            document.<%=formName%>.submit();

            }
            
            
            function SubmitToAssignSup(userLevelTypeId){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_TEAMLEADER_SUPERVISOR_ASSIGN%>";
            document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
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
            function showSupDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            //to be done now: change to unassign sup from teamlead
            function unAssignSup(supId,userLevelTypeId){
                document.<%=formName%>.<%=SCMInterfaceKey.SUP_ID%>.value=supId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_SUP_FROM_TEAMLEAD%>";
                document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                document.<%=formName%>.submit();
            }
            function unAssignRep(repId,userLevelTypeId){
                document.<%=formName%>.<%=SCMInterfaceKey.REP_ID%>.value=repId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_TO_TEAMLEADER%>";
                document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                document.<%=formName%>.submit();
            }

       </script>
    </head>
    <body>
        <div align="center">
            <%
            if(teamleadDetails!=null){

            %>
            <br>
            <br>
            <h2>Team Leader Details</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
                <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=teamleadDetails.getUserId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=teamleadDetails.getUserLevelTypeId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.REGION_ID%>" value="<%=teamleadDetails.getRegionId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.REP_ID%>" value="-1">
                <input type="hidden" name="<%=SCMInterfaceKey.SUP_ID%>" value="-1">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border=1>


                    <tr class=TableTextNote>
                        <td align="left">Full Name</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=teamleadDetails.getUserFullName()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Address</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=teamleadDetails.getUserAddress()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Email</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=teamleadDetails.getUserEmail()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Mobile No.</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=teamleadDetails.getUserMobile()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Region</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=teamleadDetails.getRegionName()%></font>
                        </td>
                    </tr>

                   <tr class=TableTextNote>
                        <td align="left">Reps</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">

                                <ul>
                                    <%
                                    
                                    if(teamleaderReps!=null&&teamleaderReps.size()!=0){
                                        
                                        for(int i=0;i<teamleaderReps.size();i++){
                                           TeamleaderRepsModel repTeamleader=new TeamleaderRepsModel();
                                            repTeamleader=(TeamleaderRepsModel)teamleaderReps.get(i);
                                    %>
                                    <li><a href="javascript:showRepDetail(<%=repTeamleader.getRepId()%>,3);"><%=repTeamleader.getRepName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignRep(<%=repTeamleader.getRepId()%>,5);">Unassign</a></font></li>
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
                        <td align="left">Supervisor</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">

                                <ul>
                                    <%
                                    
                                    if(teamleaderSupers!=null&&teamleaderSupers.size()!=0){
                                        
                                        for(int i=0;i<teamleaderSupers.size();i++){
                                           TeamleaderSupervisorsModel teamleadSupervisor=new TeamleaderSupervisorsModel();
                                            teamleadSupervisor=(TeamleaderSupervisorsModel)teamleaderSupers.get(i);
                                    
                                    %>
                                    <li><a href="javascript:showSupDetail(<%=teamleadSupervisor.getSupId()%>,4);"><%=teamleadSupervisor.getSupName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignSup(<%=teamleadSupervisor.getSupId()%>,5);">Unassign</a></font></li>
                                    <%
                                    }
                                        %>
                                </ul>
                                    <%
                                    }else{
                                        out.print("No Supervisor Assigned.");
                                        }
                                    %>


                            </font>
                        </td>
                    </tr>
                   
                    
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" name="submitButton" class="button" value="Assign Rep" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignRep(5);">
                            &nbsp;
                            <input type="button" name="submitButton" class="button" value="Assign Supervisor" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignSup(5);">
                            &nbsp;
                            <input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
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
