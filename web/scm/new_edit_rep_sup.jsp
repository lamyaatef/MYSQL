
<%--
    Document   : new_edit_rep_sup
    Created on : Nov 3, 2010, 12:0:47 PM
    Author     : AHMED SAFWAT
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="com.mobinil.sds.core.system.dcm.user.model.DCMUserModel"
        import="com.mobinil.sds.core.system.dcm.region.model.RegionModel"
        import="com.mobinil.sds.core.system.scm.dao.RepManagementDAO"
        import="com.mobinil.sds.core.utilities.Utility"
        import="java.sql.Connection"
        import="java.util.*"
        %>
<%
            //DECLERTION FOR PAGE DATA
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            DCMUserModel dcmUser = (DCMUserModel) dataHashMap.get(SCMInterfaceKey.DCM_USER_MODEL);
            DCMUserDetailModel dcmUserDetail = (DCMUserDetailModel) dataHashMap.get(SCMInterfaceKey.DCM_USER_DETAIL_MODEL);
            System.out.println("dcmUserDetail "+dcmUserDetail);
            Vector<DCMUserLevelTypeModel> repLevels = new Vector();
            Vector<RegionModel> regions = new Vector();
            Vector<RegionModel> regionGovernorates = new Vector();
            Vector<RegionModel> governorateCities = new Vector();
            Vector<RegionModel> cityDistricts = new Vector();
            Vector<RepSupervisorModel> repSupervisors=new Vector();
            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            repSupervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS);
            repTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS);
            
            Vector<DCMUserModel> regionSupervisors=new Vector();
            Vector<DCMUserModel> regionTeamleaders=new Vector();
            regionSupervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS);
            regionTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS);
            
//            Vector<RegionModel> districtAreas = new Vector();





            //BASIC DATA
            Connection con = Utility.getConnection();
            String appName = request.getContextPath();
            String formName = "repOrSupNewEditForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String confMessage = (String) dataHashMap.get(SCMInterfaceKey.CONFIRMATION_MESSAGE);
            regions = RepManagementDAO.getRegions(con);
            repLevels = RepManagementDAO.getUserLevelsForSupervisorAndRep(con);



            //Regions Dyncmic Part
            regionGovernorates = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_REGION_GOVERNORATES);
            governorateCities = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_GOVERNORATE_CITIES);
            cityDistricts = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_CITY_DISTRICTS);
 //           districtAreas = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_DISTRICT_AREAS);


            String governorateId = (String) dataHashMap.get(SCMInterfaceKey.GOVERNORATE_ID);
            String cityId = (String) dataHashMap.get(SCMInterfaceKey.CITY_ID);
            String districtId = (String) dataHashMap.get(SCMInterfaceKey.DISTRICT_ID);
            String supId = (String) dataHashMap.get(SCMInterfaceKey.SUP_ID);
            String teamleadId = (String) dataHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
            System.out.println("supervisor id && teamleader id : "+supId+" "+teamleadId);
            String regionId = (String) dataHashMap.get(SCMInterfaceKey.REGION_ID);
