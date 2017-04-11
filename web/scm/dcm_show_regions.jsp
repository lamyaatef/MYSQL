
<%@page import="com.mobinil.sds.core.system.scm.dao.RepManagementDAO"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.dcm.user.model.DCMUserModel"%>
<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.region.dao.*"
         import="com.mobinil.sds.core.system.dcm.region.dto.*"
         import="java.sql.*"
         %>

<%
    HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String Slach = System.getProperty("file.separator");
    String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);

//Vector<DCMUserModel> searchResults=new Vector();
//searchResults=(Vector)objDataHashMap.get(SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS);
           
         //   request.setAttribute("search_vector", searchResults);
          //  request.getSession().setAttribute("search_vector", searchResults);
            
    Boolean checkbox = false;

    if ((String) objDataHashMap.get(DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX) != null) {
        checkbox = (Boolean) objDataHashMap.get(DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX);
    }


    String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Connection con = Utility.getConnection();
   // Vector<RegionModel> regions =  RepManagementDAO.getRegions(con);
        Vector<RegionModel> regions = (Vector) objDataHashMap.get(DCMInterfaceKey.SEARCH_REGION_RESULT);
        Vector<RegionModel> myRegions = (Vector) objDataHashMap.get("my_regions");
        Vector<RegionModel> childRegions = (Vector) objDataHashMap.get("child_regions");
        
     
        String selectedReg = (String) objDataHashMap.get("selected_region_name");
        String selectedRegLev = (String) objDataHashMap.get("selected_region_level");
        
        String Region = (String) objDataHashMap.get("Region");
        String Governorate = (String) objDataHashMap.get("Governorate");
        String City = (String) objDataHashMap.get("City");
        String District = (String) objDataHashMap.get("District");
        String ImgDistrict = (String) objDataHashMap.get("Image_District");
        
        
        System.out.println("all NAMESSS : "+Region+" "+Governorate+" "+City+" "+District+" "+ImgDistrict);
        
       //   request.setAttribute("search_vector", searchResults);
            request.getSession().setAttribute("region_search_vector", regions);
    Vector dcmRegionlevels = (Vector) objDataHashMap.get(DCMInterfaceKey.VECTOR_ALL_REGIONS_LEVELS);
    String appName = request.getContextPath();
    String DCMFormAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_REGIONS; 

    String distinationPage = (String) objDataHashMap.get(DCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
    String totalPageNumbers = (String) objDataHashMap.get(DCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
    String regionName = (String) objDataHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_NAME);
    String Level = (String) objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME);
    System.out.println("LEVEL "+Level);
    String Message = (String) objDataHashMap.get(DCMInterfaceKey.Message);
%>

<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
<link rel="StyleSheet" href="<%out.print(appName);%>/resources/css/dtree.css" type="text/css" />

