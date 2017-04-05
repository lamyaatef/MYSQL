<%-- 
    Document   : view_edit_parent
    Created on : Dec 16, 2012, 5:34:38 PM
    Author     : SAND
--%>

<%@ page contentType="text/html;charset=windows-1252"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.region.dao.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    String regionId = (String) dataHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
    System.out.println("region id in edit parent jsp : "+regionId);//result is null
    String regionName = RegionDAO.getRegionName(regionId);
    Vector parentVec = (Vector) dataHashMap.get(DCMInterfaceKey.VECTOR_PARENTS);
    Vector<RegionModel> selectVec = (Vector) dataHashMap.get(DCMInterfaceKey.VECTOR_SELECTED);
    String UpdateParentForm = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
    String selectedRegionParentId = "";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <head>
        <script>                   
                        
            function updateparent()            
            {

                document.UpdateParentForm.submit();
            }
                    
        </script>

    </head>
    <body>
        <form name='UpdateParentForm' id='UpdateParentForm' action='<%=UpdateParentForm%>' method='post'>


            <input type="hidden"  name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> id=<%=InterfaceKey.HASHMAP_KEY_ACTION%> value="<%=DCMInterfaceKey.ACTION_UPDATE_GEOGRAPHICAL%>">
            <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%> id=<%=InterfaceKey.HASHMAP_KEY_USER_ID%>  value="<%=strUserID%>" >

            <input type="hidden"  name=<%=DCMInterfaceKey.VECTOR_PARENTS%> id=<%=DCMInterfaceKey.VECTOR_PARENTS%> value=<%=parentVec%>>

            <br>
            <br>
            <h2 align="center">Edit Parents</h2>
            <br>
            <br>

            <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
                <tr>
                    <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Entity Name</font></td>
                    <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Parent </font></td>

                </tr>

                <%if(selectVec!=null){
                    for (int j = 0; j < selectVec.size(); j++) {
                        System.out.println("Vec size is:" + selectVec.size());
                

                %>
                <input type='hidden' name='<%=DCMInterfaceKey.CONTROL_HIDDEN_UPDATE_CHILDS + selectVec.get(j).getRegionId()%>' id='<%=DCMInterfaceKey.CONTROL_HIDDEN_UPDATE_CHILDS + selectVec.get(j).getRegionId()%>' value='<%=selectVec.get(j).getRegionId()%>'>
                <% RegionModel model = (RegionModel) selectVec.get(j);
                selectedRegionParentId = model.getParentRegionId();
                %>
                <tr>

                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=model.getRegionName()%></td>

                    <%}
                }%>

                    <td align=center ><select name="<%=DCMInterfaceKey.CONTROL_SHOW_PARENT_COMBOBOX%>" id="<%=DCMInterfaceKey.CONTROL_SHOW_PARENT_COMBOBOX%>">


                            <option value="" >--</option>
                            <%if(parentVec!=null){
                                
                                for (int i = 0; i < parentVec.size(); i++) {
                                    RegionModel model = (RegionModel) parentVec.get(i);
                                   // System.out.println(i+" selectedRegionParentId "+selectedRegionParentId+" model.getRegionId() "+model.getRegionId());
                                    if(selectedRegionParentId.compareTo(model.getRegionId())==0)
                                    {
                            %>
                            <option value="<%=model.getRegionId()%>" selected ><%=model.getRegionName()%></option>
                            <%}
                                    else{
                                        %>
                            <option value="<%=model.getRegionId()%>"  ><%=model.getRegionName()%></option>
                            <%
                                    }
                                
                                
                                }
                            }
                            %>
                        </select>
                </tr>



            </table>
            <br>
            <div align="center">
                <table>

                    <tr>
                        <td>
                            <input type = button value = "Back" onClick = "history.go(-1)">

                            <input type = "submit" value = "Submit" onclick = "updateparent()">
                        </td>
                    </tr>

                </table>

            </div>


        </form>  
    </body>
</html>