//            String areaId = (String) dataHashMap.get(SCMInterfaceKey.AREA_ID);

            //IF NEW
            String pageHeader = "";
            String buttonValue = "";
            String buttonAction = "";

            String userLevelTypeId = "";
            String dcmUserId = "";
            String dcmUserUserId="";
            String dcmUserName = "";
            String dcmUserAddress = "";
            String dcmUserEmail = "";
            String dcmUserMobile = "";

            if(dcmUser!=null){

            userLevelTypeId = dcmUser.getUserLevelTypeId()==null?"":dcmUser.getUserLevelTypeId();
            dcmUserId = dcmUser.getDcmUserId()==null?"":dcmUser.getDcmUserId();
            dcmUserName = dcmUserDetail.getUserFullName()==null?"":dcmUserDetail.getUserFullName();
            dcmUserAddress = dcmUserDetail.getUserAddress()==null?"":dcmUserDetail.getUserAddress();
            dcmUserEmail = dcmUserDetail.getUserEmail()==null?"":dcmUserDetail.getUserEmail();
            dcmUserMobile = dcmUserDetail.getUserMobile()==null?"":dcmUserDetail.getUserMobile();
            dcmUserUserId =dcmUser.getUserId()==null?"":dcmUser.getUserId();


            if (dcmUserId == null || dcmUserId.trim().equals("") ) {
                //NEW
                pageHeader = "Add New Rep/Supervisor";
                buttonValue = "Add";
                buttonAction = SCMInterfaceKey.ACTION_ADD_NEW_REP_SUP;

            } else {
                //UPDATE
                pageHeader = "Update Rep/Supervisor";
                buttonValue = "Update";
                buttonAction = SCMInterfaceKey.ACTION_UPDATE_REP_SUP;

                regionId = dcmUser.getRegionId();

                userLevelTypeId = dcmUser.getUserLevelTypeId();
        /*
                        if (userLevelTypeId.equalsIgnoreCase("3")&&RepManagementDAO.checkIfRegionIsArea(con, regionId)) {
                            areaId = regionId;

                            districtId = RepManagementDAO.getParentRegionId(con, areaId);
                            districtAreas = RepManagementDAO.getAreas(con, districtId);

                            cityId = RepManagementDAO.getParentRegionId(con, districtId);
                            cityDistricts = RepManagementDAO.getDistricts(con, cityId);

                            governorateId = RepManagementDAO.getParentRegionId(con, cityId);
                            governorateCities = RepManagementDAO.getCities(con, governorateId);

                            regionId = RepManagementDAO.getParentRegionId(con, governorateId);
                            regionGovernorates = RepManagementDAO.getGovernorates(con, regionId);


                        }
        */

                        if (userLevelTypeId.equalsIgnoreCase("3")&&RepManagementDAO.checkIfRegionIsDistrict(con, regionId)) {
                            districtId = regionId;

                            cityId = RepManagementDAO.getParentRegionId(con, districtId);
                            cityDistricts = RepManagementDAO.getDistricts(con, cityId);

                            governorateId = RepManagementDAO.getParentRegionId(con, cityId);
                            governorateCities = RepManagementDAO.getCities(con, governorateId);

                            regionId = RepManagementDAO.getParentRegionId(con, governorateId);
                            regionGovernorates = RepManagementDAO.getGovernorates(con, regionId);


                        }
            }
        }else{
                pageHeader = "Add New Rep/Supervisor";
                buttonValue = "Add";
                buttonAction = SCMInterfaceKey.ACTION_ADD_NEW_REP_SUP;

        }