<style type="text/css">
</style>
<html>
    <body>
        <script src="../resources/js/jquery-1.11.3.js"></script>
        <script language=javascript type='text/javascript'>
            
            
            
                      $( document ).ready(function() {
                          
                        if('<%=selectedRegLev%>'=='1')
                          {
                               $("#<%=SCMInterfaceKey.REGION_ID%>  option:selected").text('<%=Region%>');
                          }  
                        if('<%=selectedRegLev%>'=='2')
                          {
                               $("#<%=SCMInterfaceKey.REGION_ID%>  option:selected").text('<%=Region%>');
                               $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>  option:selected").text('<%=Governorate%>');
                               //console.log("name of selected parent region text : ",$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text());
                          }
                        if('<%=selectedRegLev%>'=='3')
                          {
                               $("#<%=SCMInterfaceKey.REGION_ID%>  option:selected").text('<%=Region%>');
                               $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>  option:selected").text('<%=Governorate%>');
                               $("#<%=SCMInterfaceKey.CITY_ID%>  option:selected").text('<%=City%>');
                          }
                        if('<%=selectedRegLev%>'=='4')
                          {
                               $("#<%=SCMInterfaceKey.REGION_ID%>  option:selected").text('<%=Region%>');
                               $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>  option:selected").text('<%=Governorate%>');
                               $("#<%=SCMInterfaceKey.CITY_ID%>  option:selected").text('<%=City%>');
                               $("#<%=SCMInterfaceKey.DISTRICT_ID%>  option:selected").text('<%=District%>');
                          }
                          
                          
                        if('<%=selectedRegLev%>'=='6')
                          {
                               $("#<%=SCMInterfaceKey.REGION_ID%>  option:selected").text('<%=Region%>');
                               $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>  option:selected").text('<%=Governorate%>');
                               $("#<%=SCMInterfaceKey.CITY_ID%>  option:selected").text('<%=City%>');
                               $("#<%=SCMInterfaceKey.DISTRICT_ID%>  option:selected").text('<%=District%>');
                               $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>  option:selected").text('<%=ImgDistrict%>');
                          }  
                          
                    //console.log("name of selected region text in the element : ",$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text());
                            
           
                
    
    
    if ($('#<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>').val()== "" && '<%=Region%>' =='null' && '<%=Governorate%>' =='null' && '<%=City%>' =='null' && '<%=District%>' =='null' && '<%=ImgDistrict%>' =='null')
        $('#export_row').hide();
        
    
            
//});



////////////////////////////////////////////////////////////////////////////////
        // $(document).ready(function(){ 
                
$("#<%=SCMInterfaceKey.REGION_ID%>").change(function(){
  
  var regionid= $("#<%=SCMInterfaceKey.REGION_ID%>").val(); //value id of Option selected in the Select object
 
    
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
      
        $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").empty();
      
        $("#<%=SCMInterfaceKey.AREA_ID%>").empty();
      
        
      
        
      
    $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").append($("<option/>").text("-----"));


        $.each(data.map.districts, function(k, v) {
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
           
            var option= $("<option/>").text(k).val(v);//val(k)
 
          
            $("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").append(option);
            
            
            
            
            
            
            //$("#<%=SCMInterfaceKey.GOVERNORATE_ID%>").append("selected");
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
  
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:governid ,type:"3"},
    success: function(data, textStatus, jqXHR)
    {
       $("#<%=SCMInterfaceKey.CITY_ID%>").empty();
      $("#<%=SCMInterfaceKey.DISTRICT_ID%>").empty();
      $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").empty();
      $("#<%=SCMInterfaceKey.AREA_ID%>").empty();
        
        
              
    $("#<%=SCMInterfaceKey.CITY_ID%>").append($("<option/>").text("-----"));
    
        $.each(data.map.districts, function(k, v) {
            
            var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
           // console.log("value ",arr);
            var option= $("<option/>").text(k).val(v);//val(k)
 
          
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
        $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").empty();
        $("#<%=SCMInterfaceKey.AREA_ID%>").empty();
        
        
            $("#<%=SCMInterfaceKey.DISTRICT_ID%>").append($("<option/>").text("-----"));
    
        $.each(data.map.districts, function(k, v) {
            
           var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
           // console.log("value ",arr);
            var option= $("<option/>").text(k).val(v);//val(k)
 
           
            $("#<%=SCMInterfaceKey.DISTRICT_ID%>").append(option);
});
        
      
      
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});




//district

$("#<%=SCMInterfaceKey.DISTRICT_ID%>").change(function(){
  
  var cityid= $("#<%=SCMInterfaceKey.DISTRICT_ID%>").val(); //value id of Option selected in the Select object
  //console.log("value id of option selected in Select object is : ",id);
    $.ajax({
    url : "<%out.print(formAction);%>",
    type: "POST",
    data : {regionid:cityid ,type:"6"/*,userLevel:4*/},
    success: function(data, textStatus, jqXHR)
    {
        
        $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").empty();
        $("#<%=SCMInterfaceKey.AREA_ID%>").empty();
        
        
            $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").append($("<option/>").text("-----"));
    
        $.each(data.map.districts, function(k, v) {
            
           var arr = data.map.districts;
            arr.sort = function(a,b) {
                return a[1]>b[1]? 1:a[1]<b[1]?-1:0;
            };
           // console.log("value ",arr);
            var option= $("<option/>").text(k).val(v);//val(k)
 
           
            $("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>").append(option);
});
        
      
      
        
    },
    error: function (jqXHR, textStatus, errorThrown)
    {
 
    }
});

});









});



     
            
            function DevChangePageActionWithSubmit(action)
            {

                document.DCMform.action=document.DCMform.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+action
                document.DCMform.submit();
            }
            
            function exportSearchData(base,level)
            {
                //document.DCMform.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_TEAMLEADERS);%>'
               
                document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_SPECIFIC_REGION_REPORT);%>'
                document.DCMform.baseDirectory.value=base;
                //document.DCMform.SearchResults.value=results;
                document.DCMform.region_select.value=level;
                document.DCMform.search_level.value=$('#result_search_level').val();
                document.DCMform.child_search_level.value=$('#result_child_search_level').val();            
    
                document.DCMform.submit();
            }
            
            
            
            function change(noResults) {
                
            var export_but = document.getElementById("export_but");
                var export_row = document.getElementById("export_row");
            if(noResults=="false")                
            {
                $('#export_row').show();
                
        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='
            +'<%=DCMInterfaceKey.ACTION_SEARCH_REGION%>'+"&"+'<%=InterfaceKey.HASHMAP_KEY_USER_ID%>'+'='
            +<%=strUserID%>;
    

        document.DCMform.submit();
              //  document.DCMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
               // document.DCMform.submit();
            }
            if(noResults=="true")        
                $('#export_row').hide();
                   /* export_but.style.display = "block";
                    export_row.style.display = "block";
                    export_but.style.visibility = 'visible';   
                    export_row.style.visibility = 'visible';   */
               
            }
            
        </script>
        <%
    if (!Message.equals("") && !Message.equals("first")) {%>
        <script>
    alert("<%=Message%>");
        </script>
        <%}
        %>

    <center>
        <br>
        <br>
        <H2>Regional Management</H2>
        <br>
        <br>
    </center>
    <form name='DCMform' id='DCMform' action='<%=DCMFormAction%>' method='post' >
        <input type="hidden" name="baseDirectory" id="baseDirectory" value=""/>
        <input type="hidden" name="selectedEntityName" id="selectedEntityName" value=""/>
        <input type="hidden" name="selectedEntityLevel" id="selectedEntityLevel" value=""/>
        <input type="hidden" name="SearchResults" id="SearchResults" value=""/>
        <input type="hidden" name="region_select" id="region_select1" value=""/>
        <input type="hidden" name="selected_region_name" id="region_select2" value=""/>
        <input type="hidden" name="selected_region_level" id="region_select3" value=""/>
        <input type="hidden" name="search_level" id="region_select4" value=""/>
        <input type="hidden" name="child_search_level" id="region_select5" value=""/>
        
        <input type="hidden" name="Region" id="Region" value=""/>
        <input type="hidden" name="Governorate" id="Governorate" value=""/>
        <input type="hidden" name="City" id="City" value=""/>
        <input type="hidden" name="District" id="District" value=""/>
        <input type="hidden" name="Image_District" id="Image_District" value=""/>
        
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
            <tr class=TableHeader>
                <td align = "center" colspan=5>Search Region</td>
            </tr>
            <tr class=TableTextNote>
                <td align=middle>Entity name</td>
                <td align=middle><input type="text" name='<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>' id='<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>' value="<%=regionName%>"/></td>
            </tr>
            <tr class=TableTextNote>
                <td align=middle>Entity level</td>
                <td align=middle><select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>' onchange="change('false')">
                        <option value=''></option>
                        <%
                       
                            for (int j = 0; j < dcmRegionlevels.size(); j++) {
                                RegionLevelDto regionlevelModel = (RegionLevelDto) dcmRegionlevels.get(j);
                                Integer levelIdint = regionlevelModel.getId();
                                String levelId = levelIdint.toString();
                                String levelName = regionlevelModel.getName();

                        %>
                        <option name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_LEVEL_NAME%>' value='<%=levelId%>'<%if (levelId.equals(Level)) {
                  out.print("SELECTED");
              }%>><%=levelName%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            
            
            
            
        <tr class=TableTextNote>
                        <td align=middle>Region</td>
                        <td align=middle>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="0">-----</option>
                                <%
                                            if (myRegions != null && myRegions.size() != 0) {
                                                
                                                for (int i = 0; i < myRegions.size(); i++) {
                                                    RegionModel region = (RegionModel) myRegions.get(i);
                                %>
                                <option value="<%=region.getRegionId()%>"><%=region.getRegionName()%></option>
                                <%
                                                }
                                            }
                                %>
                            </select>

                            
                        </td>
                    </tr>
                    
                            <tr class=TableTextNote>
                        <td align=middle>Governorate</td>
                        <td align=middle>
                            <select id="<%=SCMInterfaceKey.GOVERNORATE_ID%>" name="<%=SCMInterfaceKey.GOVERNORATE_ID%>" selected=''>
                                <option value="0">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                            <tr class=TableTextNote>
                        <td align=middle>City</td>
                        <td align=middle>
                            <select id="<%=SCMInterfaceKey.CITY_ID%>" name="<%=SCMInterfaceKey.CITY_ID%>">
                                <option value="0">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                            <tr class=TableTextNote>
                        <td align=middle>District</td>
                        <td align=middle>
                            <select id="<%=SCMInterfaceKey.DISTRICT_ID%>" name="<%=SCMInterfaceKey.DISTRICT_ID%>">
                                <option value="0">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                    
                    
                    
                    <tr class=TableTextNote>
                        <td align=middle>Image District</td>
                        <td align=middle>
                            <select id="<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>" name="<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>">
                                <option value="0">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                    
                    
                      </tr>
                            
            
            
            
            
            <tr>
                <td align="center" colspan="2">
                    <input type="button" name="Submit" value="search" onclick="viewsearch();"/>
                </td>
            </tr>
            
            <tr   id="export_row">        
                    <td colspan="6" align="center">
                        
                       <input align="middle"  id="export_but" type="button"  class="button" name="Export"  value="Export List" onclick="exportSearchData('<%=base%>','<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>');">
                         
                    </td>
                    </tr>
            
        </table>
        <br>

        <%if (regions != null && regions.size() != 0) {
        System.out.println("selected regions");
            
           // int max = DBUtil.executeQuerySingleValueInt("SELECT MAX(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MAX(REGION_LEVEL_TYPE_ID)", con);
           // int min = DBUtil.executeQuerySingleValueInt("SELECT MIN(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MIN(REGION_LEVEL_TYPE_ID)", con);
        %>
        <table align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr class=TableHeader>

                <td align = "center" >Select Entity</td>
                <td align = "center" >Entity Name</td>
                <td align = "center" >Entity level</td>
                <td align = "center" >Edit Users</td>
                <td align = "center" >Add Children</td>
                <td align = "center" >Delete Region</td>
                <td align = "center" >View Parents</td>
                <td align = "center" >View Children</td>
                <td align = "center" >Export Region</td>
                <% Integer childnum = 0;
                
                String disabled="";
                String addChildDisabled="";
                
                if (regions.get(0).getRegionLevelTypeId().compareTo("5")==0)
                    addChildDisabled = "disabled";
                
                
                
                //System.out.println("jsp regions LEVEL "+Level);
                if (Level.compareTo("4")!=0 && Level.compareTo("6")!=0)
                    disabled = "disabled";
                //System.out.println("jsp regions LEVEL disabled? "+disabled);
                if (childnum.parseInt(regions.get(0).getRegionLevelTypeId()) == 5/*max*/) {
                %>
                <td align = "center" >View Details</td>
                <%}%>
            </tr>

            <%for (int i = 0; i < regions.size(); i++) {
                
            %>
            <tr class="TableTextNote">
                <td align="center" >
                    <!--
                    <input type="checkbox" name="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + regions.get(i).getRegionId()%>" id="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + regions.get(i).getRegionId()%>" value="<%=checkbox%>">
                    -->
                    <input type="checkbox" name="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + regions.get(i).getRegionId()%>" id="check_box" value="<%=checkbox%>">
                </td>
                <td align="center" ><%=regions.get(i).getRegionName()%></td>
                <td align="center" ><input type="hidden" id="result_search_level" value="<%=regions.get(i).getRegionLevelTypeId()%>"><%=regions.get(i).getRegionLevelTypeName()%></td>
                <td align="center" ><input type="button" name="edit_user" id="edit_user" value="Edit" <%=disabled%> onclick="edit('<%=regions.get(i).getRegionId()%>','<%=regions.get(i).getRegionLevelTypeId()%>')"></td>
                <td align="center" ><input type="button" <%
                    Integer num = 0;
                    
                    if (num.parseInt(regions.get(i).getRegionLevelTypeId()) == 5/*max*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                                           %> name="<%=regions.get(i).getRegionId()%>"  value="Add childs" <%=addChildDisabled%> onclick="addchilds(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%
                    Integer parentnum = 0;
                    
                    if (parentnum.parseInt(regions.get(i).getRegionLevelTypeId()) == 1/*min*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                                            %>name="<%=regions.get(i).getRegionId()%>" value="Delete" disabled onclick="deleteregion(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%
                    Integer parentnum2 = 0;
                    
                    if (parentnum2.parseInt(regions.get(i).getRegionLevelTypeId()) == 1/*min*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                                           %>name="<%=regions.get(i).getRegionId()%>" value="View parent" onclick="viewparent(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%

                 
                if (childnum.parseInt(regions.get(i).getRegionLevelTypeId()) == 5/*max*/) {
                        out.print("disabled=\"true\"");
                    }
                
                                           %>name="<%=regions.get(i).getRegionId()%>"  value="View childs" <%=addChildDisabled%> onclick="viewchilds(<%=regions.get(i).getRegionId()%>)" /> 
                </td>
                
                
                <td align="center" ><input type="button" name="export_data" value="Export" onclick="exportData('<%=base%>','<%=regions.get(i).getRegionName()%>','<%=regions.get(i).getRegionLevelTypeId()%>')" /> 
                </td>
                
                
                <%
                
                if (childnum.parseInt(regions.get(i).getRegionLevelTypeId()) == 5/*max*/) {
                %><td align="center" >
                    <input type="button" name="<%=regions.get(i).getRegionId()%>" value="View Details " onclick="viewDetails(<%=regions.get(i).getRegionId()%>);"/></td>
                    <%
                        }
                
                    %>
            </tr>

            <%
    }%>
        </table>

        <br><br>
        <center>
            <input class=button type="button" name="new" value="Edit Parent"
                   onclick="editParent()">

        </center>
        <br>
        <div align="center">
            <jsp:include page="pagingTable.jsp"  flush="true" >
                <jsp:param   name="action_name_when_click_enter" value="action_search_region"/>
                <jsp:param   name="first_page_number" value="0"/>
                <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


            </jsp:include>
        </div>
    </form>
    <%} 
        else if (childRegions != null && childRegions.size() != 0) {
            System.out.println("child regions");
        
          //  System.out.println("child regions "+childRegions.size());
          //  int max = DBUtil.executeQuerySingleValueInt("SELECT MAX(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MAX(REGION_LEVEL_TYPE_ID)", con);
           // int min = DBUtil.executeQuerySingleValueInt("SELECT MIN(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MIN(REGION_LEVEL_TYPE_ID)", con);
        %>
        <table align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr class=TableHeader>

                <td align = "center" >Select Entity</td>
                <td align = "center" >Entity Name</td>
                <td align = "center" >Entity level</td>
                <td align = "center" >Edit Users</td>
                <td align = "center" >Add Children</td>
                <td align = "center" >Delete Region</td>
                <td align = "center" >View Parents</td>
                <td align = "center" >View Children</td>
                <td align = "center" >Export Region</td>
                <% Integer childnum = 0;
                String disabled="";
                String addChildDisabled="";
                
                if (childRegions.get(0).getRegionLevelTypeId().compareTo("5")==0)
                    addChildDisabled = "disabled";
                
                System.out.println("get chilfffff");
                
                if (childRegions.get(0).getRegionLevelTypeId().compareTo("4")!=0 && childRegions.get(0).getRegionLevelTypeId().compareTo("6")!=0)
                    disabled = "disabled";
                
                if (childnum.parseInt(childRegions.get(0).getRegionLevelTypeId()) == 5/*max*/) {
                %>
                <td align = "center" >View Details</td>
                <%}%>
            </tr>

            <%for (int j = 0; j < childRegions.size(); j++) {
                System.out.println("j "+j+" childRegions.size() "+childRegions.size());
            %>
            <tr class="TableTextNote">
                <td align="center" >
                    <input type="checkbox" name="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + childRegions.get(j).getRegionId()%>" id="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + childRegions.get(j).getRegionId()%>" value="<%=checkbox%>">
                </td>
                <td align="center" ><%=childRegions.get(j).getRegionName()%></td>
                <td align="center" ><input type="hidden" id="result_child_search_level" value="<%=childRegions.get(j).getRegionLevelTypeId()%>"><%=childRegions.get(j).getRegionLevelTypeName()%></td>
                
                <td align="center" ><input type="button" name="edit_user" id="edit_user" value="Edit" <%=disabled%> onclick="edit('<%=childRegions.get(j).getRegionId()%>','<%=childRegions.get(j).getRegionLevelTypeId()%>')"></td>
                <td align="center" ><input type="button" <%
                    Integer num = 0;
                    
                    if (num.parseInt(childRegions.get(j).getRegionLevelTypeId()) == 5/*max*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                                           %> name="<%=childRegions.get(j).getRegionId()%>" value="Add childs" <%=addChildDisabled%> onclick="addchilds(<%=childRegions.get(j).getRegionId()%>)" /> </td>
                <td align="center" ><input  type="button" <%
                    Integer parentnum = 0;
                    
                    if (parentnum.parseInt(childRegions.get(j).getRegionLevelTypeId()) == 1/*min*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                                            %>name="<%=childRegions.get(j).getRegionId()%>"  value="Delete" disabled onclick="deleteregion(<%=childRegions.get(j).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%
                    Integer parentnum2 = 0;
                    
                    if (parentnum2.parseInt(childRegions.get(j).getRegionLevelTypeId()) == 1/*min*/) {
                        out.print("disabled=\"true\"");
                    }
                    
                   %>name="<%=childRegions.get(j).getRegionId()%>"  value="View parent" onclick="viewparent(<%=childRegions.get(j).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%

                 
                if (childnum.parseInt(childRegions.get(j).getRegionLevelTypeId()) == 5/*max*/) {
                        out.print("disabled=\"true\"");
                    }
                
                                           %>name="<%=childRegions.get(j).getRegionId()%>"  value="View childs" <%=addChildDisabled%> onclick="viewchilds(<%=childRegions.get(j).getRegionId()%>)" /> 
                </td>
                
                
                <td align="center" ><input type="button" name="export_data" value="Export" onclick="exportData('<%=base%>','<%=childRegions.get(j).getRegionName()%>','<%=childRegions.get(j).getRegionLevelTypeId()%>')" /> 
                </td>
                
                
                <%
                
                if (childnum.parseInt(childRegions.get(j).getRegionLevelTypeId()) == 5/*max*/) {
                %><td align="center" >
                    <input type="button" name="<%=childRegions.get(j).getRegionId()%>" value="View Details " onclick="viewDetails(<%=childRegions.get(j).getRegionId()%>);"/></td>
                    <%
                        }
                
                    %>
            </tr>

            <%
                System.out.println("j end of for"+j);
    }%>
        </table>

        <br><br>
        <center>
            <input class=button type="button" name="new" value="Edit Parent"
                   onclick="editParent()">

        </center>
        <br>
        <div align="center">
            <jsp:include page="pagingTable.jsp"  flush="true" >
                <jsp:param   name="action_name_when_click_enter" value="action_search_region"/>
                <jsp:param   name="first_page_number" value="0"/>
                <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


            </jsp:include>
        </div>
    </form>
    <%}
        
        
        else {
        if (!Message.equals("first")) {
    %>
    <h3 align="center">There's no result</h3>
    <%}
    }%>
</body>
</html>

<script>
    function viewsearch()
    {
        
        
                

        if(document.getElementById('<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>').value=="")
        {
            
        
        if(document.getElementById('<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>').value=="--" || document.getElementById('<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>').value=="-----" || document.getElementById('<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>').value==""){
        if(document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value=="--" || document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value=="-----" || document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value==""){
            
            
            if(document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value=="--" || document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value=="-----" || document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value==""){
                if(document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value=="--" || document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value=="-----" || document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value==""){
                    if(document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value=="--" || document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value=="-----" || document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value==""){
                
                
                    }
                    else{
                       // alert("region not empty");
                        document.DCMform.selected_region_name.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
                        document.DCMform.Region.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
                        document.DCMform.selected_region_level.value='1';
                     //   console.log(document.DCMform.Region.value);
                    //var elm = document.getElementById("<%=SCMInterfaceKey.REGION_ID%>");
                    //console.log(elm);
                   // elm.setAttribute("selected", "selected");
                    }
                
                }
                else{
               // alert("govern not empty");
                    document.DCMform.selected_region_name.value=$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value;
                    document.DCMform.Governorate.value=$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value;
                    document.DCMform.Region.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
                    document.DCMform.selected_region_level.value='2';
                    
                    //console.log(document.DCMform.selected_region_name.value);
                    //var elm = document.getElementById("<%=SCMInterfaceKey.GOVERNORATE_ID%>");
                    //elm.setAttribute("selected", "selected");
                    //elm.setAttribute("value",document.DCMform.selected_region_name.value);
                    //console.log(elm);
                    //$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option[value=18]").prop("selected", "selected");
                }
                
            }
            else{
            //alert("city not empty");
                document.DCMform.selected_region_name.value=$("#<%=SCMInterfaceKey.CITY_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value;
                document.DCMform.City.value=$("#<%=SCMInterfaceKey.CITY_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value;
                document.DCMform.Governorate.value=$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value;
                document.DCMform.Region.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
                document.DCMform.selected_region_level.value='3';
                //console.log(document.DCMform.selected_region_name.value);
                  //  var elm = document.getElementById("<%=SCMInterfaceKey.CITY_ID%>");
                   // console.log(elm);
                    //elm.setAttribute("selected", "selected");
            }
            
        }
        else{
        // alert("district not empty");
             document.DCMform.selected_region_name.value=$("#<%=SCMInterfaceKey.DISTRICT_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value;
             document.DCMform.District.value=$("#<%=SCMInterfaceKey.DISTRICT_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value;
             document.DCMform.City.value=$("#<%=SCMInterfaceKey.CITY_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value;
             document.DCMform.Governorate.value=$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value;
             document.DCMform.Region.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
             document.DCMform.selected_region_level.value='4';
             //console.log(document.DCMform.selected_region_name.value);
               //      var elm = document.getElementById("<%=SCMInterfaceKey.DISTRICT_ID%>");
                 //    console.log(elm);
                   //  elm.setAttribute("selected", "selected");
         }
        }
        else{
            // alert("image district not empty");
                document.DCMform.selected_region_name.value=$("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>').value;
                document.DCMform.Image_District.value=$("#<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value;            
                document.DCMform.District.value=$("#<%=SCMInterfaceKey.DISTRICT_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.DISTRICT_ID%>').value;
                document.DCMform.City.value=$("#<%=SCMInterfaceKey.CITY_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.CITY_ID%>').value;
                document.DCMform.Governorate.value=$("#<%=SCMInterfaceKey.GOVERNORATE_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.GOVERNORATE_ID%>').value;
                document.DCMform.Region.value=$("#<%=SCMInterfaceKey.REGION_ID%> option:selected").text();//document.getElementById('<%=SCMInterfaceKey.REGION_ID%>').value;
                document.DCMform.selected_region_level.value='6';
                //console.log(document.DCMform.selected_region_name.value);
                  //      var elm = document.getElementById("<%=SCMInterfaceKey.IMAGE_DISTRICT_ID%>");
                    //    console.log(elm);
                      //  elm.setAttribute("selected", "selected");

            }
        }
        else{alert("You must select level");
            return;}
            

        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='
            +'<%=DCMInterfaceKey.ACTION_SEARCH_REGION%>'+"&"+'<%=InterfaceKey.HASHMAP_KEY_USER_ID%>'+'='
            +<%=strUserID%>;
    

        document.DCMform.submit();
    }
    function deleteregion(i)
    {
        var row=i;
   
        /*document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.DELETE_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

*/
        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.DELETE_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

        document.DCMform.submit();
    }

    function addchilds(i)
    {
        var row=i;
    /*    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ADD_CHILDS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
            document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ADD_CHILDS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
        document.DCMform.submit();
    }



    function edit(i,regionLev)
    {
        var row=i;
        var level = regionLev;
        
    /*    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ADD_CHILDS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
            /*document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.EDIT_USERS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row;*/
    
    
    document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.EDIT_USERS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_LEVEL_TYPE_ID + "");%>='+level;
    
    
    
        document.DCMform.submit();
    }

    function viewparent(i)
    {
        var row=i;
        /*
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
        document.DCMform.submit();
    }
    function editParent()
    {
     
        /*document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_EDIT_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>*/

            if (document.getElementById('check_box').checked) {
                    document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_EDIT_PARENT);%>'+
                    '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>    
                    
                    document.DCMform.submit();
            } else {
                alert("Select a region before you edit parent.");
            }



        
    }
    function viewchilds(i)
    {
        var row=i;
  /*      document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_CHILDS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_CHILDS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
        document.DCMform.submit();
    }
    function exportData(base,name,level)
    {
        /*document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_REGION_POS_REPORT);%>'
    alert("document.DCMform.action "+document.DCMform.action);    */
    document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_REGION_POS_REPORT);%>'
    document.DCMform.baseDirectory.value=base;
    document.DCMform.selectedEntityName.value=name;
    document.DCMform.selectedEntityLevel.value=level;
        document.DCMform.submit();
    }
    function viewDetails(i)
    {
        var row=i;
  /*      document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_DETAILS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_DETAILS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
        document.DCMform.submit();
    }
</script>
