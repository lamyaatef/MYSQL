
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


    Boolean checkbox = false;

    if ((String) objDataHashMap.get(DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX) != null) {
        checkbox = (Boolean) objDataHashMap.get(DCMInterfaceKey.CONTROL_SHOW_REGIONS_CHECKBOX);
    }


    String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector<RegionModel> regions = (Vector) objDataHashMap.get(DCMInterfaceKey.SEARCH_REGION_RESULT);
    Vector dcmRegionlevels = (Vector) objDataHashMap.get(DCMInterfaceKey.VECTOR_ALL_REGIONS_LEVELS);
    String appName = request.getContextPath();
    String DCMFormAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";


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
        <script language=javascript type='text/javascript'>
            function DevChangePageActionWithSubmit(action)
            {

                document.DCMform.action=document.DCMform.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+action
                document.DCMform.submit();
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
                <td align=middle><select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_REGION_LEVEL_NAME%>'>
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
            <tr>
                <td align="center" colspan="2">
                    <input type="button" name="Submit" value="search" onclick="viewsearch();"/>
                </td>
            </tr>
        </table>
        <br>

        <%if (regions != null && regions.size() != 0) {%>
        <%
            Connection con = Utility.getConnection();
            int max = DBUtil.executeQuerySingleValueInt("SELECT MAX(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MAX(REGION_LEVEL_TYPE_ID)", con);
            int min = DBUtil.executeQuerySingleValueInt("SELECT MIN(REGION_LEVEL_TYPE_ID) FROM DCM_REGION_LEVEL_TYPE", "MIN(REGION_LEVEL_TYPE_ID)", con);
        %>
        <table align="center" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr class=TableHeader>

                <td align = "center" >Select Entity</td>
                <td align = "center" >Entity Name</td>
                <td align = "center" >Entity level</td>
                <td align = "center" >Add Childs</td>
                <td align = "center" >Delete Region</td>
                <td align = "center" >View Parents</td>
                <td align = "center" >View Childs</td>
                <td align = "center" >Export Region</td>
                <% Integer childnum = 0;
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
                
                
                <td align="center" ><input type="button" name="export_data" value="Export" onclick="exportData('<%=base%>')" /> 
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
        document.DCMform.action=document.DCMform.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='
            +'<%=DCMInterfaceKey.ACTION_SEARCH_REGION%>'+"&"+'<%=InterfaceKey.HASHMAP_KEY_USER_ID%>'+'='
            +<%=strUserID%>;
        document.DCMform.submit();
    }
    function deleteregion(i)
    {
        var row=i;
   
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.DELETE_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row


        document.DCMform.submit();
    }

    function addchilds(i)
    {
        var row=i;
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ADD_CHILDS_TO_REGION);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

        document.DCMform.submit();
    }

    function viewparent(i)
    {
        var row=i;
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

        document.DCMform.submit();
    }
    function editParent()
    {
     
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_EDIT_PARENT);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>

        document.DCMform.submit();
    }
    function viewchilds(i)
    {
        var row=i;
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_CHILDS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

        document.DCMform.submit();
    }
    function exportData(base)
    {
        
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_EXPORT_REGION_POS_REPORT);%>'
        document.DCMform.baseDirectory.value=base;
        document.DCMform.submit();
    }
    function viewDetails(i)
    {
        var row=i;
        document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(DCMInterfaceKey.ACTION_VIEW_REGION_DETAILS);%>'+
            '&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID + "");%>='+<%out.print(strUserID);%>+'&'+'<%out.print(DCMInterfaceKey.INPUT_TEXT_REGION_ID + "");%>='+row

        document.DCMform.submit();
    }
</script>
