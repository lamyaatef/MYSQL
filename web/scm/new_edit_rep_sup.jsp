
<%@page import="com.mobinil.sds.core.system.scm.dao.RepSupDAO"%>
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
    
    String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_REGIONS; 

System.out.println("form action "+formAction);
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
            Vector<SupervisorTeamleadersModel> superTeamleaders=new Vector();
            Vector<TeamleaderSupervisorsModel> teamSupervisors=new Vector();
            Vector allSupers=(Vector)dataHashMap.get("AllSupervisors"); 
            Vector allTeams=(Vector)dataHashMap.get("AllTeamleaders"); 
            repSupervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_SUPERVISORS);
            repTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REP_TEAMLEADS);
            RepSupervisorModel selectedRepSuper = (RepSupervisorModel) dataHashMap.get(SCMInterfaceKey.SELECTED_REP_SUPERVISOR);
            RepTeamleaderModel selectedRepTeamlead = (RepTeamleaderModel) dataHashMap.get(SCMInterfaceKey.SELECTED_REP_TEAMLEADER);
            Vector<DCMUserModel> regionSupervisors=new Vector();
            Vector<DCMUserModel> regionTeamleaders=new Vector();
            regionSupervisors=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_SUPERVISORS);
            regionTeamleaders=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGION_TEAMLEADERS);
            superTeamleaders = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_SUPERVISOR_TEAMLEADERS);
            teamSupervisors = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_TEAMLEAD_SUPERVISORS);
            System.out.println("region supers ///// "+regionSupervisors);
            System.out.println("region leaders ///// "+regionTeamleaders);