%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>New/Edit POS Group </title>
        <script>
            function submitForm()
            {
                console.log("msg ",document.<%=formName%>.<%=SCMInterfaceKey.REGION_ID%>.value);
                userName=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_FULL_NAME%>.value;
                userAddress=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ADDRESS%>.value;
                userEmail=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_EMAIL%>.value;
                userMobile=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_MOBILE%>.value;
                userLevelTypeId=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>.value;
                
                reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                if(userName==""){
                    alert("Please enter full name.");
                    return;
                }
                if(userAddress==""){
                    alert("Please enter address.");
                    return;
                }
                if(userEmail==""){
                    alert("Please enter email.");
                    return;
                }
                if(userMobile==""){
                    alert("Please enter mobile no.");
                    return;
                }
                if(reg.test(userEmail) == false) {
                    alert('Invalid email address.');
                    return;
                }
                if(userLevelTypeId==""){
                    alert("Please choose level type.");
                    return;
                }
                if(userLevelTypeId=="4"){
                    var regionId=document.<%=formName%>.<%=SCMInterfaceKey.REGION_ID%>.value;
                    if(regionId==""){
                        alert('You must choose region for the supervisor.');
                    }else{
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=buttonAction%>";
                        
                        document.<%=formName%>.submit();
                    }
                    return;
                }
                if(userLevelTypeId=="3"){
//                    var areaId=document.<%=formName%>.<%=SCMInterfaceKey.AREA_ID%>.value;
                    var areaId=document.<%=formName%>.<%=SCMInterfaceKey.DISTRICT_ID%>.value;
                    if(areaId==""){
                        alert('You must choose district for the Sales Agent.');
                    }else{
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=buttonAction%>";
                        document.<%=formName%>.submit();
                    }
                    
                    return;

                }


            }
            function doBack(){
                buttonValue=document.<%=formName%>.submitButton.value;
                if(buttonValue=="Add")
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GEN_USER_SEARCH%>";
                else
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
            function unAssignSup(supId){
                document.<%=formName%>.<%=SCMInterfaceKey.SUP_ID%>.value=supId;
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_SUP%>";
                document.<%=formName%>.submit();
            }
            function unAssignTeamlead(teamleadId){
               document.<%=formName%>.<%=SCMInterfaceKey.TEAMLEAD_ID%>.value=teamleadId;
               document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_UNASSIGN_REP_FROM_TEAMLEAD%>";
               document.<%=formName%>.submit();
            }


            function showSupDetail(dcmUserId,userLevelTypeId){
                        alert("showSupDetail");
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            function showTeamleadDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_TEAMLEAD_DETAIL%>";
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
            function submitToAssignTeamleader(){
                if(<%=repTeamleaders==null?0:repTeamleaders.size()%><2){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_REP_TEAMLEAD_ASSIGN%>";
                    document.<%=formName%>.submit();
                }
                else{
                    alert("Rep already has 2 teamleaders.");
                }

            }
    
            function ismaxlength(obj){
                var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : ""
                if (obj.getAttribute && obj.value.length>mlength)
                    obj.value=obj.value.substring(0,mlength)
                var objLowerValue=obj.value.toLowerCase();
                if(objLowerValue.lastIndexOf("<script>")!=-1||objLowerValue.lastIndexOf("<\/script>")!=-1){
                    document.<%=formName%>.submitButton.disabled=true;
                    alert("JavaScript Injection Stop.");

                }else{
                    document.<%=formName%>.submitButton.disabled=false;
                }
            }
            function checkJavascriptInjection(obj){
                var objValue=obj.value;
                if(objValue.lastIndexOf("<script>")!=-1||obj.value.lastIndexOf("<\/script>")!=-1){
                    document.<%=formName%>.submitButton.disabled=true;
                    alert("JavaScript Injection Stop.");

                }else{
                    document.<%=formName%>.submitButton.disabled=false;
                }


            }
            function checkIfSalesAgentLetHimChooseArea(selectBox){
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SUBMIT_USER_LEVEL_TYPE%>";
                document.<%=formName%>.submit();

            }
            function getUser(level){
                if(level==4){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_REGION_GOVERNORATES%>";
                    document.<%=formName%>.submit();
                }
                else if(level==5){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_GOVERNORATE_CITIES%>";
                    document.<%=formName%>.submit();
                }
            }
            function getRegion(level){
                if(level==1){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_REGION_GOVERNORATES%>";
                    document.<%=formName%>.submit();
                }
                else if(level==2){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_GOVERNORATE_CITIES%>";
                    document.<%=formName%>.submit();
                }
                else if(level==3){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_CITY_DISTRICTS%>";
                    document.<%=formName%>.submit();
                }
                else if(level==4){
                    document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GET_DISTRICT_AREAS%>";
                    document.<%=formName%>.submit();
                }
            }
        </script>

    </head>
    <body>
        <div align="center">
            <br>
            <br>
            <h2><%=pageHeader%></h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
                <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=dcmUserId%>">
                <input type="hidden" name="<%=SCMInterfaceKey.PERSON_ID%>"  value="<%=dcmUserUserId%>">
                
                
                
                <input type="hidden" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" value="-1">
                
                <table style="BORDER-COLLAPSE: collapse" cellSpacing="3" cellPadding="3" width="80%" border="1">
                    <%
                    if(confMessage==null ||(confMessage!=null && !confMessage.equalsIgnoreCase("Invalid, This user already created before."))){
                    %>
                    <tr class=TableTextNote>
                        <td>User Level</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>" onchange="checkIfSalesAgentLetHimChooseArea(this);">
                                <option value="">-----</option>
                                <%
                                            if (repLevels != null && repLevels.size() != 0) {
                                                for (int i = 0; i < repLevels.size(); i++) {
                                                    DCMUserLevelTypeModel level = (DCMUserLevelTypeModel) repLevels.get(i);
                                %>
                                <option value="<%=level.getUserLevelTypeId()%>"
                                        <%
                                                                                            if (userLevelTypeId != null && !userLevelTypeId.trim().equals("") && level.getUserLevelTypeId() == Integer.parseInt(userLevelTypeId)) {
                                                                                                out.print("selected");
                                                                                            }
                                        %>

                                        ><%=level.getUserLevelTypeName()%></option>
                                <%
                                                }
                                            }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>

                        </td>
                    </tr>

                    <tr class=TableTextNote>
                        <td>Full Name</td>
                        <td><input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_FULL_NAME%>" value="<%=dcmUserName%>"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=dcmUserName%></font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td>Address</td>
                        <td><input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ADDRESS%>" value="<%=dcmUserAddress%>"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=dcmUserAddress%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Email</td>
                        <td><input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_EMAIL%>" value="<%=dcmUserEmail%>"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=dcmUserEmail%></font></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Mobile No.</td>
                        <td><input type="text" onkeyup="return checkJavascriptInjection(this)" name="<%=SCMInterfaceKey.DCM_USER_MOBILE%>" value="<%=dcmUserMobile%>"><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td>Region</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.REGION_ID%>" 
                                    <%
                                                if (userLevelTypeId != null && !userLevelTypeId.trim().equals("") && userLevelTypeId.equals("3")) {
                                                    out.print("onchange=\"getRegion(1);\"");
                                                }

                                    %>>
                                <option value="">-----</option>
                                <%
                                            if (regions != null && regions.size() != 0) {
                                                for (int i = 0; i < regions.size(); i++) {
                                                    RegionModel region = (RegionModel) regions.get(i);
                                %>
                                <option value="<%=region.getRegionId()%>"
                                        <%
                                            if (regionId != null && regionId.equalsIgnoreCase(region.getRegionId())) {
                                                out.print("selected");
                                             }
                                        %>
                                        ><%=region.getRegionName()%></option>
                                <%
                                                }
                                            }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>
                        </td>
                    </tr>

                    <%
                                if (userLevelTypeId == null || userLevelTypeId.trim().equals("") || userLevelTypeId.equalsIgnoreCase("4")) {
                                } else {

                    %>


                    <tr class=TableTextNote>
                        <td>Governorate</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.GOVERNORATE_ID%>" onchange="getRegion(2);">
                                <option value="">-----</option>
                                <%
                                                                                 if (regionGovernorates != null && regionGovernorates.size() != 0) {
                                                                                     for (int i = 0; i < regionGovernorates.size(); i++) {
                                                                                         RegionModel governorate = (RegionModel) regionGovernorates.get(i);
                                %>
                                <option value="<%=governorate.getRegionId()%>"
                                        <%
                                                                                                if (governorateId != null && governorateId.equalsIgnoreCase(governorate.getRegionId())) {
                                                                                                    out.print("selected");
                                                                                                }
                                        %>
                                        ><%=governorate.getRegionName()%></option>
                                <%
                                                                                     }
                                                                                 }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>

                        </td>
                    </tr>
                    <tr class=TableTextNote>


                        <td>City</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.CITY_ID%>" onchange="getRegion(3);">
                                <option value="">-----</option>
                                <%
                                                                                 if (governorateCities != null && governorateCities.size() != 0) {
                                                                                     for (int i = 0; i < governorateCities.size(); i++) {
                                                                                         RegionModel city = (RegionModel) governorateCities.get(i);
                                %>
                                <option value="<%=city.getRegionId()%>"
                                        <%
                                                                                                if (cityId != null && cityId.equalsIgnoreCase(city.getRegionId())) {
                                                                                                    out.print("selected");
                                                                                                }
                                        %>
                                        ><%=city.getRegionName()%></option>
                                <%
                                                                                     }
                                                                                 }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>District</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.DISTRICT_ID%>" onchange="getRegion(4);">
                                <option value="">-----</option>
                                <%
                                    if (cityDistricts != null && cityDistricts.size() != 0) {
                                        for (int i = 0; i < cityDistricts.size(); i++) {
                                            RegionModel district = (RegionModel) cityDistricts.get(i);
                                %>
                                <option value="<%=district.getRegionId()%>"
                                  <%
                                            if (districtId != null && districtId.equalsIgnoreCase(district.getRegionId())) {
                                                out.print("selected");
                                             }
                                  %>
                                 ><%=district.getRegionName()%></option>
                                <%
                                      }
                                    }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>

                        </td>
                    </tr>
                    
                    <tr class=TableTextNote>
                        <td>Supervisors</td>
                        <td>
                           <select name="<%=SCMInterfaceKey.SUP_ID%>" onchange="">
                                
                                <%
                                   System.out.println("rep supervisors jsp******  : "+repSupervisors);
                                    if(repSupervisors!=null && repSupervisors.size()!=0)
                                    {
                                        for (int i = 0; i < repSupervisors.size(); i++)
                                        {
                                            RepSupervisorModel repSuper = (RepSupervisorModel) repSupervisors.get(i);
                                             System.out.println("rep supervisor "+((RepSupervisorModel) repSupervisors.get(i)).getRepId());
                                            %>
                                            <option value = "<%=repSuper.getSupId()%>"><%=repSuper.getSupName()%></option>
                                        <%}
                                    }
                                    else{
                                    %>
                                    <option value="">-----</option>
                                     <%
                                    }
                                    if (regionSupervisors != null && regionSupervisors.size() != 0) {
                                        for (int i = 0; i < regionSupervisors.size(); i++) {
                                            DCMUserModel regionSuper = (DCMUserModel) regionSupervisors.get(i);
                                            System.out.println("IDS: "+supId+" "+regionSuper.getDcmUserId());
                                                if (/*supId != null && */!supId.equalsIgnoreCase(regionSuper.getDcmUserId())) {
                                %>
                                <option value="<%=regionSuper.getDcmUserId()%>"
                                  <%
                                  System.out.println("regionSuper.getDcmUserId() and supId : "+regionSuper.getDcmUserId()+"  "+supId);
                                            
                                  %>
                                 ><%=regionSuper.getUserFullName()%></option>
                                <%
                                                }
                                      }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Team Leaders</td>
                        <td>
                           <select name="<%=SCMInterfaceKey.TEAMLEAD_ID%>" onchange="">
                                
                                <%
                                   System.out.println("rep teamleaders jsp******  : "+repTeamleaders);
                                    if(repTeamleaders!=null && repTeamleaders.size()!=0)
                                    {
                                        for (int i = 0; i < repTeamleaders.size(); i++)
                                        {
                                            RepTeamleaderModel repTeamlead = (RepTeamleaderModel) repTeamleaders.get(i);
                                             System.out.println("rep teamleader "+((RepTeamleaderModel) repTeamleaders.get(i)).getRepId());
                                            %>
                                            <option value = "<%=repTeamlead.getTeamleadId()%>"><%=repTeamlead.getTeamleadName()%></option>
                                        <%}
                                    }
                                    else{
                                    %>
                                    <option value="">-----</option>
                                     <%
                                    }
                                    System.out.println("Region TEAMLEADERS : "+regionTeamleaders.size());
                                    if (regionTeamleaders != null && regionTeamleaders.size() != 0) {
                                        for (int i = 0; i < regionTeamleaders.size(); i++) {
                                            DCMUserModel regionTeamlead = (DCMUserModel) regionTeamleaders.get(i);
                                              if (/*teamleadId != null && */!teamleadId.equalsIgnoreCase(regionTeamlead.getDcmUserId())) {
                                %>
                                <option value="<%=regionTeamlead.getDcmUserId()%>"
                                  <%
                                  System.out.println("regionTeamlead.getDcmUserId() and teamleadId : "+regionTeamlead.getDcmUserId()+"  "+teamleadId);
                                           
                                            
                                  %>
                                 ><%=regionTeamlead.getUserFullName()%></option>
                                <%
                                 }
                                      }
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
<%--                    <tr class=TableTextNote>
                        <td>Area</td>
                        <td>
                            <select name="<%=SCMInterfaceKey.AREA_ID%>">
                                <option value="">-----</option>
                                <%
                                                                                 if (districtAreas != null && districtAreas.size() != 0) {
                                                                                     for (int i = 0; i < districtAreas.size(); i++) {
                                                                                         RegionModel area = (RegionModel) districtAreas.get(i);
                                %>
                                <option value="<%=area.getRegionId()%>"
                                        <%
                                                                                                if (areaId != null && areaId.equalsIgnoreCase(area.getRegionId())) {
                                                                                                    out.print("selected");
                                                                                                }
                                        %>
                                        ><%=area.getRegionName()%></option>
                                <%
                                                                                     }
                                                                                 }
                                %>
                            </select>

                            <font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font>

                        </td>
                    </tr>--%>
                    <%}
            }
                    %>
                    <tr>
                        <td colspan="2" align="center">
                            <%
                            if(confMessage==null ||(confMessage!=null && !confMessage.equalsIgnoreCase("Invalid, This user already created before."))){
                            %>
                            
                            <input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm();">&nbsp;
                            <%
                            }else{
                            %>
                            <div style="display:none">
                             <input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm();">
                            </div>
                            <%
                            }
                            %>

                            <input type="button" class="button" value="Back" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="doBack();"></td>
                    </tr>
                </table>
                <div id="confMessage">
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

            </form>
        </div>
    </body>
</html>
