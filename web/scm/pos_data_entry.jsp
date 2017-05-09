

<%@ page
   import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"
%>
<script>
      function drawCalender(argOrder,argValue)
  {
      document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formDataView."+argOrder+",'dd-mm-yyyy','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
  }
    function ShowHideDiv(mobicash) {
        var orange = document.getElementById("orange");
        orange.style.display = mobicash.checked ? "block" : "none";
       
    }
</script>
<%
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {

    document.formDataView.submit();
  }
</SCRIPT>
<html>

  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
      <script src="../resources/js/jquery-1.11.3.js"></script>
  </head>
  <body>
      <%

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_REGIONS; //action=
      %>
      <script>
    $(document).ready(function(){ 
   //region     
  $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>").change(function(){
  //console.log("aaaa ",$(this).val());
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>").val(); //value id of Option selected in the Select object
 // console.log("value id of option selected in Select object is : ",regionid);
    
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:regionid,type:"2"},
    success: function(data, textStatus, jqXHR)
    {
        
      
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
      
        
      
    $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").append($("<option/>").text("--"));

        $.each(data.map.districts, function(k, v) {
            
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
        var option= $("<option/>").text(k).val(v);
 
 
          //  console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").append(option);
});

},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 



//governorate
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").change(function(){
  
  var governid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:governid ,type:"3"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
         $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        
              
    $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
            var option= $("<option/>").text(k).val(v);
 
          //  console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").append(option);
});
        
        
       
        
        
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 







//city
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").change(function(){
  
  var cityid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:cityid ,type:"4"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
        
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
            var option= $("<option/>").text(k).val(v);
 
           // console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").append(option);
});
        
      
      
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 
         
   //district
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").change(function(){
  
  var districtid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
  var array=[3];
    array[0]= 4;
    array[1] = 5;
    array[2] = 6;
    var str = JSON.stringify(array);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:districtid ,type:"6"}, //arraySent:str
    success: function(data, textStatus, jqXHR)
    {
        
      
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
            var option= $("<option/>").text(k).val(v);
 
        //    console.log("data governorates ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").append(option);
});
 
/* $.each(data.map.users, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
         //   console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append(option);
});*/
 
/* $.each(data.map.teams, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          //  console.log("data teamleaders ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append(option);
});*/

 /*$.each(data.map.sales, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
            //console.log("data salesrep ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append(option);
});*/



},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 



//image district
$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").change(function(){
  
  var districtid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT%>").val(); //value id of Option selected in the Select object
  var array=[3];
    array[0]= 4;
    array[1] = 5;
    array[2] = 6;
    var str = JSON.stringify(array);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    datatype: "JSON",
    data : {regionid:districtid ,type:"5"}, //arraySent:str
    success: function(data, textStatus, jqXHR)
    {
        
        
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").empty();
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append($("<option/>").text("--"));
        //$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));
    
        $.each(data.map.districts, function(k, v) {
            
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
           
            var option= $("<option/>").text(k).val(v);//val(k)
 
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>").append(option);
});
 
 
 
 
 
 
/* $.each(data.map.users, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
         //   console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").append(option);
});*/
 
/* $.each(data.map.teams, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
          //  console.log("data teamleaders ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append(option);
});*/

 /*$.each(data.map.sales, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
            //console.log("data salesrep ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append(option);
});*/



},
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

}); 




/*

$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").change(function(){
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%> option:selected").val());
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%> option:selected").text());
  var managerid2= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>").val(); //value id of Option selected in the Select object
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {managerid2:managerid2 ,regionid:regionid,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR%>",userLevel:5},
    success: function(data, textStatus, jqXHR)
    {
        
      
        
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").empty();
     //   $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append($("<option/>").text("--"));
      //  $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));*
    

$.each(data.map.superChildren, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
            //console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").append(option);
          
});




     
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});  






$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").change(function(){
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%> option:selected").val());
   $("#<%= SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%> option:selected").text());
  var managerid2= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>").val(); //value id of Option selected in the Select object
  var regionid= $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>").val(); //value id of Option selected in the Select object
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {managerid2:managerid2 ,regionid:regionid,type:"<%=SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER%>",userLevel:6},
    success: function(data, textStatus, jqXHR)
    {
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").empty();
        $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append($("<option/>").text("--"));
    

$.each(data.map.superChildren, function(k, v) {
            
            var option= $("<option/>").text(v).val(k);
 
           // console.log("data supervisors ",option);
            $("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").append(option);
          
});
     
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});  


$("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%>").change(function() {
  $("#<%= SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%> option:selected").val());
  $("#<%= SCMInterfaceKey.INPUT_HIDDEN_SALESREP_NAME%>").val($("#<%=SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP%> option:selected").text());
});


*/


       

}); 
</script>

    <%
    HashMap dataHashMap = new HashMap(100);
                dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
                if (request.getSession(false).getValue("hm_for_pos_data_entry") != null) {
                    HashMap dataHashMaptemp = (HashMap) request.getSession(false).getValue("hm_for_pos_data_entry");
                    for (Object obj : dataHashMaptemp.keySet()) {
                        if (!dataHashMap.containsKey(obj)) {
                            dataHashMap.put(obj, dataHashMaptemp.get(obj));
                        }
                    }
                    request.getSession(false).removeValue("hm_for_pos_data_entry");
                }


String channel_for_pos = (String)dataHashMap.get("channel_for_pos"),
level_for_pos = (String)dataHashMap.get("level_for_pos"),
payment_for_pos = (String)dataHashMap.get("payment_for_pos"),
control_text_payment_level = (String)dataHashMap.get("control_text_payment_level"),
pos_region = (String)dataHashMap.get("pos_region"),
pos_gover  = (String)dataHashMap.get("pos_gover"),
pos_city  = (String)dataHashMap.get("pos_city"),
pos_district = (String)dataHashMap.get("pos_district"),
pos_area = (String)dataHashMap.get("pos_area"),
pos_owner_id_type = (String)dataHashMap.get("pos_owner_id_type"),
pos_manager_id_type = (String)dataHashMap.get("pos_manager_id_type"),
pos_proposed_doc = (String)dataHashMap.get("pos_proposed_doc"),
control_text_pos_iqrar_type = (String)dataHashMap.get("control_text_pos_iqrar_type");