//            Vector<RegionModel> districtAreas = new Vector();





            //BASIC DATA
            Connection con = Utility.getConnection();
            appName = request.getContextPath();
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
            System.out.println("beginning gov id : "+governorateId);
            String cityId = (String) dataHashMap.get(SCMInterfaceKey.CITY_ID);
            String districtId = (String) dataHashMap.get(SCMInterfaceKey.DISTRICT_ID);
            String supId = (String) dataHashMap.get(SCMInterfaceKey.SUP_ID);
            String teamleadId = (String) dataHashMap.get(SCMInterfaceKey.TEAMLEAD_ID);
            System.out.println("supervisor id && teamleader id : "+supId+" "+teamleadId);
            String regionId = (String) dataHashMap.get(SCMInterfaceKey.REGION_ID);
            System.out.println("region from action "+regionId);
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
            System.out.println("DCM USER NOT NULL : - ");
            userLevelTypeId = dcmUser.getUserLevelTypeId()==null?"":dcmUser.getUserLevelTypeId();
            dcmUserId = dcmUser.getDcmUserId()==null?"":dcmUser.getDcmUserId();
            dcmUserName = dcmUserDetail.getUserFullName()==null?"":dcmUserDetail.getUserFullName();
            dcmUserAddress = dcmUserDetail.getUserAddress()==null?"":dcmUserDetail.getUserAddress();
            dcmUserEmail = dcmUserDetail.getUserEmail()==null?"":dcmUserDetail.getUserEmail();
            dcmUserMobile = dcmUserDetail.getUserMobile()==null?"":dcmUserDetail.getUserMobile();
            dcmUserUserId =dcmUser.getUserId()==null?"":dcmUser.getUserId();


            if (dcmUserId == null || dcmUserId.trim().equals("") ) {
                //NEW
                System.out.println("USER USER ID "+dcmUserUserId);
                System.out.println("NEW..");
                pageHeader = "Add New Rep/Supervisor";
                buttonValue = "Add";
                buttonAction = SCMInterfaceKey.ACTION_ADD_NEW_REP_SUP;

            } else {
                //UPDATE
                System.out.println("UPDATE..");
                pageHeader = "Update Rep/Supervisor";
                buttonValue = "Update";
                buttonAction = SCMInterfaceKey.ACTION_UPDATE_REP_SUP;

                regionId = dcmUser.getRegionId();// we don't set region id in dcm user anymore
                System.out.println("region from action after UPDATE .."+regionId);
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
                        System.out.println("in if true - userLevelTypeId: "+userLevelTypeId+" and(T/F): "+RepManagementDAO.checkIfRegionIsDistrict(con, regionId));
                        if (userLevelTypeId.equalsIgnoreCase("6")&&RepManagementDAO.checkIfRegionIsDistrict(con, regionId)) {
                            System.out.println("UPDATE AGAIN .... in IF TRUE");
                            districtId = regionId;

                            cityId = RepManagementDAO.getParentRegionId(con, districtId);
                            cityDistricts = RepManagementDAO.getDistricts(con, cityId);

                            governorateId = RepManagementDAO.getParentRegionId(con, cityId);
                            System.out.println("middle gov id : "+governorateId);
                            governorateCities = RepManagementDAO.getCities(con, governorateId);

                            regionId = RepManagementDAO.getParentRegionId(con, governorateId);
                            regionGovernorates = RepManagementDAO.getGovernorates(con, regionId);
                          //  regionSupervisors=RepSupDAO.getRegionSupervisors(con, regionId);
                           // regionTeamleaders=RepSupDAO.getRegionTeamleaders(con, regionId);

                        }
            }
        }else{
                System.out.println("DCM USER NULL : - ");
                System.out.println("NEW..");
                pageHeader = "Add New Rep/Supervisor";
                buttonValue = "Add";
                buttonAction = SCMInterfaceKey.ACTION_ADD_NEW_REP_SUP;

        }


          System.out.println("userLevelTypeId JSP : "+userLevelTypeId);  
            
            

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>

        <title>New/Edit POS Group </title>
        <script src="../resources/js/jquery-1.11.3.js"></script>
        <script>
            $(document).ready(function(){ 
                
$("#<%=SCMInterfaceKey.REGION_ID%>").change(function(){
  //console.log("aaaa ",$(this).val());
  var regionid= $("#<%=SCMInterfaceKey.REGION_ID%>").val(); //value id of Option selected in the Select object
 // console.log("value id of option selected in Select object is : ",regionid);
    
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:regionid,type:"2"},
    success: function(data, textStatus, jqXHR)
    {
        
      
        $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").empty();
        $("#<%=SCMInterfaceKey.CITY_ID%>").empty();
        $("#<%=SCMInterfaceKey.DISTRICT_ID%>").empty();
        
      
        
      
    $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").append($("<option/>").text("--"));

        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          //  console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").append(option);
});

},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 


//governorate
$("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").change(function(){
  
  var governid= $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:governid ,type:"3"},
    success: function(data, textStatus, jqXHR)
    {
       $("#<%=SCMInterfaceKey.CITY_ID%>").empty();
      $("#<%=SCMInterfaceKey.DISTRICT_ID%>").empty();
        
        
              
    $("#<%=SCMInterfaceKey.CITY_ID%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          //  console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CITY_ID%>").append(option);
                      });
        
  
    },
    error: function (jqXHR, textStatus, errorThrown)
    {

    }
});

}); 







