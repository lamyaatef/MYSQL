<%-- 
    Document   : rep_management
    Created on : Nov 2, 2010, 9:19:23 AM
    Author     : AHMED SAFWAT
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="com.mobinil.sds.core.system.dcm.region.model.*"
        import="com.mobinil.sds.core.system.dcm.user.model.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String Slach = System.getProperty("file.separator");
    String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
            Vector<RegionModel> regions=new Vector();
            Vector<DCMUserModel> searchResults=new Vector();
            Vector<DCMUserLevelTypeModel> repLevels=new Vector();
            String appName = request.getContextPath();
            String formName = "repManagement";
            String repformName="repForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            regions=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGIONS);
            repLevels=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REP_LEVEL_TYPES);
            searchResults=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            String regionId=(String)dataHashMap.get(SCMInterfaceKey.REGION_ID);
            String searchName=(String)dataHashMap.get(SCMInterfaceKey.SEARCH_NAME);
            userLevelTypeId=userLevelTypeId==null?"":userLevelTypeId;
            regionId=regionId==null?"":regionId;
            searchName=searchName==null?"":searchName;
            String distinationPage=(String)dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
            String totalPageNumbers=(String)dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>
<script src="../resources/js/jquery-1.11.3.js"></script>
        <title>Rep Management</title>

        <script language="JavaScript">
    
            $( document ).ready(function() {
           
                if($('#selectType').val()== "")
                    $('#export_row').hide();
            
});
            function submitEditForm(dcmUserId){
            document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_EDIT_REP_SUP%>";
            document.<%=formName%>.submit();

            }

            function DevChangePageActionWithSubmit(action){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value=action;
            document.<%=formName%>.submit();

            }
            
            function submitAddNewForm(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GEN_USER_SEARCH%>";
            document.<%=formName%>.submit();
            }
            function submitSearchForm(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
            document.<%=formName%>.submit();

            }

            function confirmDelete(dcmUserId,userLevelTypeId){
                if(confirm('Are you sure you want to delete ?'))
                    {
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_DELETE_REP_SUP%>";
                        document.<%=formName%>.submit();
                    }
                else
                return;
            }
            function exportData(base)
            {
                var userType = document.getElementById("selectType").value;
                if(userType=='3')
                    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_SALESREPS);%>'
                if(userType=='4')
                    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_SUPERVISORS);%>'
                if(userType=='5')
                    document.DCMform.action=document.DCMform.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_TEAMLEADERS);%>'
                
                document.DCMform.baseDirectory.value=base;
                document.DCMform.submit();
            }
            
            function change() {
            var export_but = document.getElementById("export_but");
                var export_row = document.getElementById("export_row");
                $('#export_row').show();
                   /* export_but.style.display = "block";
                    export_row.style.display = "block";
                    export_but.style.visibility = 'visible';   
                    export_row.style.visibility = 'visible';   */
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
            document.<%=formName%>.submit();
            }
            

            function viewDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        if(userLevelTypeId=="3")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
                        if(userLevelTypeId=="4")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        if(userLevelTypeId=="5")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_TEAMLEAD_DETAIL%>";
                        document.<%=formName%>.submit();
            }

       </script>
    </head>
    <body>
                <div align="center">
            <br>
            <br>
            <h2>Rep Management</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">

            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>">
            


                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        
                    <tr class=TableTextNote>
                        <td nowrap align=center >Name</td>
                        <td colspan="4" align="center"><input type="text" name="<%=SCMInterfaceKey.SEARCH_NAME%>" value="<%=searchName%>"></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align=center >Region</td>
                        <td align="center">
                    <select name="<%=SCMInterfaceKey.REGION_ID%>">
                        <option value="">-----</option>
                    <%
                                if(regions!=null&&regions.size()!=0){
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

                        </td>

                        <td nowrap align=center >Level</td>
                        <td align="center">
                            <select id="selectType" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" onchange="change()" >
                        <option value="">-----</option>
                    <%
                                if(repLevels!=null&&repLevels.size()!=0){
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

                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <input type="button" class="button" value="Search" onclick="submitSearchForm();">
                            &nbsp;
                            <input type="button" class="button" value="Add New" onclick="submitAddNewForm();">
                        </td>
                        
                    </tr>
                    <tr   id="export_row">        
                    <td colspan="6" align="center">
                       <input align="middle"  id="export_but" type="button"  class="button" name="Export"  value="Export" onclick="exportData('<%=base%>');">
                         
                    </td>
                    </tr>
                    
                </table>


        <%
       if(searchResults!=null){

           if(searchResults.size()==0){

              out.print("<font style='font-size: 11px;font-family: tahoma;line-height: 15px'>No Results based on your Search Criteria.</font>");

               }else{


        %>

        <br>
         
                <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    
                        
                    <tr>
                        <td class=TableHeader nowrap align=center >Name</td>
                        <td  class=TableHeader nowrap align=center>Region</td>
                        <td class=TableHeader nowrap align=center>Level Type</td>
                        <td class=TableHeader nowrap align=center>Edit</td>
                        <td class=TableHeader nowrap align=center>Delete</td>
                    </tr>


                    <%

                                for (int i = 0; i < searchResults.size(); i++) {
                                    DCMUserModel rep = (DCMUserModel) searchResults.get(i);
                    %>


                    <tr>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><a href="javascript:viewDetail(<%=rep.getDcmUserId()%>,<%=rep.getUserLevelTypeId()%>);"><%=rep.getUserFullName()%></a></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=rep.getRegionName() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=rep.getUserLevelTypeName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Edit" onclick="submitEditForm(<%=rep.getDcmUserId()%>);"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Delete" onclick="confirmDelete(<%=rep.getDcmUserId()%>,<%=rep.getUserLevelTypeId()%>);"></td>
                    </tr>

                    <%
                                }

                    %>
                </table>




 <jsp:include page="pagingTable.jsp"  flush="true">
                                      <jsp:param   name="action_name_when_click_enter" value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


</jsp:include>

                <%
                        }
           }
                %>
            </form>
                </div>
    </body>
</html>