String pos_code = (String)dataHashMap.get("pos_code"),
pos_name = (String)dataHashMap.get("pos_name"),
pos_arabic_name= (String)dataHashMap.get("pos_arabic_name"),
pos_address= (String)dataHashMap.get("pos_address"),
pos_arabic_address= (String)dataHashMap.get("pos_arabic_address"),
pos_rate= (String)dataHashMap.get("pos_rate"),
pos_phone= (String)dataHashMap.get("pos_phone"),
pos_owner_name= (String)dataHashMap.get("pos_owner_name"),
pos_owner_phone= (String)dataHashMap.get("pos_owner_phone"),
name1 = (String)dataHashMap.get("name1"),
pos_onwer_id_number= (String)dataHashMap.get("pos_onwer_id_number"),
pos_manager_name= (String)dataHashMap.get("pos_manager_name"),
pos_manager_phone= (String)dataHashMap.get("pos_manager_phone"),
name2= (String)dataHashMap.get("name2"),
pos_manager_id_number= (String)dataHashMap.get("pos_manager_id_number"),
pos_demo= (String)dataHashMap.get("pos_demo"),
pos_email= (String)dataHashMap.get("pos_email"),
pos_doc_num= (String)dataHashMap.get("pos_doc_num"),
pos_doc_loc= (String)dataHashMap.get("pos_doc_loc"),
stk_dial= (String)dataHashMap.get("stk_dial"),
control_text_iqrar_date= (String)dataHashMap.get("control_text_iqrar_date"),
stk_verify= (String)dataHashMap.get("stk_verify"),

CONTROL_REGION_PARENT_ID= (String)dataHashMap.get("CONTROL_REGION_PARENT_ID"),
action= (String)dataHashMap.get("action"),
hidden_pos_super_admin_flag= (String)dataHashMap.get("hidden_pos_super_admin_flag"),
data_user_id= (String)dataHashMap.get("data_user_id"),
pos_channel= (String)dataHashMap.get("pos_channel"),
isGenerateChild= dataHashMap.get(SCMInterfaceKey.IS_GENERATE_CHILD_CODE)==null ? "" : (String) dataHashMap.get(SCMInterfaceKey.IS_GENERATE_CHILD_CODE) ,
pos_level= (String)dataHashMap.get("pos_level");





    Vector PaymentMethodVec = (Vector) dataHashMap.get(SCMInterfaceKey.PAYMENT_METHOD_VECTOR);
    String PaymentMethod = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
    
    String alert = (String) dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);
    Vector regions        = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REGIONS);
    Vector governs = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_GOVERNS);
    Vector cities = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_CITIES);
    Vector districts = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_DISTRICTS);
    Vector imgDists = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_IMAGE_DISTRICTS);
    Vector areas = (Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_AREAS);
    
    
    HashMap <String,RegionModel> regionsChilds        = (HashMap <String,RegionModel>)dataHashMap.get(SCMInterfaceKey.CHILD_REGIONS_HM);
    Vector IDTypeVector   = (Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ID_TYPE);

    POSDetailModel posDetailModel = new POSDetailModel();
    posDetailModel = (POSDetailModel)dataHashMap.get(SCMInterfaceKey.POS_DETAIL_MODEL);
    
    PosModel posData = (PosModel) dataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
    
    String posCode = (String) dataHashMap.get(SCMInterfaceKey.POS_CODE);    
    posCode = pos_code ==null || pos_code.compareTo("")==0 ? posCode : pos_code;
    posCode = posCode!=null && !posCode.contains(".") ? "" : posCode;
     Vector channelVec   = (Vector)dataHashMap.get(SCMInterfaceKey.CHANNEL_VECTOR);
     Vector levelVec   = (Vector)dataHashMap.get(SCMInterfaceKey.LEVEL_VECTOR);
     Vector documentVec   = (Vector)dataHashMap.get(SCMInterfaceKey.DOC_VECTOR);
     Vector rateVec   = (Vector)dataHashMap.get(SCMInterfaceKey.RATE_VECTOR);
     Vector PaymentLevelVec=(Vector)dataHashMap.get(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR);
     Vector iqrarTypeVec=(Vector)dataHashMap.get(SCMInterfaceKey.IQRAR_TYPE_VECTOR);
     String generationMessage=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
       String level=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
            String channel=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
            String payment=(String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);




            Object parentIdObj =  dataHashMap.get(SCMInterfaceKey.CONTROL_REGION_PARENT_ID);
            String parentIdstr = parentIdObj==null ? "" : (String) parentIdObj;

String disabledStrCity= "disabled=disabled";
String cityIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
disabledStrCity = cityIdVal!=null && cityIdVal.compareTo("0")!=0 && cityIdVal.compareTo("")!=0 ? "" : disabledStrCity;
String disabledStrArea= "disabled=disabled";
String areaIdVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
disabledStrArea = areaIdVal!=null && areaIdVal.compareTo("0")!=0 && areaIdVal.compareTo("")!=0 ? "" : disabledStrArea;

payment = payment==null || payment.compareTo("")==0 ? payment_for_pos : payment ;
payment = payment==null || payment.compareTo("")==0 ? control_text_payment_level : payment ;
     if (generationMessage==null)
         generationMessage="";
             if (payment==null)
         payment="";
    %>
     <%if(generationMessage.equals("Invalid parent code")){%>
            <script>
                alert("Invalid Parent POS Code");
            </script>
           <%}%>
     
     
     <%
      /*for(int i = 0 ; i <  regions.size() ; i++)
      {
        PlaceDataModel placeDataModel = (PlaceDataModel) regions.get(i);*/
     %>
           <%--<input type="hidden" name="regionName<%=i%>" id="regionName<%=i%>"  value="<%=placeDataModel.getRegionName() %>" />--%>
           <%--<input type="hidden" name="regionId<%=i%>" id="regionId<%=i%>"  value="<%=placeDataModel.getRegionId() %>" />--%>
           <%--<input type="hidden" name="regionParent<%=i%>" id="regionParent<%=i%>"  value="<%=placeDataModel.getParentId() %>" />--%>
           <%--<input type="hidden" name="regionType<%=i%>" id="regionType<%=i%>"  value="<%=placeDataModel.getTypeId() %>" />--%>

          
           <%
     // }
     
      HashMap objDataHashMap = new HashMap();
      objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      String userID = (String)request.getSession(false).getValue(InterfaceKey.HASHMAP_KEY_USER_ID);


      String superAdminFlag = (String)objDataHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);




     

     



      out.println("<br><br><CENTER>");
      out.println("<h2>POS Data Entry</h2>");
      out.println("</CENTER><br><br>");

 
      
     
    
      out.println("<form name='formDataView' method='post'>");
           %><input type="hidden" name="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" id="<%= SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>" value="<%=(parentIdstr.compareTo("")==0?CONTROL_REGION_PARENT_ID : parentIdstr) %>" />

           <input type="hidden" value="<%=action%>" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" >
           <input type="hidden" value="<%=isGenerateChild%>" name="<%=SCMInterfaceKey.HIDDEN_IS_GENERATE_CHILD_CODE%>" >