//city
$("#<%=SCMInterfaceKey.CITY_ID%>").change(function(){
  
  var cityid= $("#<%=SCMInterfaceKey.CITY_ID%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:cityid ,type:"4"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
        
        $("#<%=SCMInterfaceKey.DISTRICT_ID%>").empty();
        
        
            $("#<%=SCMInterfaceKey.DISTRICT_ID%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
           // console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.DISTRICT_ID%>").append(option);
});
        
      
      
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});

});
  
            function submitForm(isSalesAgent)
            {
                userName=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_FULL_NAME%>.value;
                userAddress="none";//document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ADDRESS%>.value;
                userEmail=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_EMAIL%>.value;
                userMobile=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_MOBILE%>.value;
                userLevelTypeId=document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>.value;
             if(isSalesAgent=="true")
             {
                 
                 document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_SUP_ID%>.value;
                document.<%=formName%>.<%=SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID%>.value;
            }
               
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
                if(userLevelTypeId=="4" || userLevelTypeId=="5"){
                    
                    var regionId=document.<%=formName%>.<%=SCMInterfaceKey.REGION_ID%>.value;
                    if(regionId==""){
                        alert('You must choose region for the supervisor.');
                    }else{
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=buttonAction%>";
                        //document.<%=formName%>.<%=SCMInterfaceKey.REGION_LEVEL_TYPE_ID%>.value="1";
                        document.<%=formName%>.submit();
                    }
                    return;
                }
                if(userLevelTypeId=="6"){
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


         /*   function viewDetail(dcmUserId,userLevelTypeId){
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

            }*/
    
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
               // //////console.log("selected : ",selectBox);
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
                    document.getElementById("<%=SCMInterfaceKey.DCM_USER_ID%>").value = "<%=dcmUserId%>";
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
                <input type="hidden" id ="<%=SCMInterfaceKey.DCM_USER_ID%>" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="<%=dcmUserId%>">
                <input type="hidden" id ="<%=SCMInterfaceKey.PERSON_ID%>" name="<%=SCMInterfaceKey.PERSON_ID%>"  value="<%=dcmUserUserId%>">
                
                
                
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
                                System.out.println("Rep LEVELS : "+repLevels.size());
                                if (repLevels != null && repLevels.size() != 0) {
                                                for (int i = 0; i < repLevels.size(); i++) {
                                                    DCMUserLevelTypeModel level = (DCMUserLevelTypeModel) repLevels.get(i);
                                %>
                                <option value="<%=level.getUserLevelTypeId()%>"
                                        <%
                                            if (userLevelTypeId != null && !userLevelTypeId.trim().equals("") && level.getUserLevelTypeId() == Integer.parseInt(userLevelTypeId)) {
                                                   System.out.println("SELECTED ..."+level.getUserLevelTypeName());
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
                        <%
                                System.out.println("user name "+dcmUserName);
                        %>
                        <td>Full Name</td>
                        <td><input type="text" name="<%=SCMInterfaceKey.DCM_USER_FULL_NAME%>" value="<%=dcmUserName%>" style="width: 70%"></font></td>
                    </tr>
<%--
                    <tr class=TableTextNote>
                        <td>Address</td>
                        <td><input type="text" name="<%=SCMInterfaceKey.DCM_USER_ADDRESS%>" value="<%=dcmUserAddress%>" style="width: 70%"></font></td>
                    </tr>
--%>
                    <tr class=TableTextNote>
  
                        <td>Email</td>
                        <!--
                        <font style="font-size: 11px;font-family: tahoma;line-height: 15px" ><%=dcmUserEmail%></font>
                        -->
                        
                        <td><input type="text" name="<%=SCMInterfaceKey.DCM_USER_EMAIL%>" value="<%=dcmUserEmail%>" style="width: 70%"></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Mobile No.</td>
                        <td><input type="text" onkeyup="return checkJavascriptInjection(this)" name="<%=SCMInterfaceKey.DCM_USER_MOBILE%>" value="<%=dcmUserMobile%>" style="width: 70%"><font style="font-size: 11px;font-family: tahoma;line-height: 15px;color: red">*</font></td>
                    </tr>

                    <tr class=TableTextNote>
                        <td>Region</td>
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>" 
                                    <%
                                                if (userLevelTypeId != null && !userLevelTypeId.trim().equals("") && userLevelTypeId.equals("4")) {
                                                   // out.print("onchange=\"getRegion(1);\"");
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
                                if (userLevelTypeId == null || userLevelTypeId.trim().equals("") /*|| userLevelTypeId.equalsIgnoreCase("4") || userLevelTypeId.equalsIgnoreCase("5")*/ ) {
                                } 
                                else if (userLevelTypeId.equalsIgnoreCase("4")) { 
                                    
                                    %>
                                    <tr class=TableTextNote>
                                        <input type="hidden" id ="supervisorHidden" name="supervisorHidden"  value="true">
                        <td>Team Leaders</td>
                        <td>
                           <select id="<%=SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID%>" name="<%=SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID%>" >
                                
                                 <%
                                    System.out.println("CHANGED TEAMLEADER  under update SUPERVISOR my teams: " + superTeamleaders);
                                    System.out.println("CHANGED TEAMLEADER  under update SUPERVISOR all teams: " + allTeams.size());
                                    if (superTeamleaders!=null && superTeamleaders.size()!=0 && allTeams != null && allTeams.size() != 0) {
                                        //System.out.println("repTeamleaders.get(0).getTeamleadId() "+repTeamleaders.get(0).getTeamleadId());
                                        for (int i = 0; i < allTeams.size(); i++) {
                                            com.mobinil.sds.core.system.scm.model.TeamleaderModel teamleader = (com.mobinil.sds.core.system.scm.model.TeamleaderModel) allTeams.get(i);
                                            String selected ="";
                                           // System.out.println("teamleader.getTeamleaderId() "+teamleader.getTeamleaderId());
                                            if(superTeamleaders.get(0).getTeamleadId().compareTo(teamleader.getTeamleaderId())==0){
                                 
                                                selected = "selected";
                                            }
                                            
                                %>
                                <option <%=selected%> value ="<%=teamleader.getTeamleaderId()%>" ><%=teamleader.getTeamleaderName()%></option>
                                <%}
                                        
                                }
                                    
                                    else if(allTeams != null && allTeams.size() != 0){
                                        
                                        for (int i = 0; i < allSupers.size(); i++) {
                                         com.mobinil.sds.core.system.scm.model.TeamleaderModel teamleader = (com.mobinil.sds.core.system.scm.model.TeamleaderModel) allTeams.get(i);
                                           %>
                                    <option value ="<%=teamleader.getTeamleaderId()%>" ><%=teamleader.getTeamleaderName()%></option>
                                <% 
                                        }
                                        
                                    }
                                    else{   
                                %>
                                <option>---</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <%       } else if (userLevelTypeId.equalsIgnoreCase("5")) { 
                                    %>
                                   
                    <tr class=TableTextNote>
                        <input type="hidden" id ="supervisorHidden" name="supervisorHidden"  value="false">
                        <td>Supervisors</td>
                        <td>
                            <select id="selectSuper" name="<%=SCMInterfaceKey.CONTROL_TEXT_SUP_ID%>" >

                                <%
                                    System.out.println("CHANGED SUPERVISOR  2: " + teamSupervisors+" all supers "+allSupers.size());
                                    
                                    if (teamSupervisors!=null && teamSupervisors.size()!=0 && allSupers != null && allSupers.size() != 0) {
                                        //System.out.println("repSupervisors.get(0).getSupId() "+repSupervisors.get(0).getSupId());
                                        for (int i = 0; i < allSupers.size(); i++) {
                                            com.mobinil.sds.core.system.scm.model.SupervisorModel repSuper = (com.mobinil.sds.core.system.scm.model.SupervisorModel) allSupers.get(i);
                                            String selected ="";
                                            if(teamSupervisors.get(0).getSupId().compareTo(repSuper.getSupervisorId())==0){
                                 
                                                selected = "selected";
                                            }
                                            
                                %>
                                <option <%=selected%> value ="<%=repSuper.getSupervisorId()%>" ><%=repSuper.getSupervisorName()%></option>
                                <%}
                                        
                                }
                                    else if(allSupers != null && allSupers.size() != 0){
                                        for (int i = 0; i < allSupers.size(); i++) {
                                         com.mobinil.sds.core.system.scm.model.SupervisorModel repSuper = (com.mobinil.sds.core.system.scm.model.SupervisorModel) allSupers.get(i);
                                           %>
                                    <option value ="<%=repSuper.getSupervisorId()%>" ><%=repSuper.getSupervisorName()%></option>
                                <% 
                                        }
                                        
                                    }
                                    else{   
                                %>
                                <option>---</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                                <% } else if (userLevelTypeId.equalsIgnoreCase("6")) {
                                    System.out.println("ESLEsssssssss : REP ");

                    %>


                    <tr class=TableTextNote>
                        <td>Governorate</td>
                        <td>
                            <select id="<%=SCMInterfaceKey.GOVERNORATE_ID%>" name="<%=SCMInterfaceKey.GOVERNORATE_ID%>" >
                                <option value="">-----</option>
                                <%
                                System.out.println("governorateId "+governorateId+" and regionGovernorates "+regionGovernorates);
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
                            <select id="<%=SCMInterfaceKey.CITY_ID%>" name="<%=SCMInterfaceKey.CITY_ID%>" >
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
                            <select id="<%=SCMInterfaceKey.DISTRICT_ID%>" name="<%=SCMInterfaceKey.DISTRICT_ID%>">
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
                            <select id="selectSuper" name="<%=SCMInterfaceKey.CONTROL_TEXT_SUP_ID%>" >

                                <%
                                    System.out.println("CHANGED SUPERVISOR  repSupSize : " + repSupervisors.size()+" allSupersSize : "+allSupers);
                                    
                                    if (repSupervisors!=null && repSupervisors.size()!=0 && allSupers != null && allSupers.size() != 0) {
                                        //System.out.println("repSupervisors.get(0).getSupId() "+repSupervisors.get(0).getSupId());
                                        for (int i = 0; i < allSupers.size(); i++) {
                                            com.mobinil.sds.core.system.scm.model.SupervisorModel repSuper = (com.mobinil.sds.core.system.scm.model.SupervisorModel) allSupers.get(i);
                                            String selected ="";
                                            if(repSupervisors.get(0).getSupId().compareTo(repSuper.getSupervisorId())==0){
                                 
                                                selected = "selected";
                                            }
                                            
                                %>
                                <option <%=selected%> value ="<%=repSuper.getSupervisorId()%>" ><%=repSuper.getSupervisorName()%></option>
                                <%}
                                        
                                }
                                    else if(allSupers != null && allSupers.size() != 0){
                                        for (int i = 0; i < allSupers.size(); i++) {
                                         com.mobinil.sds.core.system.scm.model.SupervisorModel repSuper = (com.mobinil.sds.core.system.scm.model.SupervisorModel) allSupers.get(i);
                                           %>
                                    <option value ="<%=repSuper.getSupervisorId()%>" ><%=repSuper.getSupervisorName()%></option>
                                <% 
                                        }
                                        
                                    }
                                    else{   
                                %>
                                <option>---</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <tr class=TableTextNote>
                        <td>Team Leaders</td>
                        <td>
                           <select id="<%=SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID%>" name="<%=SCMInterfaceKey.CONTROL_TEXT_TEAMLEAD_ID%>" >
                                
                               <%
                                    System.out.println("CHANGED TEAMLEADER  : " + repTeamleaders.size());
                                    
                                    if (repTeamleaders!=null && repTeamleaders.size()!=0 && allTeams != null && allTeams.size() != 0) {
                                        //System.out.println("repTeamleaders.get(0).getTeamleadId() "+repTeamleaders.get(0).getTeamleadId());
                                        for (int i = 0; i < allTeams.size(); i++) {
                                            com.mobinil.sds.core.system.scm.model.TeamleaderModel teamleader = (com.mobinil.sds.core.system.scm.model.TeamleaderModel) allTeams.get(i);
                                            String selected ="";
                                           // System.out.println("teamleader.getTeamleaderId() "+teamleader.getTeamleaderId());
                                            if(repTeamleaders.get(0).getTeamleadId().compareTo(teamleader.getTeamleaderId())==0){
                                 
                                                selected = "selected";
                                            }
                                            
                                %>
                                <option <%=selected%> value ="<%=teamleader.getTeamleaderId()%>" ><%=teamleader.getTeamleaderName()%></option>
                                <%}
                                        
                                }
                                    else if(allTeams != null && allTeams.size() != 0){
                                        for (int i = 0; i < allTeams.size(); i++) {
                                         com.mobinil.sds.core.system.scm.model.TeamleaderModel teamleader = (com.mobinil.sds.core.system.scm.model.TeamleaderModel) allTeams.get(i);
                                           %>
                                    <option value ="<%=teamleader.getTeamleaderId()%>" ><%=teamleader.getTeamleaderName()%></option>
                                <% 
                                        }
                                        
                                    }
                                    else{   
                                %>
                                <option>---</option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                    <%           
                    }
                    }
                    %>
                    <tr>
                        <td colspan="2" align="center">
                            
                           
                    <%--
                            
                            // if(confMessage==null ||(confMessage!=null && !confMessage.equalsIgnoreCase("Invalid, This user was created.")))
                            //{
                            <input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm('empty');">&nbsp;
                            }else
                            --%>
                            <% System.out.println("BEFORE SUBMITFORM BUTTON - userLevelTypeId: "+userLevelTypeId);
                               if (userLevelTypeId != null &&(userLevelTypeId.equalsIgnoreCase("") || userLevelTypeId.equalsIgnoreCase("4") || userLevelTypeId.equalsIgnoreCase("5") )){
                            %>
                            
                             <input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm('false');">
                          
                            <%
                            } else if (userLevelTypeId != null && userLevelTypeId.equalsIgnoreCase("6")){
                            %>
                          
                             <input type="button" name="submitButton" class="button" value="<%=buttonValue%>" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitForm('true');">
                           
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
                                <%--
                <script>
                           $("#selectSuper option:selected").val("<%=selectedRepSuper.getSupId()%>");
                </script>
                                --%>
    </body>
</html>
