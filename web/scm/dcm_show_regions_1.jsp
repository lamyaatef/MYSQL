
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
           
                if($('#<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>').val()== "")
                    $('#export_row').hide();
            
});



////////////////////////////////////////////////////////////////////////////////
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
        <input type="hidden" name="SearchResults" id="SearchResults" value=""/>
        <input type="hidden" name="region_select" id="region_select" value=""/>
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
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="">-----</option>
                                <%
                                            if (regions != null && regions.size() != 0) {
                                                System.out.print("REGIONSSS "+regions);
                                                for (int i = 0; i < regions.size(); i++) {
                                                    RegionModel region = (RegionModel) regions.get(i);
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
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                            <tr class=TableTextNote>
                        <td align=middle>City</td>
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                            <tr class=TableTextNote>
                        <td align=middle>District</td>
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="">-----</option>
                                
                            </select>

                            
                        </td>
                    </tr>
                    
                      </tr>
                            <tr class=TableTextNote>
                        <td align=middle>Area</td>
                        <td>
                            <select id="<%=SCMInterfaceKey.REGION_ID%>" name="<%=SCMInterfaceKey.REGION_ID%>">
                                <option value="">-----</option>
                                
                            </select>

                            
                        </td>
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

        <%if (regions != null && regions.size() != 0) {%>
        <%
            
            int max = DBUtil.executeQuerySingleValueInt("SELECT MAX(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MAX(REGION_LEVEL_TYPE_ID)", con);
            int min = DBUtil.executeQuerySingleValueInt("SELECT MIN(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MIN(REGION_LEVEL_TYPE_ID)", con);
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
                if (Level.compareTo("4")!=0)
                    disabled = "disabled";
                
                if (childnum.parseInt(regions.get(0).getRegionLevelTypeId()) == max) {
                %>
                <td align = "center" >View Details</td>
                <%}%>
            </tr>

            <%for (int i = 0; i < regions.size(); i++) {
            %>
            <tr class="TableTextNote">
                <td align="center" >
                    <input type="checkbox" name="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + regions.get(i).getRegionId()%>" id="<%=DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX + regions.get(i).getRegionId()%>" value="<%=checkbox%>">
                </td>
                <td align="center" ><%=regions.get(i).getRegionName()%></td>
                <td align="center" ><%=regions.get(i).getRegionLevelTypeName()%></td>
                <td align="center" ><input type="button" name="edit_user" id="edit_user" value="Edit" <%=disabled%> onclick="edit('<%=regions.get(i).getRegionId()%>')"></td>
                <td align="center" ><input type="button" <%
                    Integer num = 0;
                    if (num.parseInt(regions.get(i).getRegionLevelTypeId()) == max) {
                        out.print("disabled=\"true\"");
                    }
                                           %> name="<%=regions.get(i).getRegionId()%>" name="<%=regions.get(i).getRegionId()%>" value="Add childs" onclick="addchilds(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input  type="button" <%
                    Integer parentnum = 0;
                    if (parentnum.parseInt(regions.get(i).getRegionLevelTypeId()) == min) {
                        out.print("disabled=\"true\"");
                    }
                                            %>name="<%=regions.get(i).getRegionId()%> name="<%=regions.get(i).getRegionId()%>" name="<%=regions.get(i).getRegionId()%>" value="Delete" onclick="deleteregion(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%
                    Integer parentnum2 = 0;
                    if (parentnum2.parseInt(regions.get(i).getRegionLevelTypeId()) == min) {
                        out.print("disabled=\"true\"");
                    }
                                           %>name="<%=regions.get(i).getRegionId()%>" name="<%=regions.get(i).getRegionId()%>" value="View parent" onclick="viewparent(<%=regions.get(i).getRegionId()%>)" /> </td>
                <td align="center" ><input type="button" <%

                    if (childnum.parseInt(regions.get(i).getRegionLevelTypeId()) == max) {
                        out.print("disabled=\"true\"");
                    }
                                           %>name="<%=regions.get(i).getRegionId()%>" name="<%=regions.get(i).getRegionId()%>" value="View childs" onclick="viewchilds(<%=regions.get(i).getRegionId()%>)" /> 
                </td>
                
                
                <td align="center" ><input type="button" name="export_data" value="Export" onclick="exportData('<%=base%>','<%=regions.get(i).getRegionName()%>')" /> 
                </td>
                
                
                <%
                    if (childnum.parseInt(regions.get(i).getRegionLevelTypeId()) == max) {
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
    <%} else {
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
            alert("You must select level");
            return;
        }

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



    function edit(i)
    {
        var row=i;
    /*    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ADD_CHILDS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
*/
            document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.EDIT_USERS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row
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

        document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_EDIT_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>    
        document.DCMform.submit();
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
    function exportData(base,name)
    {
        
        /*document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_REGION_POS_REPORT);%>'
    alert("document.DCMform.action "+document.DCMform.action);    */
    document.DCMform.action='<%=DCMFormAction%>'+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_REGION_POS_REPORT);%>'
    document.DCMform.baseDirectory.value=base;
    document.DCMform.selectedEntityName.value=name;
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