<%
      out.println("<input type='hidden' name='"+SCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG+"' value='"+(superAdminFlag==null ? hidden_pos_super_admin_flag :superAdminFlag )+"'>");
     

    int v = 0;
    GenericModel  gm, placeTypeGM,IDTypeModel;
    RegionModel regionModel;



      out.println("<input type='hidden' name='" + InterfaceKey.HASHMAP_KEY_USER_ID + "' value='" + (userID==null ? data_user_id : userID) + "'>");

      out.println("<TABLE align=center style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"80%\" border=1>");
      out.println("<tr>");
      out.println("<td>POS Data ");

      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD colspan=2>");
      out.println("<table border=1 align='center' width='100%'>");
       out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=0 align='center' width='100%'>");
     out.println("<TR class=TableTextNote>");

      

      out.println("      <TD class=TableTextNote width='40%'>Channel</TD>");
      out.println("<TD align=\"left\"><select onchange=\"onSelectChannel_Level()\" name='" + SCMInterfaceKey.CHANNEL_FOR_POS + "' id='" + SCMInterfaceKey.CHANNEL_FOR_POS + "'>");
      out.println("<option>--</option>");
      channel = channel==null || channel.compareTo("")==0 ? channel_for_pos : channel ;
      for(int i = 0 ; i < channelVec.size() ; i++)
                            {
                             ChannelModel channelModel=(ChannelModel)channelVec.get(i);
                        %>
                          <option value="<%=channelModel.getChannelId() %>" <%=((channel.compareTo(channelModel.getChannelId()+"")==0)?"selected":"")%> ><%=channelModel.getChannelName()%></option>
                       <% 
                             }
      out.println("</select></td>");


      out.println("</tr>");
      
      //lamya
        
        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Payment Method</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD  + "'>");
        out.println("<option>--</option>");
        for (int i = 0; i < PaymentMethodVec.size(); i++) {
            PaymentMethodModel payModel = (PaymentMethodModel) PaymentMethodVec.get(i);
            if (PaymentMethod.compareTo(payModel.getPaymentMethodId()+ "") == 0) {
                %>
                <option value="<%=payModel.getPaymentMethodId()%>" selected><%=payModel.getPaymentMethodName()%></option>
                <%
                } else {
                %>
                <option value="<%=payModel.getPaymentMethodId()%>" ><%=payModel.getPaymentMethodName()%></option>
                <%
                        }
                    }
                    out.println("</select></td>");
                    out.println("</tr>");
        
      //lamya
      
      
      out.println("<input type=\"hidden\" name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL+ "' value='"+pos_channel+"' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL+ "'>" );
      out.println("<input type=\"hidden\" name='" + SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+ "' value='"+pos_level+"' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL+ "'>");
      out.println("<input type=\"hidden\" name='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL+ "' value='"+payment+"' id='" + SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL+ "'>");
      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Level</TD>");
      out.println("      <TD align=\"left\"><select onchange=\"onSelectChannel_Level()\" name='" + SCMInterfaceKey.LEVEL_FOR_POS  + "' id='" + SCMInterfaceKey.LEVEL_FOR_POS + "' >");
      out.println("<option>--</option>");
      level = level==null || level.compareTo("")==0 ? level_for_pos : level ;
       for(int i = 0 ; i < levelVec.size() ; i++)
                            {
                             LevelModel levelModel=(LevelModel)levelVec.get(i);
                              
                        %>
                          <option value="<%=levelModel.getLevelId() %>" <%=((level.compareTo(levelModel.getLevelId()+"")==0)? "selected" : "") %>><%=levelModel.getLevelName()%></option>
                       <%                          
                             }
      out.println("</select></td>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Payment Level</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.PAYMENT_FOR_POS + "' id='" + SCMInterfaceKey.PAYMENT_FOR_POS + "'>");
     out.println("<option>--</option>");
     System.out.println("payment isss "+payment);
     System.out.println("payment_for_pos isss "+payment_for_pos);
     System.out.println("control_text_payment_level isss "+control_text_payment_level);
     
      for(int i = 0 ; i < PaymentLevelVec.size() ; i++)
                        {
                              PaymentModel levelModel=(PaymentModel)PaymentLevelVec.get(i);
                                   
                        %>
                          <option value="<%=levelModel.getPaymentId() %>" <%= ((payment!=null &&payment.compareTo(levelModel.getPaymentId()+"")==0)? "selected" : "")%>><%=levelModel.getPaymentName()%></option>
                       <%                          
                             }
      out.println("</select></td>");
      out.println("</tr>");
      out.println("<TR>");
        out.println(" <td><input type=checkbox name=report_to_calidus value=yes><font size='1'>Report to Calidus</font></td>");
        out.println("</tr>");
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=set_sign value=yes><font size='1'>Has Sign</font></td>");
        out.println("</tr>");
        out.println("</TR>");
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=pos_level_one value=yes><font size='1'>POS Level One</font></td>");
        out.println("</tr>");



        out.println("<TR>");
        out.println(" <td><input type=checkbox name=quality_club value=yes><font size='1'>Quality Club</font></td>");
        out.println("</tr>");



        out.println("<TR>");
        out.println(" <td><input type=checkbox name=exclusive value=yes><font size='1'>Exclusive</font></td>");
        out.println("</tr>");
        
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=nomad value=yes><font size='1'>Nomad</font></td>");
        out.println("</tr>");
        
        
        out.println("<TR>");
        out.println(" <td><input type=checkbox name=mobicash onclick=\"ShowHideDiv(this)\" value=yes><font size='1'>Orange Money</font></td>");
        out.println("<td><div name=orange id=orange style=display:none><font size='1'>Orange Money Number: </font><input type=text name=orangeText id=orangeText /><font size='1'> ex. 1227777777</font></div></td>");
        out.println("</tr>");
        
        
      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");   
      //lamya
        
      //lamya
      out.println("<TR>");
      out.println("<TD class=TableTextNote width='40%'>POS Code</TD>");
      out.println("<TD><input type='text'  value='"+posCode+"'  name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CODE + "'onblur=\"lengthRestriction(this,9,10)\"></TD>");
      %>
      <tr>
  <td>
      <input type="button" name="generateCode" value="GenerateNewCode" onclick="Generate();">
 </td>
  <td>
      <input type="button" name="generateChild" value="GenrateChildCode" onclick="Generatechild();">
 </td>
 </tr>

 <%if(posCode!=null && posCode.compareTo("")!=0 && posCode.contains(".")){%>
 <script>
       
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value=document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value=document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS %>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL %>.value=document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value;
 document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.disabled=true; 
 document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.disabled=true;
</script>

<%}%>
      <%
      out.println("</tr>");

       out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>POS Name</TD>");
      out.println("<TD><input type='text' value='"+pos_name+"' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_NAME + "'>");
      out.println("&nbsp;&nbsp;");
      out.println("<input class='button' type='button' value=Similars  onclick=similarName(); />");
      out.println("</TD>");
      out.println("</tr>");
      out.println("</tr>");

       out.println("<TR class=TableTextNote>");


      out.println("<TD class=TableTextNote width='40%'>POS Arabic Name</TD>");
      out.println("<TD><input type='text' value='"+pos_arabic_name+"' name='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_NAME + "'>");
      out.println("</TD>");
      out.println("</tr>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");


      out.println("     <TD class=TableTextNote width='40%'>Address</TD>");
      out.println("      <TD><TEXTAREA value='"+pos_address+"' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS + "\" cols=50 rows=5>"+pos_address+"</TEXTAREA></TD>");
      out.println("</tr>");

       out.println("     <TD class=TableTextNote width='40%'>Arabic Address</TD>");
      out.println("      <TD><TEXTAREA value='"+pos_arabic_address+"' name=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_ARABIC_POS_ADDRESS + "\" cols=50 rows=5>"+pos_arabic_address+"</TEXTAREA></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote  width='40%'>Region</TD>");
      //onChange=AddGovernrate();
      out.println("      <TD><select  name='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_REGION + "'>");

      out.println("<option value=0>--</option>");
/*String selectedId = (String)dataHashMap.get(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
selectedId = selectedId==null || selectedId.compareTo("")==0 ? pos_region : selectedId;*/
       for(int i = 0 ; i < regions.size() ; i++)
      {
        PlaceDataModel placeDataModel = (PlaceDataModel) regions.get(i);
        if(placeDataModel.getTypeId() == 1)
            out.println("<option value="+placeDataModel.getRegionId()+">"+placeDataModel.getRegionName()+"</option>");
      }
      out.println("</select></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>Governrate</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_GOVER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_GOVER + "'>");

        out.println("<option value=0>--</option>");
        /*selectedId = posData.getGovernateId() + "";
        System.out.println("jsp: selectedId "+selectedId);
        for (int i = 0; i < governs.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) governs.get(i);
            if (placeDataModel.getTypeId() == 2) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }*/
        out.println("</select></TD>");
        out.println("</tr>");


        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>City</TD>");
        
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_CITY + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_CITY + "'>");

        out.println("<option value=0>--</option>");
        /*selectedId = posData.getCityId()+ "";
        System.out.println("jsp: selectedId "+selectedId);
        for (int i = 0; i < cities.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) cities.get(i);
            if (placeDataModel.getTypeId() == 3) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }*/
        out.println("</select></TD>");
        
        
        //out.println("      <TD>");
        //ild: drowRegionChild(out, regionsChilds, "3", SCMInterfaceKey.CONTROL_TEXT_POS_CITY, posData.getCityId() + "");
        //drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_CITY, city, 3/*,disabled*/);
        //out.println("&nbsp;&nbsp;");
        out.println("<input class='button' " + disabledStrCity + " type='button' name='similar_city' id='similar_city' value='All POS in City'  onclick=similarCity(); />");
        //out.println("</TD>");
        out.println("</tr>");

        
        
        
      out.println("<TR>");
      out.println("      <TD class=TableTextNote width='40%'>District</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT + "'>");

        out.println("<option value=0>--</option>");
        /*selectedId = posData.getDistrictId()+ "";
        System.out.println("jsp: selectedId "+selectedId);
        for (int i = 0; i < districts.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) districts.get(i);
            if (placeDataModel.getTypeId() == 4) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }*/
        out.println("</select></TD>");
        out.println("</tr>");

        
        
        
        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Image District</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_IMAGE_DISTRICT + "'>");

        out.println("<option value=0>--</option>");
        /*selectedId = posData.getImgDistrictId()+ "";
        System.out.println("jsp: selectedId "+selectedId);
        for (int i = 0; i < imgDists.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) imgDists.get(i);
            if (placeDataModel.getTypeId() == 4) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }*/
        out.println("</select></TD>");
        
        
        //out.println("      <TD>");
        //old: drowRegionChild(out, regionsChilds, "4", SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, posData.getDistrictId() + "");
        //drawSelectRegions(out, (Vector<PlaceDataModel>) regions, SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, district, 4/*,disabled*/);
        //out.println("</TD>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("      <TD class=TableTextNote width='40%'>Area</TD>");
        out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_AREA + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_AREA + "'>");

        out.println("<option value=0>--</option>");
        /*selectedId = posData.getAreaId()+ "";
        System.out.println("jsp: selectedId "+selectedId);
        for (int i = 0; i < areas.size(); i++) {
            PlaceDataModel placeDataModel = (PlaceDataModel) areas.get(i);
            if (placeDataModel.getTypeId() == 5) {
                out.println("<option " + ((selectedId != null && selectedId.compareTo(placeDataModel.getRegionId() + "") == 0) ? "selected" : "") + " value=" + placeDataModel.getRegionId() + ">" + placeDataModel.getRegionName() + "</option>");
            }
        }*/
        out.println("</select></TD>");
        //out.println("<TD>");
        //old: drowRegionChild(out, regionsChilds, "5", SCMInterfaceKey.CONTROL_TEXT_POS_AREA, posData.getAreaId() + "");
        
        //out.println("&nbsp;&nbsp;");
        out.println("<input class='button' " + disabledStrArea + " name='similar_area' id='similar_area' type='button' value='All POS in Area'  onclick=similarArea(); />");
        //out.println("</TD>");
        out.println("</tr>");

        
        
        

      out.println("  <input type=hidden value=1 name='" + SCMInterfaceKey.CONTROL_TEXT_POS_RATE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_RATE+ "'>");
      
      out.println("<TR>");
      out.println("  <TD class=TableTextNote>Phone");
      out.println("  </TD>");
      out.println("  <TD class=TableTextNote><input type='text' value='"+pos_phone+"' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_PHONE +"'></TD>");
      out.println("  </TD>");
      out.println("</TR>");
      out.println("</table>");

      out.println("</td>");
      out.println("</tr>");

      ///////////////////////////////////////////////////////////////////lamya//////////////
      /////////////////////////////////////////////////////////
       /* out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Supervisor Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Supervisor Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_NAME + "'></TD>");
       
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Supervisor Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SUPERVISOR_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        
        
        ///////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>TeamLeader Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>TeamLeader Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>TeamLeader Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_TEAMLEADER_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");
        ///added - end
        ///////////////////////////////////////////////////////////
        
        
        //////////////////////////////////////////////////////////////////////
        out.println("<TR>");
        out.println("<TD colspan=2 class=TableTextNote>Salesrep Data ");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD colspan=2>");
        out.println("      <table border=1 align='center' width='100%'>");
        out.println("<TR class=TableTextNote>");
        out.println("      <TD width='40%'>Salesrep Name</TD>");
        out.println("      <TD><input type='text' value='' name='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_SALESREP_NAME + "'></TD>");
        out.println("</tr>");



        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Mobile Phone</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_MOBILE+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Email</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_EMAIL+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        
        out.println("<TR class=TableTextNote>");
        out.println("      <TD>Salesrep Address</TD>");
        out.println("<td>");
        out.println("<input type='text' name='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' id='"+SCMInterfaceKey.CONTROL_TEXT_SALESREP_ADDRESS+"' value=''>");
        out.println("</td>");
        out.println("</tr>");
        
        ////added
         out.println("</tr>");

        out.println("      </table>");
        out.println("      </TD>");
        out.println("</tr>");*/
        ///added - end
        
        /////////////////////////////////////////////////////////////////////////

      //////////////////////lamya - end////////////////////////////////////////////////////
      
      
      String ownerIDTypeName = "";
      out.println("<tr>");
      out.println("<td>Owner Data ");

      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Owner Name</TD>");
      out.println("      <TD><input type='text' value='"+pos_owner_name+"' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME + "'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote >Owner Phone");
      out.println("  </TD>");
      out.println("  <TD class=TableTextNote ><input type='text' value='"+pos_owner_phone+"' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE +"'>");
      out.println("  </TD>");
      out.println("</TR>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Owner Birth Date</TD>");
      out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE + "',\""+(name1==null||name1.compareTo("")==0?"*": name1)+"\");</script></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Owner I.D Type</TD>");
      out.println("      <TD><select id ='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE + "'>");

      for (int i = 0; i < IDTypeVector.size(); i++) {
          IDTypeModel = (GenericModel) IDTypeVector.get(i);
          String selection = "";
          if (ownerIDTypeName != null && ownerIDTypeName.equals(IDTypeModel.get_field_2_value())) {
              selection = "selected";
          }
          if (pos_owner_id_type!=null && pos_owner_id_type.compareTo("")!=0&& pos_owner_id_type.compareTo(IDTypeModel.get_primary_key_value())==0){
              selection = "selected";
          }
          out.println("<option value='" + IDTypeModel.get_primary_key_value() + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
      }
      out.println("</select></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Owner I.D Number</TD>");
      out.println("      <TD><input type='text' value='"+pos_onwer_id_number+"' maxlength=14 name='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER + "'></TD>");
      out.println("</tr>");

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td>Manager Data ");

      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD width='40%'>Manager Name</TD>");
      out.println("      <TD><input type='text' value='"+pos_manager_name+"' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME + "'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("  <TD class=TableTextNote >Manager Phone");
      out.println("  </TD>");
        out.println("  <TD class=TableTextNote><input type='text' value='"+pos_manager_phone+"' name='"+ SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE +"' id='"+ SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE +"'>");
      out.println("  </TD>");
      out.println("</TR>");


      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager Birth Date</TD>");
      out.println("      <TD><Script>drawCalender('" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_BIRTH_DATE + "',\""+(name2==null||name2.compareTo("")==0?"*": name2)+"\");</script></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager I.D Type</TD>");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE + "'>");
      out.println("<option></option>");
      for (int i = 0; i < IDTypeVector.size(); i++) {

          IDTypeModel = (GenericModel) IDTypeVector.get(i);

          String selection = "";
          if (posDetailModel != null
                  && posDetailModel.getPosManagerIDTypeName() != null
                  && posDetailModel.getPosManagerIDTypeName().equals(IDTypeModel.get_field_2_value())) {
              selection = "selected";
          }
          selection = pos_manager_id_type!=null && pos_manager_id_type.compareTo("")!=0&& pos_manager_id_type.compareTo(IDTypeModel.get_field_1_value())==0                  ? "selected" : "";
           
          out.println("<option value='" + IDTypeModel.get_field_1_value()
                  + "' " + selection + ">" + IDTypeModel.get_field_2_value() + "</option>");
//    Utility.logger.debug("XxxxXxxxXxxxX:  MANAGER ID TYPE  ID:  "+ posDetailModel.getPOSManagerIDTypeName());
      }

      out.println("</select></TD>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD>Manager I.D Number</TD>");
      out.println("      <TD><input type='text' onkeyup=\"onKeyPressManagerIDType()\" value='"+pos_manager_id_number+"' maxlength=14 name='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER + "'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Demo Line Number</TD>");
      out.println("      <TD><input type='text' value='"+pos_demo+"' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_DEMO + "'></TD>");
      out.println("</tr>");

      out.println("<TR>");
      out.println("      <TD class=TableTextNote>E-mail</TD>");
      out.println("      <TD><input type='text' value='"+pos_email+"' name='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL + "'></TD>");
      out.println("</tr>");

      out.println("      </table>");
      out.println("      </TD>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("<td>Documents ");

      out.println("</td>");
      out.println("</tr>");
      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");
      out.println("<TR class=TableTextNote>");


      out.println("      <TD class=TableTextNote width='40%'>Proposed Documents");
      out.println("      <TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC + "'onchange=\"doc(this)\">");
      out.println("<option value='-1'></option>");




      for(int i = 0 ; i < documentVec.size() ; i++)
      {
        ProposedDocument docModel = (ProposedDocument) documentVec.get(i);
         out.println("<option value="+docModel.getDocId()+" "+(pos_proposed_doc.compareTo(docModel.getDocId()+"")==0? "selected":"" )+">"+docModel.getDocName()+"</option>");
      }
      out.println("</select></td>");
      out.println("</tr>");


      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Number</TD>");
      out.println("      <TD><input type='text' value='"+pos_doc_num+"' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM + "\" cols=50 rows=5></TEXTAREA></TD>");
      out.println("</tr>");
      out.println("<TR>");
      out.println("      <TD class=TableTextNote>Document Location</TD>");
      out.println("      <TD><input type='text' value='"+pos_doc_loc+"' name=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC + "\" cols=50 rows=5></TD>");
      out.println("</tr>");


      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

%>
      <script>
      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=true;
      </script>
<%
      out.println("<tr>");
      out.println("<td>STK Data ");

      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>STK Dial Number</TD>");
      out.println("<TD><input disabled value='"+stk_dial+"' type='text' name='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "' id='" + SCMInterfaceKey.CONTROL_TEXT_STK_DIAL + "'onblur=\"lengthRestriction(this,10,11)\"></TD>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</table>");
      out.println("<tr>");
      out.println("<td>Iqrar Data");

      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("      <table border=1 align='center' width='100%'>");

      out.println("<TD class=TableTextNote width='40%'>Iqrar Type");
      out.println("<TD><select name='" + SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE + "' id='" + SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE + "' onchange=\"iqrar(this);\">");
      out.println("<option></option>");
      for(int i = 0 ; i < iqrarTypeVec.size() ; i++)
      {
            IqrarTypeModel iqrarModel = (IqrarTypeModel) iqrarTypeVec.get(i);
         out.println("<option value="+iqrarModel.getTypeId()+" "+(control_text_pos_iqrar_type.compareTo(iqrarModel.getTypeId()+"")==0?"selected":"")+">"+iqrarModel.getName()+"</option>");
      }
      out.println("</select></td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>Iqrar Receiving Date</TD>");
      out.println("<TD><Script>drawCalender('" +SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE+ "',\""+(control_text_iqrar_date==null||control_text_iqrar_date.compareTo("")==0?"*": control_text_iqrar_date)+"\");</script></TD>");
      out.println("</tr>");
      out.println("</table>");
      out.println("<tr>");
%>
      <script>
      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE%>.disabled=true;
      </script>
<%
      out.println("<td>General Data ");

      out.println("</td>");
      out.println("</tr>");

      out.println("<TR class=TableTextNote>");
      out.println("      <TD colspan=2>");
      out.println("<table border=1 align='center' width='100%'>");
       out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width='40%'>Survey ID</TD>");
      out.println("<TD><input type='text' value='"+stk_verify+"' name=\"" + SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION + "\" id=\"" + SCMInterfaceKey.CONTROL_TEXT_STK_VERIFICATION+ "\"></TD>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</tr>");
      out.println("</table>");
      

      out.println("</table>");
      out.println("</TD>");
      out.println("</tr>");

      out.println("</table>");

     out.println("<center>");
     out.println("<input class='button' type='button' value='Save' onclick=saveForm(); \">");
     out.println("<input type=\"button\" class=\"button\" value=\" Back \" onclick=\"history.go(-1);\">");
     out.println("</center>");

      out.println("</form>");

    %>
    <br><br>

  

    </body>
<SCRIPT language="javascript">
    
// Declaring required variables
var digits = "0123456789";
// non-digit characters which are allowed in phone numbers
var phoneNumberDelimiters = "()- ";
// characters which are allowed in international phone numbers
// (a leading + is OK)
var validWorldPhoneChars = phoneNumberDelimiters + "+";
// Minimum no of digits in an international phone no.
var minDigitsInIPhoneNumber = 10;
function IsPhoneInteger(s)
{   var i;
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}
function trimPhone(s)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not a whitespace, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (c != " ") returnString += c;
    }
    return returnString;
}
function stripCharsInBag(s, bag)
{   var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function checkInternationalPhone(strPhone){
var bracket=3
strPhone=trimPhone(strPhone)
if(strPhone.indexOf("+")!=-1) return false
if(strPhone.indexOf("-")!=-1)return false
if(strPhone.indexOf("(")!=-1)return false
if(strPhone.indexOf(")")!=-1)return false
s=stripCharsInBag(strPhone,validWorldPhoneChars);
return (IsPhoneInteger(s));
}

function ValidateForm(){
	var Phone=document.frmSample.txtPhone
	
	if ((Phone.value==null)||(Phone.value=="")){
		alert("Please Enter your Phone Number")
		Phone.focus()
		return false
	}
	if (checkInternationalPhone(Phone.value)==false){
		alert("Please Enter a Valid Phone Number")
		Phone.value=""
		Phone.focus()
		return false
	}
	return true
 }
function similarName()
{
  if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME %>.value") == "")
  {
         alert("Please Enter POS Name ..");
 }
else
{
 document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_NAME%>';
 formDataView.submit();
}
}
function Generate()
{
      
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value=document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value=document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS %>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL %>.value=document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value;
 document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_NEW_GENERATE_CODE%>';
 if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value==""){
alert("Please Select Channel");
    return;
}
 if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL%>.value==""){
alert("Please Select payment Level");
    return;
}

 formDataView.submit();

}
function Generatechild()
{
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value=document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value=document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS %>.value;
 document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL %>.value=document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value;
 document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_GENERATE_CODE%>';
 
 document.formDataView.submit();

}
function similarCity()
{
    if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY %>.value") == "0")
  {
         alert("Please Enter City ..");
 }
else
    {
   document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_CITY %>';
   formDataView.submit();
}
}


function similarArea()
{
  if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value") == "0")
  {
    alert("Please Enter Area ..");
  }
  else
    {
      document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_SIMILAR_AREA %>';
      formDataView.submit();
    }
 
}

function onKeyPressManagerIDType(){
    var indx = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE%>.selectedIndex;
    //alert(indx);
    if (indx==0)
        {
            alert ('Please select Manager I.D Type.');
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER%>.value='';
        }
}

function onSelectChannel_Level(){
var selectedChannelIndexVal = document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.selectedIndex;
var selectedLevelIndexVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.selectedIndex;
var channelOptVal = document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.options[selectedChannelIndexVal].text;
var levelOptVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS %>.options[selectedLevelIndexVal].text;

    if (channelOptVal.toLowerCase() == 'distribution' && levelOptVal.toLowerCase()=='pos'){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.disabled = false;
    }
    else{
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value='';
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.disabled = true;
    }

}

function saveForm()
{
    var flag = 0;
   // var posCode = document.getElementById('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE %>').value;
   // alert(posCode);
   // var indicator = posCode.indexof('.001');
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL%>.value=document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.value;
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value=document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.value;
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL %>.value=document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value;
var docLoc = trimAll(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value);
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_LOC%>.value = docLoc;
if( docLoc == "")
    {
        flag = 1;
         alert("Please Enter Document Location ..");
    }
   else if(eval("document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS Payment level ..");
    }
     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL %>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS Channel ..");
    }
     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL%>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS Level ..");
    }
    
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS Name ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value") == "")
    {
        flag = 1;
         alert("Please Enter POS code ..");
    }
   else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE %>.value")))
    {
        flag = 1;
       alert("POS Code Accepts Numbers Only ...");
    }
   // else if(indicator== -1)
    //{
    // flag = 1;
    // alert("POS Code Must Contais .001  ...");
   // }

    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_ADDRESS %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Address ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Region ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Governrate ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter City ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL %>.value") == "3")
    {
    if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value") == "0")
    {
         flag = 1;
         alert("Please Enter District ..");
    }
    if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value") == "0")
    {
         flag = 1;
         alert("Please Enter Area ..");
    }
    }
     if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Owner Name ..");
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER %>.value") == "")
    {
         flag = 1;
         alert("Please Enter Owner ID Number ..");
    }
    else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER %>.value")))
    {
       flag = 1;
       alert("Owner ID Number Accepts Numbers Only ...");
    }
   
    else if(isNaN(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER %>.value")))
    {
       flag = 1;
       alert("Manager ID Number Accepts Numbers Only ...");
    }
   
   
    
   
    else if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE %>.value==""){
         alert("Please enter valid POS phone.");
         return;
    }
      else if(checkInternationalPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE %>.value)==false){
         alert("Please POS Phone Must Be Numeric.");
         return;
    }
    
    else if(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE %>.value==""){
          alert("Please enter valid POS Owner phone.");
          return;
    }
      else if(checkInternationalPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE %>.value)==false){
         alert("Please Owner Phone Must Be Numeric.");
         return;
    }
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL %>.value") != "")
        {
                var str = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_EMAIL %>.value;
            	var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1)
                {
		   flag = 1;
                   alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
                {
		   flag = 1;
                   alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		}

		else if (str.indexOf(at,(lat+1))!=-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
                {
		    flag = 1;

                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.indexOf(dot,(lat+2))==-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }

		else if (str.indexOf(" ")!=-1)
                {
		    flag = 1;
                    alert("Invalid E-mail Formate ..")

		 }
        }

   
     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_BIRTH_DATE %>.value") == "*")
       {
         flag = 1;
         alert("Please Enter Owner Birth date ..");
       }
        else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME%>.value") != "")
       {
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE %>.value") == "")
       {
         flag = 1;
         alert("Please Enter the Manager Phone  ..");
       }
        else if(checkInternationalPhone("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE %>.value")==false)
       {
        flag = 1;
        alert("Manager Phone Number Accepts Numbers Only ...");
       }
       
       
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME %>.value") == "")
       {
         flag = 1;
         alert("Please Enter the Manager Name  ..");
       }
       
   }
   
    else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value") != "")
    {
        if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL %>.value"))==false)
       {
        flag = 1;
        alert("STK Dial Number Accepts Numbers Only ...");
       }
         
    }
     else if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_IQRAR_TYPE %>.value") == "2")
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value") == "")
         {
             flag = 1 ;
            alert("Please Enter STK Number ...");
         }
         if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value"))==false)
         {
             flag = 1 ;
            alert("Please STK Number Must Be Numric...");
         }
          if(checkInternationalPhone(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DEMO %>.value"))==false)
        {
       flag = 1;
       alert("Demo Line Accepts Numbers Only ...");
        }
         if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE%>.value") == "*")
         {
             flag = 1 ;
            alert("Please Enter Iqrar Date ...");
         }
    }
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>.value);
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value);
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_PHONE%>.value);
document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE%>.value=trimPhone(document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_PHONE%>.value);

if(flag != 1)
        {
          document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_POS_DATA_ENTRY_STORE %>';
          formDataView.submit();
        }



System.out.println("after generation of the script");
}


  function checkQuotes()
  {
    var nKeyCode = event.keyCode;
    if( Number(nKeyCode)== 34 )
    {
        alert("You are not allowed to use the (\") character");
        event.keyCode =0;
        return false;
    }
    if( Number(nKeyCode)== 39 )
    {
       alert("You are not allowed to use the (\') character");
       event.keyCode = 0;
       return false;
    }
    return true;
  }

  function data_view_RowSet_add(argCurrentValue,argCurrentName,argCounterName,argControlName,argArrayDataView)
  {
  //Number("+ argControlName + ".RowSet.getRowCount())
      ix = eval("document.formDataView."+argCounterName+".value = Number(document.formDataView."+argCounterName+".value) + 1;");
      ix = ix-1;
      eval(argControlName+".RowSet.add()");
      argCurrentName = argCurrentName+","+argCurrentName;
      //for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      //{
      //   if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
      //   {
      //      eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");
      //   }
      //}
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".getCell("+i+",2).setValue == false; ")
          }//end if
        }//end for
      }//end else
    }

    var UserDefinedDataViewArray =new Array();
    function popUp(argObj,argVersionArrayName,argDescriptionArrayName)
    {
        var nRowIndex;
        var nSelectedIndex = 0;
        var strPopUpColumnIDVersion = new String();
        var strPopUpColumnIDDescription = new String();
        var strID= new String(argObj.id);
        nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
        strPopUpColumnIDVersion = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDVersion +=2
        strPopUpColumnIDDescription = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDDescription +=3;

        eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

           eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
     eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
     if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
         strDescrioption = "N/A";
     eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

      }
      else
      {
        eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
        eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
      }
    }



                function AddGovernrate()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }

                function AddCity()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
                   buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function AddDistrict()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function AddArea()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT %>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        getRegionChild();
                    }
                }
                function onChangeArea()
                {
                    var regionId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA %>.value;
                    buildPatternOfRegions();
                    if (regionId!=0){
                        document.formDataView.similar_area.disabled=false;
                    }
                    else
                        {

                            document.formDataView.similar_area.disabled=true;
                        }
                }
          function getRegionChild()
          {
              
              document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL %>.value=document.formDataView.<%=SCMInterfaceKey.PAYMENT_FOR_POS %>.value;
              document.formDataView.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%= SCMInterfaceKey.ACTION_GET_REGION_CHILD %>';
                formDataView.submit();
          }
          function buildPatternOfRegions(){

          var disCt = "0";
          var disArea = "0";
    document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value='';

    var regId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value;
    var govId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.value;
    var cityId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.value;
    var distId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.value;
    var areaId = document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.value;

    if (regId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_GOVER%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
        disCt=1;
        disArea=1;

    }
    if (govId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_CITY%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
        disCt=1;
        disArea=1;
    }
    if (cityId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>.selectedIndex=0;
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;

        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT%>');
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
        disCt=1;
        disArea=1;
    }
    if (distId==0){
        document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>.selectedIndex=0;
        removeAllOptions('<%=SCMInterfaceKey.CONTROL_TEXT_POS_AREA%>');
        disArea=1;
    }

    if (disArea==1)
        {
        document.formDataView.similar_area.disabled=true;
        }
    if (disCt==1)
        {
        document.formDataView.similar_city.disabled=true;
        }
    document.formDataView.<%=SCMInterfaceKey.CONTROL_REGION_PARENT_ID%>.value = regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId;
    //alert(regId+'-'+govId+'-'+cityId+'-'+distId+'-'+areaId)
    
}
function removeAllOptions(selectName){
    document.getElementById(selectName).length = 0;
    document.getElementById(selectName).options[0] = new Option(' -- ', '0');

}

function iqrar(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
    if(selectedValue=="2")
        {
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE %>.disabled=false;
        }
        else{

            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_IQRAR_DATE %>.disabled=true;
        }
}

function doc(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
    if(selectedValue=="")
        {
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=true;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="";
    }
        else{
             if(selectedValue=="4")
                 {
                      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=false;
                     document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="memo";

                 }else{
                      document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.disabled=false;
            document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM %>.value="";
           
            }
        }
}
function lengthRestriction(elem,min,max){

        /*var uInput = elem.value;
	if(uInput.length >= min && uInput.length <= max ||uInput.length ==0){
		return true;
	}else{
		alert("Please enter between " +min+ " and " +max+ " characters");
		elem.focus();
		return false;
	}*/
}
function trimAll(sString)
        {
            while (sString.substring(0,1) == ' ')
            {
                sString = sString.substring(1, sString.length);
            }
            while (sString.substring(sString.length-1, sString.length) == ' ')
            {
                sString = sString.substring(0,sString.length-1);
            }
            return sString;
        }
  </SCRIPT>
            <script>

                var selectedChannelIndexVal = document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.selectedIndex;
                var selectedLevelIndexVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.selectedIndex;
                var channelOptVal = document.formDataView.<%=SCMInterfaceKey.CHANNEL_FOR_POS%>.options[selectedChannelIndexVal].text;
                var levelOptVal = document.formDataView.<%=SCMInterfaceKey.LEVEL_FOR_POS%>.options[selectedLevelIndexVal].text;

                if (channelOptVal.toLowerCase() == 'distribution' && levelOptVal.toLowerCase()=='pos'){
                    document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.disabled = false;
                }
                else{
                    document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.value='';
                    document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_STK_DIAL%>.disabled = true;
                }
                  
            </script>


</html>

<%!
private void drowRegionChild(JspWriter out, HashMap<String, RegionModel> regionsChilds, String regionType, String ControlName, String selectedId) throws IOException {
    //System.out.println("regionType iss "+regionType);

String functionName = regionType.compareTo("2") == 0 ? "onChange=AddCity();" : regionType.compareTo("3") == 0 ? "onChange=AddDistrict();" :
    regionType.compareTo("4") == 0 ? "onChange=AddArea();" : 
        regionType.compareTo("5") == 0 ? "onChange=onChangeArea();" : "";    
        
         
        out.println("<select " + functionName + "  name='" + ControlName + "' id='" + ControlName + "'>");
        out.println("<option value=0>--</option>");
        if(regionsChilds!=null && !regionsChilds.isEmpty()){
        for (String regId : regionsChilds.keySet()) {

            String regName = regionsChilds.get(regId).getRegionName();
            String levl = regionsChilds.get(regId).getRegionLevelTypeId();
            //String selected = "selected";
            if (regionType.compareTo(levl) == 0) {
                out.println("<option ");
                if (selectedId != null && regId.compareTo(selectedId)==0){
                out.print("selected");
                }
                out.print(" value=" + regId + ">" + regName + "</option>");
            }
        }}
        out.println("</select>");


}%>