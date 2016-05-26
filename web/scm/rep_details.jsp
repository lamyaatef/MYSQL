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
            Vector<RepSupervisorModel> repSupervisors=new Vector();
            RepPOSGroupModel posGroup=new RepPOSGroupModel();
            DCMUserDetailModel repDetails=new DCMUserDetailModel();
            String appName = request.getContextPath();
            String formName = "repDetailsForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            repSupervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS);
            repDetails=(DCMUserDetailModel)dataHashMap.get(SCMInterfaceKey.REP_SUP_DETAILS);
            posGroup=(RepPOSGroupModel)dataHashMap.get(SCMInterfaceKey.REP_POS_GROUP);
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

            function SubmitToAssignGroup(dcmUserId){
            document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
            if(<%=posGroup==null?0:1%><1){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_REP_POS_GROUP%>";
            document.<%=formName%>.submit();
            }
            else{
                alert("Rep already has a group.");
            }

            }

            function submitAddNewForm(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_NEW_REP_SUP%>";
            document.<%=formName%>.submit();
            }
            function submitSearchForm(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
            document.<%=formName%>.submit();

            }

            function doBack(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_MANAGEMENT%>";
            document.<%=formName%>.submit();

            }

            function viewDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        if(userLevelTypeId=="3")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
                        if(userLevelTypeId=="4")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        document.<%=formName%>.submit();
            }

            function unAssignPOSGroup(groupId){
                document.<%=formName%>.<%=SCMInterfaceKey.POS_GROUP_ID%>.value=groupId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_POS_GROUP%>";
                document.<%=formName%>.submit();
            }

            function unAssignSup(supId){
                document.<%=formName%>.<%=SCMInterfaceKey.SUP_ID%>.value=supId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_SUP%>";
                document.<%=formName%>.submit();
            }


            function showSupDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            function submitToAssignSupervisor(){
                if(<%=repSupervisors==null?0:repSupervisors.size()%><2){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_REP_SUP_ASSIGN%>";
                    document.<%=formName%>.submit();
                }
                else{
                    alert("Rep already has 2 supervisors.");
                }

            }

       </script>
    </head>
    <body>
        <div align="center">
            <%
            if(repDetails!=null){

            %>
            <br>
            <br>
            <h2>Rep Details</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
                <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=repDetails.getUserId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="<%=repDetails.getUserLevelTypeId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.REGION_ID%>" value="<%=repDetails.getRegionId()%>">
                <input type="hidden" name="<%=SCMInterfaceKey.POS_GROUP_ID%>" value="-1">
                <input type="hidden" name="<%=SCMInterfaceKey.SUP_ID%>" value="-1">
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="50%" border=1>


                    <tr class=TableTextNote >
                        <td align="left">Full Name</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=repDetails.getUserFullName()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Address</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=repDetails.getUserAddress()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Email</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=repDetails.getUserEmail()%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align="left">Mobile No.</td>
                        <td align="left"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=repDetails.getUserMobile()%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">Region</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=repDetails.getRegionName()%></font>
                        </td>
                    </tr>

                    <tr class=TableTextNote>
                        <td align="left">POS Group</td>
                        <td align="left">
                            
                                

                                    <% if(posGroup!=null){ %>
                                    <b>-</b>&nbsp;<font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posGroup.getGroupName()%></font>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignPOSGroup(<%=posGroup.getGroupId()%>);">Unassign</a></font><br>

                                    <%}else{
                                        %>
                                        <b>No POS Group Assigned.</b>
                                        <%
                                        }
                                    %>


                        </td>
                    </tr>
                                        <tr class=TableTextNote>
                        <td align="left">Supervisors</td>
                        <td align="left">
                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px">


                                    <%
                                    if(repSupervisors!=null&&repSupervisors.size()!=0){
                                        for(int i=0;i<repSupervisors.size();i++){
                                           RepSupervisorModel repSupervisor=new RepSupervisorModel();
                                            repSupervisor=(RepSupervisorModel)repSupervisors.get(i);
                                    %>
                                    <b>-</b>&nbsp;<a href="javascript:showSupDetail(<%=repSupervisor.getSupId()%>,4);"><%=repSupervisor.getSupName()%></a>&nbsp;&nbsp;<font style="font-size: 9px;font-family: tahoma;line-height: 15px"><a href="javascript:unAssignSup(<%=repSupervisor.getSupId()%>);">Unassign</a></font><br>

                                    <%
                                    }
                                        %>


                                        
                                    <%
                                    }else{
                                        %>
                                        
                                        <b>No Supervisors Assigned.</b>

                                    <%
                                        }
                                        %>


                            </font>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center"><input type="button" name="submitButton" class="button" value="Assign Group" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="SubmitToAssignGroup(<%=repDetails.getUserId()%>);">&nbsp;<input type="button" name="submitButton" class="button" value="Assign to Supervisor" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitToAssignSupervisor();">&nbsp;<input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
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
